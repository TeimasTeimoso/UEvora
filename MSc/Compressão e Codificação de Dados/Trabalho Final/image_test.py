from PIL import Image
import numpy as np


# https://stackoverflow.com/questions/11105375/how-to-split-a-matrix-into-4-blocks-using-numpy/11105569
def split(array, nrows, ncols):
    """Split a matrix into sub-matrices."""

    _, h = array.shape
    return (array.reshape(h//nrows, nrows, -1, ncols)
                 .swapaxes(1, 2)
                 .reshape(-1, nrows, ncols))

im = Image.open("linda_small.png")
im_bw = im.convert('1') #convert to black and white
im_bw.save('linda_small_bw.png')

im = Image.open("linda_small_bw.png")
width, height = im.size
print(f"Width: {width} || Heigth: {height}")
print(len(list(im.getdata())))

im_np = np.array(im).astype(int)
print(im_np)

a=split(im_np, 2,2)
print((a))
#print(np.shape(im_np))
#new_list = split(im_np, 2,2)
#print('a')

# nota => False = 0 = preto (ausencia de cores)
#      => True = 255 = white (combincação de todas as cores)
"""
01 23 45 67 89
01 23 45 67 89
01 23 45 67 89
01 23 45 67 89
01 23 45 67 89
01 23 45 67 89
01 23 45 67 89
01 23 45 67 89
01 23 45 67 89
01 23 45 67 89
"""