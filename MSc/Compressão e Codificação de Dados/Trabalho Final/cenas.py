from image_processing import open_image
from utils import get_occurences

raw_image = open_image('sauron.pbm')
s = get_occurences(raw_image)


