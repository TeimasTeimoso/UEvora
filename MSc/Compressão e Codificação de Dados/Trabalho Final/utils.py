from typing import List, Tuple
from pathlib import Path
from node import Node

def read_input(file_path) -> bytes:
    with open(file_path, 'r') as file:
        return file.read()

def get_occurences(input: bytes) -> dict:
    symbol_dict: dict = {}

    for char in input:
        if symbol_dict.get(char):
            symbol_dict[char] += 1
        else:
            symbol_dict[char] = 1
    return symbol_dict

def compute_probability(occur: int, total_items: int) -> float:
    return occur/total_items

def create_leafs(input: list) -> List[Node]:
    total_number_of_chars: int = len(input)

    symbol_dict: dict = get_occurences(input)
    leafs_list: list = []

    for symbol in symbol_dict.keys():
        occurrences: int = symbol_dict.get(symbol)
        leafs_list.append(Node(symbol=symbol, 
                            probability=compute_probability(occurrences, total_number_of_chars)))

    return leafs_list

"""
[3 bits with number of tailling 0 |  Number of leading 0s | content]
"""
def pad_representation(binary_representation: str) -> str:
    length_without_padding: int = len(binary_representation) + 3
    number_of_leading_zeros: int = (8-length_without_padding) % 8

    bin_number_of_zeros: str = format(number_of_leading_zeros, '03b')
    leading_zeros: str = ''.zfill(number_of_leading_zeros)

    return bin_number_of_zeros + leading_zeros + binary_representation

def unpad_representation(bitstream: str) -> str:
    number_of_leading_zeros: int = int(bitstream[:3],2) # get 3 bit representation
    starting_index: int = 3+number_of_leading_zeros
    
    return bitstream[starting_index:]

def split_encoded_data(unpadded_bitstream: str) -> Tuple[Node, str]:
    if unpadded_bitstream[0] == '1':
        next_bit: int = 1
        #bin_symbol: str = unpadded_bitstream[next_bit:next_bit+8]
        #symbol: str = chr(int(bin_symbol, 2))
        symbol: str = unpadded_bitstream[next_bit:next_bit+4]
        return (Node(symbol=symbol), unpadded_bitstream[next_bit+4:])
    else:
        unpadded_bitstream: str = unpadded_bitstream[1:]

    left_node, remaining_bitstream = split_encoded_data(unpadded_bitstream)
    rigth_node, remaining_bitstream = split_encoded_data(remaining_bitstream)

    return (Node(left=left_node, rigth=rigth_node), remaining_bitstream)

def change_file_name(path: Path, extension: str) -> Path:
    str_path = str(path)
    str_path_without_ext = str_path.split('.')[0]
    
    return Path(str_path_without_ext+'.'+extension)