from tree import HuffmanTree

def extract(unpadded_bitstream: str, huff_tree: HuffmanTree):
    tree_root, encoded_content_index = huff_tree
    huff_tree.set_root()

