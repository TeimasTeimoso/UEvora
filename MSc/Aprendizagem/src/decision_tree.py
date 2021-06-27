from typing import Dict
from utils import choose_best_attribute, is_homogeneous
from node import Node
import pandas as pd

class DecisionTree:
    def __init__(self, purity_measure='entropy', prune=False) -> None:
        self._root = Node()
        self._purity_measure = purity_measure
        self._prune = prune

    @staticmethod
    def _create_children(x_data: pd.DataFrame, attribute: str) -> Dict[str, Node]:
        children = {}
        values = x_data[attribute].unique()
        
        for value in values:
            children[value] = Node(attribute=attribute, class_value=value)

        return children


    def fit(self, x_data: pd.DataFrame, y_data: pd.DataFrame, attribute_list: list):
        homogenous, value = is_homogeneous(y_data)

        if homogenous:
            return self._root.set_class(value)

        root_attribute = choose_best_attribute(x_data, y_data, attribute_list)
        self._root.set_attribute(root_attribute)

        children = self._create_children(x_data, root_attribute)
        self._root.set_children(children)

        for child in children.items():
            self.grow_tree(child[1], x_data, y_data, attribute_list)


        return 1

    def grow_tree(self, node: Node, x_data: pd.DataFrame, y_data: pd.DataFrame, attribute_list: list):
        new_x_data = x_data.loc[x_data[node.get_attribute()] == node.get_class_value()]
        desired_indexes = list(new_x_data.index)
        new_y_data = y_data.loc[desired_indexes,:]

        