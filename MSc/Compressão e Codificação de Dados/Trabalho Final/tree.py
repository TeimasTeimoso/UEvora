from os import read
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
        
        if symbol := node.get_symbol():
            self.store_codes(symbol, computed_code)
            print(f"symbol: {symbol} || code: {computed_code}")

    def store_codes(self, symbol: str, computed_code: str) -> None:
        self._symbol_table[symbol] = computed_code

    # Done
    """
    def get_binary_representation(self, node:Node) -> str:
        left_node_representation = ''
        rigth_node_representation = ''

        if left_node := node.get_left_child():
            left_node_representation = self.get_binary_representation(left_node)
        if rigth_node := node.get_rigth_child():
            rigth_node_representation = self.get_binary_representation(rigth_node)

        if symbol := node.get_symbol():
            # doing it this way because of symolbs, but for images it wont be needed
            ascii_value: int = ord(symbol)
            bin_ascii_value: str = format(ascii_value, '08b') # 1 byte
            node_representation = f"1{bin_ascii_value}"
        else:
            node_representation = '0'

        return left_node_representation + rigth_node_representation + node_representation
    """

    def get_binary_representation(self, node:Node) -> str:
        if symbol := node.get_symbol():
            # doing it this way because of symolbs, but for images it wont be needed
            ascii_value: int = ord(symbol)
            bin_ascii_value: str = format(ascii_value, '08b') # 1 byte
            node_representation = f"1{bin_ascii_value}"
        else:
            node_representation = '0'

        if left_node := node.get_left_child():
            node_representation = node_representation + self.get_binary_representation(left_node)
        if rigth_node := node.get_rigth_child():
            node_representation = node_representation + self.get_binary_representation(rigth_node)

        return node_representation

    @staticmethod
    def compute_new_probability(left_node: Node, right_node: Node) -> float:
        return left_node.get_probability() + right_node.get_probability()


    def get_encoded_data(self, bitstream: str) -> str:
        number_of_leading_zeros = int(bitstream[:3],2) # get 3 bit representation
        starting_index = 3+number_of_leading_zeros
        unpadded_bitstream = bitstream[starting_index:]

    def read_tree(self, unpadded_bitstream: str, index: int) -> Node:
        if unpadded_bitstream[index] == '1':
            index += 1
            bin_symbol = unpadded_bitstream[index:index+8]
            symbol = chr(int(bin_symbol, 2))
            return (Node(symbol=symbol), index+7)

        left_node, index = self.read_tree(unpadded_bitstream, index+1)
        rigth_node, index = self.read_tree(unpadded_bitstream, index+1)

        return (Node(left=left_node, rigth=rigth_node), index)



