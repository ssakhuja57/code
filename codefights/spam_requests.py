import re

def spamClusterization(requests, IDs, threshold):
    cluster_membership = {}
    next_cluster = 1
    reqs_tokenized = [ tokenize(r) for r in requests ]
    for i in range(len(requests)):
        for j in range(len(requests)):
            #print str(i) + ',' + str(j)
            if i == j:
                continue
            jaccard = getJaccard(reqs_tokenized[i], reqs_tokenized[j])
            #print 'jaccard: ' + str(jaccard)
            if jaccard >= threshold:
                idi = IDs[i]
                idj = IDs[j]
                #print str(idi) + ' matches ' + str(idj)
                clusteri = cluster_membership.get(idi, None)
                clusterj = cluster_membership.get(idj, None)
                if clusteri is not None:
                    cluster = clusteri
                elif clusterj is not None:
                    cluster = clusterj
                else:
                    cluster = next_cluster
                    next_cluster += 1
                cluster_membership[idi] = cluster
                cluster_membership[idj] = cluster
            #print 'cluster state: ' + str(cluster_membership)
    
    #print cluster_membership
    cluster_ids = {}
    for i,cluster_id in cluster_membership.items():
        try:
            cluster_ids[cluster_id].append(i)
        except KeyError:
            cluster_ids[cluster_id] = [i]
    
    res = []
    for cluster in cluster_ids.values():
        res.append(sorted(cluster))
    return sorted(res, key=lambda c:c[0])
    
            

    
def getJaccard(A, B):
    intersect = len([i for i in A if i in B])
    union = len(A.union(B))
    return 1.0*intersect/union

regex = re.compile(r'[^a-z0-9 ]')
def tokenize(string):
    stripped = regex.sub('', string.lower())
    return set(stripped.split(' '))


REQUESTS = [
 "I need a new window.", 
 "I really, really want to replace my window!", 
 "Replace my window.", 
 "I want a new window.", 
 "I want a new carpet, I want a new carpet, I want a new carpet.", 
 "Replace my carpet"
]

IDS = [374, 2845, 83, 1848, 1837, 1500]
print spamClusterization(REQUESTS, IDS, 0.5)
