from tree import HuffmanTree
from utils import split_encoded_data

def extract(unpadded_bitstream: str, huff_tree: HuffmanTree):
    tree_root, encoded_content = split_encoded_data(unpadded_bitstream)

    huff_tree.compute_codes(tree_root, '')

    print("Decode symbols")
    print(huff_tree._symbol_table)

    decoded_message = ''
    current_node = tree_root

    for bit in encoded_content:
        if symbol := current_node.get_symbol():
            decoded_message += symbol
            current_node = tree_root

        if bit == '0':
            current_node = current_node.get_left_child()
        elif bit == '1':
            current_node = current_node.get_rigth_child()

    decoded_message += current_node.get_symbol()    # the last node will have the last symbol

    return decoded_message