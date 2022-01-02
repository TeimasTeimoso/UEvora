from tree import HuffmanTree
from utils import split_encoded_data

def extract(unpadded_bitstream: str, huff_tree: HuffmanTree):
    tree_root, encoded_content = split_encoded_data(unpadded_bitstream)

    huff_tree.compute_codes(tree_root, '')

    print("Decode symbols")
    print(huff_tree._symbol_table)

