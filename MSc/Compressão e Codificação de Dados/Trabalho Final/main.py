import argparse
from pathlib import Path

if __name__ == '__main__':

    args_parser = argparse.ArgumentParser()

    args_parser.add_argument('--compress', action='store_true', help='Flag to compress file')
    args_parser.add_argument('--extract', action='store_true', help='Flag to extract file')
    args_parser.add_argument('-f', action='store', dest='file_path', type=str, required=True, help='Path of the file to compress/extract')
    args = args_parser.parse_args()

    file_path: Path = Path(args.file_path)

    if args.compress:
        print(f"Compressing {args.file_path}")
    elif args.extract:
        print(f"Extracting {args.file_path}")
    else:
        print("Pass --compress or --decompress flags.")

    pass