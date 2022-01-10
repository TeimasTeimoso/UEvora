from pathlib import Path
from data_classes.node import Node

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