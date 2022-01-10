from typing import List
from operator import attrgetter
from node import Node
from utils import get_occurences, compute_probability

class HuffmanTree:
    def __init__(self) -> None:
        self._root = Node()
        self._symbol_table = {}

    def set_root(self, node: Node):
        self._root = node

    def get_root(self) -> Node:
        return self._root

    def build_tree(self, input_sequence: list):
        not_yet_processed_nodes: list[Node] = self.create_leafs(input_sequence)

        while len(not_yet_processed_nodes) > 1:
            sorted_probabilities: list = sorted(not_yet_processed_nodes, key=attrgetter('_probability'))

            rigth, left = sorted_probabilities[:2] 

            new_node = Node(left=left, rigth=rigth, 
                            probability=self.compute_new_probability(left, rigth))

            not_yet_processed_nodes = sorted_probabilities[2:]
            not_yet_processed_nodes.append(new_node)

        self._root = not_yet_processed_nodes[0]

        self.compute_codes(self._root, '')

    def compute_codes(self, node: Node, computed_code: str):
        if left_node := node.get_left_child():
            self.compute_codes(left_node, computed_code + '0') 
        if rigth_node := node.get_rigth_child():
            self.compute_codes(rigth_node, computed_code + '1')
        
        if symbol := node.get_symbol():
            self.store_codes(symbol, computed_code)
            print(f"symbol: {symbol} || code: {computed_code}")

    def store_codes(self, symbol: str, computed_code: str) -> None:
        self._symbol_table[symbol] = computed_code

    def get_binary_representation(self, node:Node) -> str:
        if symbol := node.get_symbol():
            node_representation = f"1{symbol}"
        else:
            node_representation = '0'

        if left_node := node.get_left_child():
            node_representation = node_representation + self.get_binary_representation(left_node)
        if rigth_node := node.get_rigth_child():
            node_representation = node_representation + self.get_binary_representation(rigth_node)

        return node_representation

    @staticmethod
    def create_leafs(input: list) -> List[Node]:
        total_number_of_chars: int = len(input)

        symbol_dict: dict = get_occurences(input)
        leafs_list: list = []

        for symbol in symbol_dict.keys():
            occurrences: int = symbol_dict.get(symbol)
            leafs_list.append(Node(symbol=symbol, 
                                probability=compute_probability(occurrences, total_number_of_chars)))

        return leafs_list

    @staticmethod
    def compute_new_probability(left_node: Node, right_node: Node) -> float:
        return left_node.get_probability() + right_node.get_probability()
