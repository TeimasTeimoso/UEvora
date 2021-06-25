import pandas as pd

class Node:
    def __init__(self, dataset: pd.DataFrame, parent_attrib_value='', attribute='', childs=[]):
        self.dataset = dataset
        self.parent_attrib_value = parent_attrib_value
        self.attribute= attribute
        self.childs = childs
    
    def set_attribute(self, attr: str) -> None:
        self.parent_attrib_value = attr

    def set_childs(self, childs: list) -> None:
        self.childs = childs