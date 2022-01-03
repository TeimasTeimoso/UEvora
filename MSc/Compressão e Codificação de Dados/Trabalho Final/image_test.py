from PIL import Image

im = Image.open("lenna.png")
im_bw = im.convert('1') #convert to black and white
im_bw.save('lenna_bw.png')