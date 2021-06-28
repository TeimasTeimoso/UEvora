import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
import argparse
from decision_tree import DecisionTree

if __name__ == '__main__':

    argparser = argparse.ArgumentParser()
    argparser.add_argument('-f', "-file", action='store', dest='file', help='name of the dataset', required=True)
    argparser.add_argument('-m', '-measure', action='store', dest='measure', help='Choose criterion, it can be information_gain or gini. By default: information_gain', default='information_gain')
    argparser.add_argument('-p', '-prune', action='store', dest='prune', help='Set prune. By default: ""', default="")
    results = argparser.parse_args()

    dataset = pd.read_csv(f'datasets/{results.file}')

    attribute_list = list(dataset.columns)[:-1]
    x_data, y_data = np.split(dataset, [-1], axis=1)
    x_train, x_test, y_train,y_test = train_test_split(x_data, y_data)

    decision_tree = DecisionTree(results.measure, results.prune)

    decision_tree.fit(x_train, y_train, attribute_list)

    print(decision_tree.score(x_test, y_test))