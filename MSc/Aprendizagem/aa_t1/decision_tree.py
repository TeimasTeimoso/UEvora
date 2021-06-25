from node import Node
import pandas as pd

class DecisionTree:
     def __init__(self, dataset:pd.DataFrame) -> None:
         self.root = Node(dataset)