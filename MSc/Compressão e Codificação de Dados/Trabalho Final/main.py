from bitstring import BitArray

from compress import compress
from extract import extract
from tree import HuffmanTree
from utils import create_leafs, read_input, pad_representation, unpad_representation

if __name__ == '__main__':
    i = read_input('b.txt')
    leafs = create_leafs(i)

    h = HuffmanTree()
    h.build_tree(leafs)

    r = compress(i, h._symbol_table)
    padded_r = pad_representation(r)
    print(padded_r)

    print("Written:")
    x = h.get_binary_representation(h._root)
    padded_x = pad_representation(x)
    print(padded_x)

    with open('b.bin', 'wb') as f:
        b = BitArray(bin=padded_x)
        f.write(b.tobytes())
        bb = BitArray(bin=padded_r)
        f.write(bb.tobytes())

    b = BitArray(filename='b.bin')
    print("Read:")
    print(b.bin)

    new_huff = HuffmanTree()
    unp_bitstram = unpad_representation(b.bin)
    extract(unp_bitstram, new_huff)
    