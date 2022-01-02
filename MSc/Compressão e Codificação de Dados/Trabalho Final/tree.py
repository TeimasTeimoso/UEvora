import pickle
from operator import attrgetter
from node import Node

class HuffmanTree:
    def __init__(self) -> None:
        self._root = Node()
        self._symbol_table = {}

    def build_tree(self, not_yet_processed_nodes: list):

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
        
        if s := node.get_symbol():
            self.store_codes(s, computed_code)
            print(f"symbol: {s} || code: {computed_code}")

    def store_codes(self, symbol: str, computed_code: str) -> None:
        self._symbol_table[symbol] = computed_code

    @staticmethod
    def compute_new_probability(left_node: Node, right_node: Node) -> float:
        return left_node.get_probability() + right_node.get_probability()