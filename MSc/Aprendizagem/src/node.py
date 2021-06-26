import pandas as pd

class Node:
    def __init__(self, dataset='', class_value='', attribute='', childs={}):
        self._dataset = dataset
        self._class_value = class_value
        self._attribute= attribute
        self._childs = childs
    
    def set_attribute(self, attr: str) -> None:
        self._class_value = attr

    def set_childs(self, childs: dict) -> None:
        self._childs = childs

    def set_class(self, class_value):
        self._class_value = class_value