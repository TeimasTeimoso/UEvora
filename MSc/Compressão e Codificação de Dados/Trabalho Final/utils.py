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

    symbol_dict = get_occurences(input)
    leafs_list = []

    for symbol in symbol_dict.keys():
        occurrences = symbol_dict.get(symbol)
        leafs_list.append(Node(symbol=symbol, 
                            probability=compute_probability(occurrences, total_number_of_chars)))

    return leafs_list

