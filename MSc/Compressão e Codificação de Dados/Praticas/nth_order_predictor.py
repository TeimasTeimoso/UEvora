import numpy as np
from numpy.linalg import solve
from numpy import linspace
from math import sin
from math import cos
from math import pi


def build_nxn_matrix(n: int, new_matrix: np.ndarray, signal_points: np.ndarray) -> list:
    signal_sample_size = len(signal_points) - 1 # -1 to adjust to index
    n = n - 1 # -1 to adjust index
    
    for i in range(n):
        for j in range(n):
            if i < j:
                new_matrix[i][j] = new_matrix[j][i]
            else:
                x_ti = signal_points[(n-i):(signal_sample_size-i)]
                x_tj = signal_points[(n-j):(signal_sample_size-j)]
                new_matrix[i][j] = sum(x_ti * x_tj)
                
    return new_matrix
                
def build_vector(n: int, new_vector: np.ndarray, signal_sample: np.ndarray) -> list:
    signal_sample_size = len(signal_sample)
    
    for i in range(n):
        new_vector[i] = sum(signal_sample[n:signal_sample_size] * signal_sample[n-1-i:signal_sample_size-1-i]) 
    
    return new_vector

def nth_order_predictor(n: int, signal_sample: np.ndarray) -> list:
    empty_matrix = np.empty(shape=(n,n))
    empty_vector = np.empty(shape=(n,))
    
    matrix = build_nxn_matrix(n, empty_matrix, signal_sample)
    print(matrix)
    vector = build_vector(n, empty_vector, signal_sample)
    print(vector)
    
    weights = solve(matrix, vector)
    
    return weights

if __name__ == "__main__":
    t = linspace(0, 0.01, 100)                        # 0 to 10ms with 100 samples
    x = sin(2*pi*700*t) + 0.5*cos(2*pi*500*t)         # our signal

    w = nth_order_predictor(3, x)
    print(w)