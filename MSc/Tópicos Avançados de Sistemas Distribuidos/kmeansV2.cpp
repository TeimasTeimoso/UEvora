#include <vector>
#include <random>
#include <limits>
#include <iostream>
#include <fstream>
#include <sstream>
#include <chrono>

#define ITERATIONS 200

struct Point
{
    float x{0}, y{0};
};

using DataFrame = std::vector<Point>;

float square(float val) {
    return val * val;
}

float square_distance(Point first, Point second)
{
    return square(first.x - second.x) + square(first.y - second.y);
}

/* Place the centroids randomly */
void place_centroids(const DataFrame &data, DataFrame &means)
{
    static std::random_device seed;
    static std::mt19937 gen_random_index(seed());
    std::uniform_int_distribution<int> indices(0, data.size() - 1);

    for(auto &cluster : means)
    {
        cluster = data[indices(gen_random_index)];
    }
}

/* Returns the best cluster for a given point */
int assign_point_to_cluster(const DataFrame &data, const DataFrame &means, int point_index)
{
    auto best_distance = std::numeric_limits<float>::max();
    int best_cluster = 0;

    for(int cluster = 0; cluster < means.size(); cluster++)
    {
        const float distance = square_distance(data[point_index], means[cluster]);

        if(distance < best_distance)
        {
            best_distance = distance;
            best_cluster = cluster;
        }
    }

    return best_cluster;
}

DataFrame k_means(const DataFrame &data, int k, int n_of_iterations)
{

    DataFrame means(k);

    place_centroids(data, means);

    std::vector<int> assignments(data.size());
    for(int iter = 0; iter < n_of_iterations; iter++)
    {
        // Get assignment
        for(int point = 0; point < data.size(); point++)
        {
           assignments[point] = assign_point_to_cluster(data, means, point);
        }

        // Compute sum for each cluster
        DataFrame new_means(k);
        std::vector<int> counts(k, 0);
        for(int point = 0; point < data.size(); point++)
        {
            const auto cluster = assignments[point];
            new_means[cluster].x += data[point].x;
            new_means[cluster].y += data[point].y;
            counts[cluster] += 1;
        }

        for(int cluster = 0; cluster < k; cluster++)
        {
            const auto count = std::max<int>(1, counts[cluster]);
            means[cluster].x = new_means[cluster].x / count;
            means[cluster].y = new_means[cluster].y / count;
        }
    }

    return means;
}

int main(int argc, char const *argv[])
{
    if (argc < 3)
    {
        std::cerr << "usage: k_means <data-file> <k> [iterations]" << std::endl;
        std::exit(EXIT_FAILURE);
    }

    const auto k = std::atoi(argv[2]);
    const auto iterations = (argc >= 4) ? std::atoi(argv[3]) : ITERATIONS;
    
    DataFrame data;
    
    std::ifstream data_file(argv[1]);
    if(!data_file)
    {
        std::cerr << "Could not open file: " << argv[1] << std::endl;
        std::exit(EXIT_FAILURE);
    }

    std::string line;
    while (std::getline(data_file, line)) {
        Point point;
        std::istringstream line_stream(line);
        size_t label;
        line_stream >> point.x >> point.y >> label;
        data.push_back(point);
    }   


    const auto start = std::chrono::high_resolution_clock::now();
    DataFrame means = k_means(data, k, iterations);
    const auto end = std::chrono::high_resolution_clock::now();
    const auto duration = std::chrono::duration_cast<std::chrono::duration<double>>(end - start);

    std::cout << "Time: " << duration.count() << "s" << std::endl;

    for (auto& mean : means) {
        std::cout << mean.x << " " << mean.y << std::endl;
    }

    return 0;
}
