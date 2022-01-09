from bitstring import BitArray
from pathlib import Path
from tree import HuffmanTree
from utils import create_leafs, read_input, pad_representation, unpad_representation
from utils import change_file_name

def compress_image(input: list, symbol_table: dict) -> str:
    compressed_data = ''
    for char in input:
        compressed_data += symbol_table.get(char)

    return compressed_data

    
def write_to_file(path: Path, heigth: int, width: int, compressed_image: str, tree: HuffmanTree) -> None:
    path = change_file_name(path, 'huffman')
    
    bin_heigth = format(heigth, '011b')
    bin_width = format(width, '011b')
    tree_bin_representation = tree.get_binary_representation(tree.get_root())

    bin_content = bin_heigth + bin_width + tree_bin_representation + compressed_image

    padded_bin_content = pad_representation(bin_content)

    with open(path, 'wb') as file:
        content = BitArray(bin=padded_bin_content)
        file.write(content.tobytes())

