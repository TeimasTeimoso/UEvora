import argparse
from pathlib import Path
from compress import compress, write_to_file
from image_processing import open_image, split_into_tiles, tiles_to_symbols
from utils import create_leafs
from tree import HuffmanTree

if __name__ == '__main__':

    args_parser = argparse.ArgumentParser()

    args_parser.add_argument('--compress', action='store_true', help='Flag to compress file')
    args_parser.add_argument('--extract', action='store_true', help='Flag to extract file')
    args_parser.add_argument('-f', action='store', dest='file_path', type=str, required=True, help='Path of the file to compress/extract')
    args = args_parser.parse_args()

    file_path: Path = Path(args.file_path)

    if args.compress:
        raw_image = open_image(file_path)
        heigth, width = raw_image.shape

        tiled_image = split_into_tiles(raw_image, 2, 2)
        input_sequence = tiles_to_symbols(tiled_image)

        leafs = create_leafs(input_sequence)

        huffman_tree = HuffmanTree()
        huffman_tree.build_tree(leafs)

        compressed_image = compress(input_sequence, huffman_tree.get_root())
        write_to_file(file_path, heigth,width, compressed_image, huffman_tree)
    
    elif args.extract:
        print(f"Extracting {args.file_path}")
    
    else:
        print("Pass --compress or --decompress flags.")

    pass