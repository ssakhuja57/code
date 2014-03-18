# need to fix end case, e.g. if it ended in 4423, it won't be right, could trim off ending flat line and handle one case only, or something else...

# pool of variable depth:
#
# depths is expected to be a list that contains the consecutive depths of the pool, note that the depth at a given
# index should be the height of the lower end of the vertical line segment at that index.

def total_volume(depths):

        # ensure that depths ends in a flat line, so no special clause needed for end:
        # depths.append(depths[len(depths)-1])

        volume = 0
        markers = []
        for x in range(1, len(depths)):
                if depths[x] < depths[x-1]:
                        markers.append((x,depths[x-1]))
                elif depths[x] > depths[x-1]:
                        level = depths[x-1]
                        while True:
                                try:
                                        prev_marker = markers.pop()
                                except IndexError:
                                        break
                                if depths[x] < prev_marker[1]:
                                        # <specialcase>
                                        # if it is end of pool
                                        #if x == len(depths) - 1:
                                        #       volume += (depths[x] - depths[x-1])*(x - prev_marker[0])
                                        #       return volume
                                        # </specialcase>
                                        markers.append(prev_marker)
                                        break
                                #print x, (prev_marker[1] - level)*(x - prev_marker[0]) #debug
                                volume += (prev_marker[1] - level)*(x - prev_marker[0])
                                level = prev_marker[1]
        #try:
        #       prev_marker = markers.pop()
        #except IndexError:
        #       return volume
        #if depths[len(depths)-1] < 
        return volume

print total_volume([3,2,1,1,0,1,0,2,0,4,4,2,3])
