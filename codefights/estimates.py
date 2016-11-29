def loadTimeEstimator(sizes, uploadingStart, V):

    files_count = len(sizes)
    if files_count <= 0:
        return []
    uploaded_count = 0
    uploaded_amounts = [0] * files_count
    estimates = [0] * files_count
    uploading_states = [0] * files_count # 0: waiting, 1: uploading, 2: done
    time = uploadingStart[0] #0
    
    while uploaded_count < files_count:

        # increment each uploading file
        for i in range(len(uploading_states)):
            state = uploading_states[i]
            # increment size if uploading
            if state == 1:
                uploading_now = sum([1 for state in uploading_states if state == 1])
                speed_per_file = 1.0*V/uploading_now
                #print 'upping ' + str(i) + ' by ' + str(speed_per_file)
                uploaded_amounts[i] += speed_per_file #*(time - lastTime)

        # check for state updates
        for i in range(len(uploading_states)):
            state = uploading_states[i]

            # check for waiting -> uploading
            if state == 0 and uploadingStart[i] == time:
                uploading_states[i] = 1

            # check for uploading -> done
            if state == 1 and uploaded_amounts[i] >= sizes[i]:
                uploading_states[i] = 2
                uploaded_count += 1
                estimates[i] = int(time + 0.5) # round to nearest int
                
        # print current uploaded amounts
        #print 'time: ' + str(time) + ', states: ' + str(uploading_states) + ' amounts: ' + str(uploaded_amounts)

        time += 1

    return estimates

print loadTimeEstimator([35]*10, [0] + [350000]*9, 1)
