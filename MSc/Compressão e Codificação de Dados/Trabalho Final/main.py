from bitstring import BitArray
import pickle

from encode import encode
from tree import HuffmanTree
from utils import create_leafs, read_input, pad_representation

if __name__ == '__main__':
    i = read_input('b.txt')
    leafs = create_leafs(i)

    h = HuffmanTree()
    h.build_tree(leafs)

    r = encode(i, h._symbol_table)
    padded_r = pad_representation(r)
    print(padded_r)

    x = h.get_binary_representation(h._root)
    padded_x = pad_representation(x)
    print(padded_x)

    print("Written:")
    with open('b.bin', 'wb') as f:
        b = BitArray(bin=padded_x)
        f.write(b.tobytes())
        f.write(bytes('\n', 'utf-8'))
        bb = BitArray(bin=padded_r)
        f.write(bb.tobytes())


    #with open("b.bin", "ab") as myfile:
    #    myfile.write(pickle.dumps(h._root))


    #print('####################################')

    b = BitArray(filename='b.bin')
    print("Read:")
    print(b.bin)

    #with open('b.txt', 'rb') as f:
    #    a = f.read()
    #    print(loads(a)._rigth_child._symbol)