import numpy as np

COST_MATRIX_M1 = np.array([[0, 1, 2], [1, -1, 2], [1, 4, -1]])
COST_MATRIX_M2 = np.array([[0, 3, 1], [1, -1, 2], [3, 4, -1]])

def get_cost(matrix, cost_m):
    return np.multiply(matrix, cost_m).sum()

if __name__ == "__main__":
    """
    0 -> M1, 3 classse
    1 -> M2, 3 classes
    2 -> M_, 2 classes
    """

    j48_confusion = [np.array([[97, 1, 5], [10, 485, 53], [9, 41, 652]]),
                    np.array([[97, 1, 5], [10, 471, 67], [9, 30, 663]]),
                    np.array([[0, 0, 0], [0, 485, 63], [0, 41, 661]])]

    jrip_confusion = [np.array([[98, 1, 4], [12, 486, 50], [10, 50, 642]]),
                    np.array([[99, 1, 3], [12, 474, 62], [9, 43, 650]]),
                    np.array([[0, 0, 0], [0, 496, 52], [0, 49, 653]])]

    naivebayes_confusion = [np.array([[10, 44, 49], [13, 487, 48], [5, 53, 644]]),
                            np.array([[12, 42, 49], [20, 471, 57], [5, 37, 660]]),
                            np.array([[0, 0, 0], [0, 491, 57], [0, 48, 654]])]

    randomforest_confusion = [np.array([[82, 9, 12], [8, 482, 58], [8, 46, 648]]),
                            np.array([[86, 7, 10], [10, 458, 80], [7, 39, 656]]),
                            np.array([[0, 0, 0], [0, 486, 62], [0, 47, 655]])]

    svm_confusion = [np.array([[16, 45, 42], [13, 491, 44], [5, 38, 659]]),
                    np.array([[21, 39, 43], [17, 474, 57], [5, 28, 669]]),
                    np.array([[0, 0, 0], [0, 492, 56], [0, 34, 668]])]
    
    print(f"J48 M1: {get_cost(j48_confusion[0], COST_MATRIX_M1)}")
    print(f"JRip M1: {get_cost(jrip_confusion[0], COST_MATRIX_M1)}")
    print(f"Naive Bayes M1: {get_cost(naivebayes_confusion[0], COST_MATRIX_M1)}")
    print(f"Random Forest M1: {get_cost(randomforest_confusion[0], COST_MATRIX_M1)}")
    print(f"SVM M1: {get_cost(svm_confusion[0], COST_MATRIX_M1)}")

    print()

    print(f"J48 M2: {get_cost(j48_confusion[1], COST_MATRIX_M1)}")
    print(f"JRip M2: {get_cost(jrip_confusion[1], COST_MATRIX_M2)}")
    print(f"Naive Bayes M2: {get_cost(naivebayes_confusion[1], COST_MATRIX_M2)}")
    print(f"Random Forest M2: {get_cost(randomforest_confusion[1], COST_MATRIX_M2)}")
    print(f"SVM M2: {get_cost(svm_confusion[1], COST_MATRIX_M2)}")

    print()

    print(f"J48 2C: {get_cost(j48_confusion[2], COST_MATRIX_M2)}")
    print(f"JRip 2C: {get_cost(jrip_confusion[2], COST_MATRIX_M2)}")
    print(f"Naive Bayes 2C: {get_cost(naivebayes_confusion[2], COST_MATRIX_M2)}")
    print(f"Random Forest 2C: {get_cost(randomforest_confusion[2], COST_MATRIX_M2)}")
    print(f"SVM 2C: {get_cost(svm_confusion[2], COST_MATRIX_M2)}")


