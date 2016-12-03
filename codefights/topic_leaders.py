def mostViewedWriters(topicIds, answerIds, views):
    answer_topics = {} 
    for i in range(len(answerIds)):
        for answerId in answerIds[i]:
            try:
                answer_topics[answerId] += topicIds[i]
            except KeyError:
                answer_topics[answerId] = topicIds[i]
    
    #print answer_topics
    
    topic_views_by_user = {}
    
    for view in views:
        answer_id = view[0]
        user_id = view[1]
        view_count = view[2]
        topics = answer_topics[answer_id]
        for topic_id in topics:
            if topic_id not in topic_views_by_user:
                topic_views_by_user[topic_id] = {}
            try:
                topic_views_by_user[topic_id][user_id] += view_count
            except KeyError:
                topic_views_by_user[topic_id][user_id] = view_count
    
    # add set of unviewed topics
    all_topics = []
    for topics in topicIds:
        all_topics += topics
    print all_topics
    for topic in all_topics:
        if topic not in topic_views_by_user:
            topic_views_by_user[topic] = {}
    
    print topic_views_by_user
    
    topic_leaders = {}
    for topic, user_views in topic_views_by_user.items():
        sorted_leaders = sorted(user_views.items(), key=lambda item: (item[1], -item[0]), reverse=True)
        if len(sorted_leaders) > 10:
            sorted_leaders = sorted_leaders[:10]
        topic_leaders[topic] = sorted_leaders
    
    print topic_leaders
    
    most_viewed = sorted(topic_leaders)
    #print most_viewed
    
    return [ topic_leaders[t] for t in most_viewed ]

# example input:
topicIds = [[5,6,81], 
 [1,3,2], 
 [10,12,34], 
 [13,14,23,43], 
 [11,22,17]]
answerIds = [[1,2,3], 
 [], 
 [], 
 [4,5,6,7], 
 [8,9,10,11]]
views = [[4,18,5], 
 [5,23,37], 
 [8,1,23], 
 [11,18,18], 
 [1,7,20], 
 [9,239,10], 
 [2,239,1], 
 [10,18,1], 
 [3,239,5], 
 [6,169,2], 
 [7,800,1]]
