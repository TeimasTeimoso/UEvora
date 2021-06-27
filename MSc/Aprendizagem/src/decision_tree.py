from typing import Dict
from utils import choose_best_attribute, is_homogeneous, most_of_y
from node import Node
import pandas as pd

DEPTH_LIMIT = 5

"""
Prune can be passed pre or post
"""
class DecisionTree:
    def __init__(self, purity_measure='entropy', prune=False) -> None:
        self._root = Node()
        self._purity_measure = purity_measure
        self._prune = prune
        self._depth = 0
        self._hit = 0
      
    @staticmethod
    def _create_children(x_data: pd.DataFrame, attribute: str) -> Dict[str, Node]:
        children = {}
        values = x_data[attribute].unique()
        
        for value in values:
            children[value] = Node(attribute=attribute, class_value=value)

        return children

    def fit(self, x_data: pd.DataFrame, y_data: pd.DataFrame, attribute_list: list):
        return self.grow_tree(self._root, x_data, y_data, attribute_list)

    def grow_tree(self, node: Node, x_data: pd.DataFrame, y_data: pd.DataFrame, attribute_list: list):
        attribute = node.get_attribute()

        # if not root
        if attribute: 
            x_data = x_data.loc[x_data[node.get_attribute()] == node.get_class_value()]
            desired_indexes = list(x_data.index)
            y_data = y_data.loc[desired_indexes,:]
            if attribute in attribute_list:
                attribute_list.remove(attribute)

        homogenous, value = is_homogeneous(y_data)

        if homogenous:
            node.set_class(value)
            return node

        new_node_attribute = choose_best_attribute(x_data, y_data, attribute_list)

        if new_node_attribute:        
            node.set_attribute(new_node_attribute)

            children = self._create_children(x_data, new_node_attribute)
            node.set_children(children)

            for child in children.items():
                # increases branch depth
                self._depth += 1

                if self._prune == 'pre' and self._depth == DEPTH_LIMIT:
                    # reset branch depth
                    class_value = most_of_y(y_data)
                    node.set_class(class_value)
                    return node

                self.grow_tree(child[1], x_data, y_data, attribute_list)
        else:
            # reset branch depth
            class_value = most_of_y(y_data)
            node.set_class(class_value)

        return node
        
    def score(self, x_data: pd.DataFrame, y_data: pd.DataFrame):
        test_data_len = len(x_data)

        for entry in range(test_data_len):
            result = self.traverse_tree(x_data.iloc[entry, :], y_data.iloc[entry, :])
            self._hit += result

        return self._hit/test_data_len

    def traverse_tree(self, x_entry: pd.Series, y_entry: pd.Series) -> int:
        current_node = self._root

        while not current_node.is_leaf():
            attribute = current_node.get_attribute()
            node_children = current_node.get_children()

            x_class_value_for_attribute = x_entry[attribute]
            current_node = node_children.get(x_class_value_for_attribute)

            # a new value for an attribute appears on the training test
            if not current_node:
                return 0

        if current_node.get_class_value() == y_entry.iloc[0]:
            return 1

        return 0
        
