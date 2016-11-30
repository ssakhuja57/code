def packageBoxing(pkg, boxes):
    res = -1
    vol = None
    pkg_sorted = sorted(pkg)
    for i in range(len(boxes)):
        box_too_big = False
        box = boxes[i]
        box_dims = sorted(box)
        for d in range(len(box_dims)):
            if pkg_sorted[d] > box_dims[d]:
                print str(pkg_sorted[d]) + ' larger than ' + str(box_dims[d])
                box_too_big = True
                break
        if box_too_big:
            continue
        box_vol = calc_vol(box)
        if res < 0 or box_vol < vol:
            res = i
            vol = box_vol

    return res
        

def calc_vol(box):
    res = 1
    for x in box:
        res = res*x
    return res
        
# pkg is an xyz of a box, boxes is a list of xyzs
