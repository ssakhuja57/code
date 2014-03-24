# pool of variable depth:
#
# depths is expected to be a list that contains the consecutive depths of the pool, note that the depth at a given
# index should be the height of the limit approached from the right end of the line segment at that index.

# this function trims off any existing flat line at the end of the pool
def clean_pool(depths):
    length = len(depths)
    last = depths[length - 1]
    for i in range(length-2, -1, -1):
        if depths[i] == last:
            depths.pop()
        else:
            break

def total_volume(depths):

        clean_pool(depths)
        volume = 0
        markers = []
        last_counted_marker = ()
        length = len(depths)
        for x in range(1, length):
                if depths[x] < depths[x-1]:
                        marker = (x,depths[x-1])
                        markers.append(marker)
                        last_marker = marker
                elif depths[x] > depths[x-1]:
                        level = depths[x-1]
                        while True:
                                try:
                                        prev_marker = markers.pop()
                                except IndexError:
                                        break
                                if depths[x] < prev_marker[1]:
                                        markers.append(prev_marker)
                                        break
                                #print x, (prev_marker[1] - level)*(x - prev_marker[0]) #debug
                                volume += (prev_marker[1] - level)*(x - prev_marker[0])
                                level = prev_marker[1]
                                last_counted_marker = prev_marker
        if last_marker != last_counted_marker and depths[length-1] < last_marker[1]:
            volume += (depths[length-1] - depths[length-2])*(length-1 - last_marker[0])
        return volume

print total_volume([3,2,1,1,0,1,0,2,0,4,4,2,3])

