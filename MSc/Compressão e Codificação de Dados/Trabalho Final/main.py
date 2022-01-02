from bitstring import BitArray
import pickle

from encode import encode
from tree import HuffmanTree
from utils import create_leafs, read_input

if __name__ == '__main__':
    """
    h = HuffmanTree()

    a = [
        Node(symbol="A", probability=0.1),
        Node(symbol="B", probability=0.1),
        Node(symbol="C", probability=0.2),
        Node(symbol="D", probability=0.3),
        Node(symbol="E", probability=0.3)
        ]

    h.build_tree(a)
    """
    i = read_input('b.txt')
    leafs = create_leafs(i)

    h = HuffmanTree()
    h.build_tree(leafs)

    r = encode(i, h._symbol_table)

    print(r)

    x = h.get_binary_representation(h._root)
    print(x)
    #with open('b.bin', 'w') as f:
    #    f.write('0b'+r)

    with open('b.bin', 'wb') as f:
        b = BitArray(bin=r)
        f.write(b.tobytes())

    with open("b.bin", "ab") as myfile:
        myfile.write(pickle.dumps(h._root))


    #print('####################################')

    #b = BitArray(filename='b.bin')
    #print(b.bin)

    #with open('b.txt', 'rb') as f:
    #    a = f.read()
    #    print(loads(a)._rigth_child._symbol)