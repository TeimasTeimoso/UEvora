from typing import List
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

def create_leafs(input: bytes) -> List[Node]:
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