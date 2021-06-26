from node import Node
import pandas as pd

class DecisionTree:
     def __init__(self, purity_measure='entropy', prune=False) -> None:
         self.root = Node()
         self.purity_measure = purity_measure
         self.prune = prune

    