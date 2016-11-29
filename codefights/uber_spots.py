def parkingSpot(carDimensions, parkingLot, luckySpot):
    
    lucky_x1 = luckySpot[0]
    lucky_x2 = luckySpot[2]
    lucky_y1 = luckySpot[1]
    lucky_y2 = luckySpot[3]
    
    lotX = len(parkingLot)
    lotY = len(parkingLot[0])
    
    
    ranges = []
    # from the left
    ranges.append(
        (
        True,
        'left',
        range(lucky_x1, lucky_x2+1),
        range(lucky_y2+1)
        )
    )

    # from the right
    ranges.append(
        (
        True,
        'right',
        range(lucky_x1, lucky_x2+1),
        range(lotY-1, lucky_y1-1, -1)
        )
    )

    # from the top
    ranges.append(
        (
        False,
        'top',
        range(lucky_x2+1),
        range(lucky_y1, lucky_y2+1)
        )
    )

    # from the bottom
    ranges.append(
        (
        False,
        'bottom',
        range(lotX-1, lucky_x1-1, -1),
        range(lucky_y1, lucky_y2+1)
        )
    )
    
    valid = validOrientations(carDimensions, luckySpot)
    print 'horizontal/vertical validtities: ' + str(valid)
    for r_set in ranges:
        if (valid[0] and r_set[0]) or (valid[1] and not r_set[0]):
            print 'check from ' + r_set[1]
            if spotsOpen(parkingLot, r_set[2], r_set[3]):
                return True
        else:
            print 'orientation ' + str(r_set[0]) + ' not valid'
    return False
    
def validOrientations(carDimensions, luckySpot):
    horiz = False
    vert = False
    if carDimensions[0] == luckySpot[3] - luckySpot[1] + 1 and carDimensions[1] == luckySpot[2] - luckySpot[0] + 1:
        horiz = True
    if carDimensions[1] == luckySpot[3] - luckySpot[1] + 1 and carDimensions[0] == luckySpot[2] - luckySpot[0] + 1:
        vert = True
    return horiz,vert
        
    
def spotsOpen(lot, x_range, y_range):
    found = True
    for x in x_range:
        for y in y_range:
            print str(x) + ',' + str(y)
            try:
                if lot[x][y] != 0:
                    print 'blocked!'
                    found = False
                    break
            except IndexError:
                print 'dimensions error!'
                found = False
                break
        if not found:
            break
            
    if found:
        return True
    return False
    
    
# example:
carDimensions = [3, 2]
parkingLot = [[1, 0, 1, 0, 1, 0],
              [1, 0, 0, 0, 1, 0],
              [0, 0, 0, 0, 0, 1],
              [1, 0, 0, 0, 1, 1]]
luckySpot = [1, 1, 2, 3]
# should return false since though it could fit in from the bottom, it wouldn't line up with the orientation described with the lucky spot
