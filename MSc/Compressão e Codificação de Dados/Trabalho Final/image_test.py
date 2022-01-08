from PIL import Image
import numpy as np

im = Image.open("linda_small.png")
im_bw = im.convert('1') #convert to black and white
im_bw.save('linda_small_bw.png')

im = Image.open("linda_small_bw.png")
width, height = im.size
print(f"Width: {width} || Heigth: {height}")
print(len(list(im.getdata())))

im_np = np.array(im).astype(int)

print(im_np)

# https://stackoverflow.com/questions/16856788/slice-2d-array-into-smaller-2d-arrays
def blockshaped(arr, nrows, ncols):
    """
    Return an array of shape (n, nrows, ncols) where
    n * nrows * ncols = arr.size

    If arr is a 2D array, the returned array should look like n subblocks with
    each subblock preserving the "physical" layout of arr.
    """
    h, w = arr.shape
    return (arr.reshape(h//nrows, nrows, -1, ncols)
               .swapaxes(1,2)
               .reshape(-1, nrows, ncols))

a = blockshaped(im_np, 2, 2)
print(a[12])
print(a[12].flatten())

test = np.array([1,2,3,4])
d= test.reshape(2,2)
print(d.flatten())