import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
#from aa_t1.decision_tree import DecisionTree

dataset = pd.read_csv('weather.nominal.csv')

#decision_tree = DecisionTree(dataset)
attribute_list = list(dataset.columns)[:-1]
print(attribute_list)
x_data, y_data = np.split(dataset, [-1], axis=1)

x_train, y_train, x_test, y_test = train_test_split(x_data, y_data)
print(x_train)
print(y_train)
print(x_test)
print(y_test)