import pandas as pd

class Node:
    def __init__(self, class_value='', attribute='', children={}):
        self._class_value = class_value
        self._attribute= attribute
        self._children = children
    
    def set_attribute(self, attr: str) -> None:
        self._class_value = attr

    def set_children(self, children: dict) -> None:
        self._children = children

    def set_class(self, class_value: str) -> None:
        self._class_value = class_value

    def get_attribute(self) -> str:
        return self._attribute

    def get_class_value(self) -> str:
        return self._class_value