#include <vector>
#include <random>
#include <limits>
#include <iostream>
#include <fstream>
#include <sstream>
#include <algorithm>
#include <chrono>

#define ITERATIONS 200

struct Point
{
    float x{0}, y{0};
};

using DataFrame = std::vector<Point>;

__global__ void cudaClear(Point *points, const int bytes)
{
    const int index = threadIdx.x;

    if(index < bytes)
        points[index] = {0,0};

    __syncthreads();

}

struct CUDADataFrame
{
    CUDADataFrame(int size): size(size), bytes(size * sizeof(Point))
    {
        cudaMalloc((void **)&points, bytes);
    }

    CUDADataFrame(int size, DataFrame df): size(size), bytes(size * sizeof(Point))
    {
        cudaMalloc((void **)&points, bytes);
        cudaMemcpy(points, df.data(), bytes, cudaMemcpyHostToDevice);
    }

    ~CUDADataFrame()
    {
        cudaFree(points);
    }

    void clear()
    {
        cudaClear<<<1, size>>>(points, bytes);
    }

    Point *points;
    int size{0};
    int bytes{0};
};

__device__ float square(float val)
{
    return val * val;
}

__device__ float squared_distance(Point first, Point second)
{
    return square(first.x - second.x) + square(first.y - second.y);
}

__global__ void assign_clusters(const Point *data_set, int set_size, const Point *means, Point *new_sums, int k, int *counts)
{

    extern __shared__ float shared_data[];


    // Index of thread in it's own block
    const int local_index = threadIdx.x;
    const int global_index = blockIdx.x * blockDim.x + threadIdx.x;

    // Set all positions to 0 TEST
    //shared_data[global_index] = 0;

    if(global_index >= set_size) return;

    // Load the means value into shared memory.
    if(local_index < k)
    {
        shared_data[local_index] = means[local_index].x;
        shared_data[local_index + k] = means[local_index].y;
    }

    __syncthreads();

    const Point data_point = data_set[global_index];

    float best_distance = 99999999;
    int best_cluster = -1;

    for(int cluster = 0; cluster < k; cluster++)
    {
        Point p = {shared_data[cluster], shared_data[cluster + k]};

        float distance = squared_distance(data_point, p);

        if(distance < best_distance)
        {
            best_distance = distance;
            best_cluster = cluster;
        }
    }

    __syncthreads();

    atomicAdd(&new_sums[best_cluster].x, data_point.x);
    atomicAdd(&new_sums[best_cluster].y, data_point.y);
    atomicAdd(&counts[best_cluster], 1);
}


__global__ void compute_new_means(Point *means, Point *new_sums, int *counts)
{
    const int cluster = threadIdx.x;
    const int count = max(1, counts[cluster]);
    means[cluster].x = new_sums[cluster].x / count;
    means[cluster].y = new_sums[cluster].y / count;
}

void place_random_centroids(DataFrame &data)
{
    std::mt19937 rng(std::random_device{}());
    std::shuffle(data.begin(), data.end(), rng);
}

int main(int argc, char const *argv[])
{
    if(argc < 3)
    {
        std::cerr << "usage: assign_clusters <data-file> <k> [iterations]" << std::endl;
        std::exit(EXIT_FAILURE);
    }

    const auto k = std::atoi(argv[2]);
    const auto n_of_iterations = (argc == 4) ? std::atoi(argv[3]) : ITERATIONS;

    DataFrame df;

    std::ifstream data_file(argv[1]);
    if(!data_file)
    {
        std::cerr << "Could not open file: " << argv[1] << std::endl;
        std::exit(EXIT_FAILURE);
    }

    std::string line;
    while (std::getline(data_file, line)) {
        std::istringstream line_stream(line);   
        Point point;
        int label;
        line_stream >> point.x >> point.y >> label;
        df.push_back(point);
    }   

    const int n_of_elements = df.size();

    CUDADataFrame device_data(n_of_elements, df);

    place_random_centroids(df);

    CUDADataFrame device_means(k, df);

    const int threads = 1024;
    const int blocks = (n_of_elements + threads - 1) / threads;

    const int fine_shared_memory = 3 * threads * sizeof(float);

    CUDADataFrame device_sums(k);
    int *device_counts;
    cudaMalloc(&device_counts, k * sizeof(int));
    cudaMemset(device_counts, 0, k * sizeof(int));

    const auto start = std::chrono::high_resolution_clock::now();
    for(int iter = 0; iter < n_of_iterations; iter++)
    {
        assign_clusters<<<blocks, threads, fine_shared_memory>>>(device_data.points, n_of_elements, device_means.points, device_sums.points, k, device_counts);

        cudaDeviceSynchronize();

        compute_new_means<<<1, k>>>(device_means.points, device_sums.points, device_counts);

        cudaDeviceSynchronize();
    }
  	const auto end = std::chrono::high_resolution_clock::now();
  	const auto duration = std::chrono::duration_cast<std::chrono::duration<float>>(end - start);
  	std::cerr << "Time: " << duration.count() << "s" << std::endl;

    cudaFree(device_counts);

    DataFrame result(k);
    cudaMemcpy(result.data(), device_means.points, device_means.bytes, cudaMemcpyDeviceToHost);

    for(int cluster = 0; cluster < k; cluster++)
    {
        std::cout << result[cluster].x << " " << result[cluster].y << std::endl;
    }


    return 0;
}
