from pathlib import Path
from PIL.PngImagePlugin import PngImageFile
from PIL import Image
import numpy as np

def convert_image_to_black_and_white(opened_image: PngImageFile) -> Image.Image:
    return opened_image.convert('1')

def open_image(file_path: Path) -> np.array:
    opened_image: PngImageFile = Image.open(file_path)
    black_white_image: Image.Image = convert_image_to_black_and_white(opened_image)

    return np.array(black_white_image).astype(int)

