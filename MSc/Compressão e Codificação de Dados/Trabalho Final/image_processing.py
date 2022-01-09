from pathlib import Path
from typing import List
from PIL.PngImagePlugin import PngImageFile
from PIL import Image
import numpy as np

def convert_image_to_black_and_white(opened_image: PngImageFile) -> Image.Image:
    a = opened_image.convert('1')
    a.save('tmp_bw.png')
    return a
    #return opened_image.convert('1')

def open_image(file_path: Path) -> np.array:
    opened_image: PngImageFile = Image.open(file_path)
    black_white_image: Image.Image = convert_image_to_black_and_white(opened_image)

    return np.array(black_white_image).astype(int)

# https://stackoverflow.com/questions/16873441/form-a-big-2d-array-from-multiple-smaller-2d-arrays/16873755#16873755
def split_into_tiles(image: np.ndarray, n_rows: int, n_cols: int) -> np.ndarray:
    """
    Return an array of shape (n, nrows, ncols) where
    n * nrows * ncols = arr.size

    If arr is a 2D array, the returned array should look like n subblocks with
    each subblock preserving the "physical" layout of arr.
    """
    height,_ = image.shape

    return (image.reshape(height//n_rows, n_rows, -1, n_cols)
                .swapaxes(1,2)
                .reshape(-1,n_rows, n_cols))

def join_tiles(tiles_array: np.ndarray, height: int, width: int) -> np.ndarray:
    """
    Return an array of shape (h, w) where
    h * w = arr.size

    If arr is of shape (n, nrows, ncols), n sublocks of shape (nrows, ncols),
    then the returned array preserves the "physical" layout of the sublocks.
    """
    _, nrows, ncols = tiles_array.shape
    return (tiles_array.reshape(height//nrows, -1, nrows, ncols)
               .swapaxes(1,2)
               .reshape(height, width))

def tiles_to_symbols(tiles_array: np.ndarray) -> List[str]:
    input_sequence = [] 

    for tile in tiles_array:
        symbol = str(tile.flatten())[1:-1].replace(" ", "")
        input_sequence.append(symbol)

    return input_sequence
