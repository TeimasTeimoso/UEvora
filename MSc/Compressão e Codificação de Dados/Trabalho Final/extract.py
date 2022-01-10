from pathlib import Path
from utils import change_file_name
from typing import Tuple
from bitstring import BitArray
import numpy as np
from PIL import Image
from tree import HuffmanTree
from node import Node

def read_compressed_image(file_path: Path) -> str:
    representation = BitArray(filename=file_path)

    return representation.bin

def extract_dimensions(unpadded_representation: str) -> Tuple[int, int, str]:
    heigth = int(unpadded_representation[:11], 2)
    width = int(unpadded_representation[11:22], 2)
    remaining_represenation = unpadded_representation[22:]

    return heigth, width, remaining_represenation

def unpad_representation(bitstream: str) -> str:
    number_of_leading_zeros: int = int(bitstream[:3],2) # get 3 bit representation
    starting_index: int = 3+number_of_leading_zeros
    
    return bitstream[starting_index:]

def split_encoded_data(unpadded_bitstream: str) -> Tuple[Node, str]:
    if unpadded_bitstream[0] == '1':
        next_bit: int = 1
        symbol: str = unpadded_bitstream[next_bit:next_bit+4]
        return (Node(symbol=symbol), unpadded_bitstream[next_bit+4:])
    else:
        unpadded_bitstream: str = unpadded_bitstream[1:]

    left_node, remaining_bitstream = split_encoded_data(unpadded_bitstream)
    rigth_node, remaining_bitstream = split_encoded_data(remaining_bitstream)

    return (Node(left=left_node, rigth=rigth_node), remaining_bitstream)

def join_tiles(tiles_array: np.ndarray, height: int, width: int) -> np.ndarray:
    """
    Return an array of shape (h, w) where
    h * w = arr.size

    If arr is of shape (n, nrows, ncols), n sublocks of shape (nrows, ncols),
    then the returned array preserves the "physical" layout of the sublocks.
    """
    _, nrows, ncols = tiles_array.shape
    return (tiles_array.reshape(height//nrows, -1, nrows, ncols)
               .swapaxes(1,2)
               .reshape(height, width))

def symbol_to_tile(symbol: str) -> np.ndarray:
    return np.array([[symbol[0], symbol[1]], [symbol[2], symbol[3]]], np.uint8)

def save_extracted_image(file_path: Path, image_array: np.ndarray) -> None:
    file_path = change_file_name(file_path, 'pbm')
    print(image_array.shape)
    print(image_array)
    image = Image.fromarray(image_array * 255)
    image.save(file_path)

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
