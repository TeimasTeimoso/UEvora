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
        """
        homogenous, value = is_homogeneous(y_data)

        if homogenous:
            return self._root.set_class(value)

        root_attribute = choose_best_attribute(x_data, y_data, attribute_list)
        self._root.set_attribute(root_attribute)

        children = self._create_children(x_data, root_attribute)
        self._root.set_children(children)

        for child in children.items():
            self.grow_tree(child[1], x_data, y_data, attribute_list)

        """
        return self.grow_tree(self._root, x_data, y_data, attribute_list)

    def grow_tree(self, node: Node, x_data: pd.DataFrame, y_data: pd.DataFrame, attribute_list: list):
        attribute = node.get_attribute()

        # if not root
        if attribute: 
            x_data = x_data.loc[x_data[node.get_attribute()] == node.get_class_value()]
            print(x_data)
            desired_indexes = list(x_data.index)
            y_data = y_data.loc[desired_indexes,:]
            print(y_data)
            if attribute in attribute_list:
                attribute_list.remove(attribute)

        homogenous, value = is_homogeneous(y_data)

        if homogenous:
            return node.set_class(value)

        new_node_attribute = choose_best_attribute(x_data, y_data, attribute_list)
        node.set_attribute(new_node_attribute)
        print(f'ATTR: {new_node_attribute}')

        children = self._create_children(x_data, new_node_attribute)
        node.set_children(children)

        for child in children.items():
            self.grow_tree(child[1], x_data, y_data, attribute_list)
            
        return node
        