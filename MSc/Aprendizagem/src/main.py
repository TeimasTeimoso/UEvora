import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn import tree
from decision_tree import DecisionTree

dataset = pd.read_csv('contact-lenses.csv')

attribute_list = list(dataset.columns)[:-1]
x_data, y_data = np.split(dataset, [-1], axis=1)
x_train, x_test, y_train,y_test = train_test_split(x_data, y_data)

decision_tree = DecisionTree()

decision_tree.fit(x_train, y_train, attribute_list)

print(decision_tree.score(x_test, y_test))