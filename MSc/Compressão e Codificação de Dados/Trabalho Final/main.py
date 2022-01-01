from node import Node
from tree import HuffmanTree

if __name__ == '__main__':
    h = HuffmanTree()



    a = [
        Node(symbol="A", probability=0.1),
        Node(symbol="B", probability=0.1),
        Node(symbol="C", probability=0.2),
        Node(symbol="D", probability=0.3),
        Node(symbol="E", probability=0.3)
        ]

    h.build_tree(a)