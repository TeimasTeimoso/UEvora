import argparse
from pathlib import Path
from operations.compress import compress_image, write_to_file, open_image, split_into_tiles, tiles_to_symbols
from operations.extract import extract, extract_dimensions, read_compressed_image, save_extracted_image, join_tiles, unpad_representation
from data_classes.tree import HuffmanTree
from utils import compute_average_code_length, compute_entropy

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

        huffman_tree = HuffmanTree()
        huffman_tree.build_tree(input_sequence)

        average_code_length = compute_average_code_length(input_sequence, huffman_tree._symbol_table)
        entropy = compute_entropy(input_sequence)
        print(f"L(C) = {average_code_length}")
        print(f"H(X) = {entropy}")
        print(f"[H(X) <= L(C) < H(X) + 1]: {average_code_length - entropy < 1}")

        compressed_image = compress_image(input_sequence, huffman_tree._symbol_table)

        original_image_size = heigth * width
        print(f"Compression rate: {original_image_size/len(compressed_image)}")

        write_to_file(file_path, heigth,width, compressed_image, huffman_tree)
    
    elif args.extract:
        compressed_image = read_compressed_image(file_path)
        huffman_tree = HuffmanTree()

        unpadded_compressed_image = unpad_representation(compressed_image)
        heigth, width, remaining_compressed_image = extract_dimensions(unpadded_compressed_image)

        extracted_image = extract(remaining_compressed_image, huffman_tree)
        two_dimensional_image = join_tiles(extracted_image, heigth, width)

        save_extracted_image(file_path, two_dimensional_image)

        print("Image extracted successfully!")
    else:
        print("Pass --compress or --decompress flags.")