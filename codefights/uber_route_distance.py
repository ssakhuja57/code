def perfectCity(departure, destination):
    x1,y1 = departure[0], departure[1]
    x2,y2 = destination[0], destination[1]
    
    dx = shortest_dist(x1,x2)
    dy = shortest_dist(y1,y2)
    
    print 'dx: ' + str(dx)
    print 'dy: ' + str(dy)
    
    return dx + dy

        
def shortest_dist(i1,i2):
    # if just going across, then simple
    if not int(i1) == int(i2):
        return abs(i2 - i1)
    # but if staying in the same lateral block, then need to find whether to go CW or CCW
    p1 = abs(i1 - int(i1)) + abs(i2 - int(i2))
    p2 = abs(i1 - int(i1+1)) + abs(i2 - int(i2+1))
    return abs(min(p1,p2))

# departure and destination are each x,y coordinates that can be floats as well (so not just on corners of the grid)
