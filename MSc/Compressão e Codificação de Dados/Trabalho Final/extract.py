from tree import HuffmanTree
from pathlib import Path
from utils import change_file_name, split_encoded_data, unpad_representation
from typing import Tuple
from bitstring import BitArray
import numpy as np
from PIL import Image

def extract(unpadded_bitstream: str, huff_tree: HuffmanTree) -> np.ndarray:
    tree_root, encoded_content = split_encoded_data(unpadded_bitstream)

    huff_tree.compute_codes(tree_root, '')

    print("Decode symbols")
    print(huff_tree._symbol_table)

    decoded_message = []
    current_node = tree_root

    for bit in encoded_content:
        if symbol := current_node.get_symbol():
            tiled_symbol = symbol_to_tile(symbol)
            decoded_message.append(tiled_symbol)
            current_node = tree_root

        if bit == '0':
            current_node = current_node.get_left_child()
        elif bit == '1':
            current_node = current_node.get_rigth_child()

    last_symbol = current_node.get_symbol()    # the last node will have the last symbol
    tiled_symbol = symbol_to_tile(last_symbol)
    decoded_message.append(tiled_symbol)

    return np.asarray(decoded_message)

def extract_dimensions(unpadded_representation: str) -> Tuple[int, int, str]:
    heigth = int(unpadded_representation[:11], 2)
    width = int(unpadded_representation[11:22], 2)
    remaining_represenation = unpadded_representation[22:]

    return heigth, width, remaining_represenation
    
def read_compressed_image(file_path: Path) -> str:
    representation = BitArray(filename=file_path)

    return representation.bin

def symbol_to_tile(symbol: str) -> np.ndarray:
    return np.array([[symbol[0], symbol[1]], [symbol[2], symbol[3]]], np.uint8)

def save_extracted_image(file_path: Path, image_array: np.ndarray) -> None:
    file_path = change_file_name(file_path, 'png')
    print(image_array.shape)
    print(image_array)
    image = Image.fromarray(image_array * 255)
    image.save(file_path)