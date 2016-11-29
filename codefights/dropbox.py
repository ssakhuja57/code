import time

def fileSyncOrder(files, storageLimit, uploadSpeed, duration):
    res = []
    used = 0
    time = 0
    syncing = None
    while time <= duration:
        if syncing is not None:
            res.append(syncing)
            used += files[syncing][0]
            files[syncing] = [999999, 0]
            syncing = None
        nextIndex = getNextFileIdx(files, time, used, storageLimit)
        if nextIndex is not None:
            syncing = nextIndex
            time += files[nextIndex][0]/uploadSpeed
        else:
            time += 1
    return res
                
            
def getNextFileIdx(files, time, used, storageLimit):
    idx = 0
    nextFile = files[idx]
    if nextFile[1] > time:
        return None
    for i in range(len(files)):
        if files[i][1] > time:
            break
        if files[i][0] < nextFile[0]:
            nextFile = files[i]
            idx = i
    if nextFile[0] + used > storageLimit:
        return None
    return idx
        

files = [
    [10, 3],
    [10, 500]    
time.clock()

