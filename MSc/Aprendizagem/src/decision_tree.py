from src.aux import is_homogeneous
from node import Node
import pandas as pd

class DecisionTree:
     def __init__(self, purity_measure='entropy', prune=False) -> None:
         self._root = Node()
         self._purity_measure = purity_measure
         self._prune = prune

     def fit(self, x_data, y_data, attribute_list):
         homogenous, value = is_homogeneous(y_data)

         if homogenous:
            return self._root.set_class(value)

        #root_attribute = 