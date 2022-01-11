from pathlib import Path
import numpy as np

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

def change_file_name(path: Path, extension: str) -> Path:
    str_path = str(path)
    str_path_without_ext = str_path.split('.')[0]
    
    return Path(str_path_without_ext+'.'+extension)

def compute_average_code_length(input: str, codes_dict: dict) -> float:
    average_length: float = 0

    total_chars = len(input)
    symbol_dict = get_occurences(input)

    for symbol in symbol_dict.keys():
        occurrences: int = symbol_dict.get(symbol)
        s_prob = compute_probability(occurrences, total_chars)
        s_len = len(codes_dict.get(symbol))
        average_length += s_prob * s_len

    return average_length

def compute_entropy(input: str) -> float:
    entropy: float = 0

    total_chars = len(input)
    symbol_dict = get_occurences(input)

    for symbol in symbol_dict.keys():
        occurrences: int = symbol_dict.get(symbol)
        s_prob = compute_probability(occurrences, total_chars)
        entropy += s_prob * np.log2(1/s_prob) 

    return entropy
