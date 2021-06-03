import cv2
import numpy as np
import math, sys

VIDEO_SRC = 'video_2.mp4'
KERNEL = np.ones((9,9)) # creates a 9x9 matrix of 1s
ELIPTICAL_KERNEL = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (9, 9))

# These values should be dynamic
MIN_CONTOUR_WIDTH = 60
MIN_COUNTOUR_HEIGH = 60

# frame percentage to multiply for line height
FRAME_PERCENTAGE = 0.85

def get_centroid(x, y, w, h):
    x1 = int(w/2)
    y1 = int(h/2)
    cx = x + x1
    cy = y + y1
    return cx, cy

def counter(vehicle):
    centroid_y = vehicle[1][1]
    line_y = line[0][1]
    MIN_BOUND = math.ceil(line_y * 0.995)
    MAX_BOUND = math.floor( line_y * 1.015)
    if MIN_BOUND <= centroid_y <= MAX_BOUND:
        return 1
    return 0


def detect_vehicles(frame):
    # Find countours with simple approx method
    # eliminating redundant points
    contours, hierarchy = cv2.findContours(frame, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    vehicles = []
    for (i, contour) in enumerate(contours):
        (x, y, w, h) = cv2.boundingRect(contour)
        valid_contour = (w >= MIN_CONTOUR_WIDTH) and (h >= MIN_COUNTOUR_HEIGH)

        if not valid_contour:
            continue

        centroid = get_centroid(x, y, w, h)

        vehicles.append(((x, y, w, h), centroid))

    return vehicles      

def process_frame(frame, mask, line):
    global n_of_cars
    processed = frame.copy()

    cv2.line(processed, line[0], line[1], (255,0,0), 3)

    vehicles = detect_vehicles(mask)

    for (i, vehicle) in enumerate(vehicles):
        contour, centroid = vehicle
        x, y, w, h = contour

        cv2.rectangle(processed, (x, y), (x + w, y + h), (255, 0, 0), 3)
        cv2.circle(processed, centroid, 2, (0, 255, 0), 2)

        n_of_cars += counter(vehicle)

    cv2.putText(processed, 'Count: ' + str(n_of_cars), (30, 30), cv2.FONT_HERSHEY_SIMPLEX,  1, (255, 0, 0), 2, cv2.LINE_AA)

    return processed

def frame_to_grayscale(frame):
    return cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)


# implement backtrack subtraction
# This can be achieved by using frame difference
def background_subtractor(c_frame, p_frame):
    c_gray = frame_to_grayscale(c_frame)
    p_gray = frame_to_grayscale(p_frame)

    return cv2.absdiff(c_gray, p_gray)

# Note: I would really like that python had an 
# pipe operator, like Elixir
def mask_preprocessor(mask):
    # apply segmentation to the mask, by using binary threshold
    _, segmented_frame = cv2.threshold(mask, 50, 255, cv2.THRESH_BINARY)
    # apply median filter in order to remove "salt and pepper" noise
    median_filtered = cv2.medianBlur(segmented_frame, 5)

    # dilate the frame to fill and widen the space
    dilated_frame = cv2.dilate(median_filtered, KERNEL)
    filled =  cv2.morphologyEx(dilated_frame, cv2.MORPH_CLOSE, ELIPTICAL_KERNEL)

    return filled

########################################################################

capture = cv2.VideoCapture(VIDEO_SRC)

_, current_frame = capture.read()
previous_frame = current_frame

line_height = math.floor(current_frame.shape[0] * FRAME_PERCENTAGE)
line = ((0, line_height), (current_frame.shape[1], line_height))

n_of_cars = 0
res = None

while capture.isOpened():

    if res is False:
        sys.exit(0)

    foreground_mask = background_subtractor(current_frame, previous_frame)

    cv2.imshow('Framme Difference', foreground_mask)

    tr = mask_preprocessor(foreground_mask)
    cv2.imshow('Pre-Processed', tr)

    processed = process_frame(current_frame, tr, line)
    cv2.imshow('Processed', processed)

    if cv2.waitKey(25) & 0xFF == ord('q'):
        break

    previous_frame = current_frame.copy()
    res, current_frame = capture.read()

capture.release()
cv2.destroyAllWindows()