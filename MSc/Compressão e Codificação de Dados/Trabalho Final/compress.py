from PIL.PngImagePlugin import PngImageFile
from PIL import Image
from bitstring import BitArray
from pathlib import Path
from typing import List
from tree import HuffmanTree
from utils import change_file_name
import numpy as np

def convert_image_to_black_and_white(opened_image: PngImageFile) -> Image.Image:
    return opened_image.convert('1')

def open_image(file_path: Path) -> np.array:
    opened_image: PngImageFile = Image.open(file_path)
    opened_image: Image = convert_image_to_black_and_white(opened_image)
    
    return np.array(opened_image).astype(np.uint8)

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

# https://stackoverflow.com/questions/16873441/form-a-big-2d-array-from-multiple-smaller-2d-arrays/16873755#16873755
def split_into_tiles(image: np.ndarray, n_rows: int, n_cols: int) -> np.ndarray:
    """
    Return an array of shape (n, nrows, ncols) where
    n * nrows * ncols = arr.size

    If arr is a 2D array, the returned array should look like n subblocks with
    each subblock preserving the "physical" layout of arr.
    """
    height,_ = image.shape

    return (image.reshape(height//n_rows, n_rows, -1, n_cols)
                .swapaxes(1,2)
                .reshape(-1,n_rows, n_cols))


def tiles_to_symbols(tiles_array: np.ndarray) -> List[str]:
    input_sequence = [] 

    for tile in tiles_array:
        symbol = str(tile.flatten())[1:-1].replace(" ", "")
        input_sequence.append(symbol)

    return input_sequence

"""
[3 bits with number of tailling 0 | Number of leading 0s | 11 bits heigth | 11 bits width | content]
"""
def pad_representation(binary_representation: str) -> str:
    length_without_padding: int = len(binary_representation) + 3
    number_of_leading_zeros: int = (8-length_without_padding) % 8

    bin_number_of_zeros: str = format(number_of_leading_zeros, '03b')
    leading_zeros: str = ''.zfill(number_of_leading_zeros)

    return bin_number_of_zeros + leading_zeros + binary_representation

def compress_image(input: list, symbol_table: dict) -> str:
    compressed_data = ''
    for char in input:
        compressed_data += symbol_table.get(char)

    return compressed_data