def line_overlap(a, b):

    return not (max(a) < min(b) or max(b) < min(a))

def shape_overlap(shape1, shape2):

    dimensions = len(shape1[0])
    overlaps = [ False for dim in range(dimensions) ]

    for dim in range(dimensions):
        line1 = ( shape1[0][dim], shape1[1][dim] )
        line2 = ( shape2[0][dim], shape2[1][dim] )
        overlap = line_overlap(line1, line2)
        if overlap:
            overlaps[dim] = True

    if sum([1 for dim in overlaps if dim]) == dimensions:
        return True
    print overlaps
    return False


a = ((4,5,6), (7,9,9))
b = ((5,3,5), (6,6,7))

print shape_overlap(a, b)
