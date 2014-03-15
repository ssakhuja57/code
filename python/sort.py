#!/usr/bin/python

from random import randint

def sort_quick(L):
    if len(L) <= 1:
        return L
    pivot = L[0]
    less = []
    more = []
    equal = []
    for i in L:
        if i < pivot:
            less += [i]
        elif i > pivot:
            more += [i]
        else:
            equal += [i]
    return sort_quick(less) + equal + sort_quick(more)

#print sort_quick([7,4,0,4,2,8])


def select_quick(L, k):
    if len(L) == k:
        return L
    pivot = L[randint(0,len(L)-1)]
    less = []
    more = []
    equal = 0
    for i in L:
        if i <= pivot:
            less += [i]
        else:
            more += [i]
        if i == pivot:
            equal += 1
    length = len(less)
    if length > k:
        if length - equal < k:
            for x in range(0,length-k):
                less.remove(pivot)
        return select_quick(less, k)
    if length < k:
        return less + select_quick(more, k-length)
    return less

def median(L):
    arr = select_quick(L, (len(L)+1)/2)
    return max(arr)

#print select_quick([7,4,0,4,2,8], 3)
#print median([1,2,3])

def sort_merge(L):
    if len(L) <= 1:
        return L
    a1 = L[0:len(L)/2]
    a2 = L[len(L)/2:]
    return merge(sort_merge(a1), sort_merge(a2))

def merge(L1, L2):
    merged = []
    while len(L1) > 0 and len(L2) > 0:
        if L1[0] <= L2[0]:
            merged += [L1.pop(0)]
        else:
            merged += [L2.pop(0)]
    if len(L1) == 0:
        merged += L2
    elif len(L2) == 0:
        merged += L1
    return merged

#print sort_merge([4,2,5,1,9,9,6,10,44,3,0,90,33,2,7])

def find_intersect(L1, L2):
    L1 = sort_merge(L1)
    L2 = sort_merge(L2)
    inter = []
    i = 0
    j = 0
    while i < len(L1) and j < len(L2):
        if L1[i] == L2[j]:
            inter += [L1[i]]
            i += 1
            j += 1
        elif L1[i] < L2[j]:
            i += 1
        else:
            j += 1
    return inter

#print find_intersect([5,1,8,9,3], [4,3,5,1,9,92,0])

def sort_insertion(L):
	if len(L) <= 1: return L
	idx = 1
	value = L[idx]
	insert = idx - 1
	while True:
		if insert > 0 and L[idx] < L[insert]:
			insert -= 1
		else:
			if L[idx] < L[idx-1]:
				if L[idx] > L[insert]:
					insert += 1
				for i in range(idx-1, insert-1, -1):
					L[i+1] = L[i]
				L[insert] = value
			idx += 1
			if idx >= len(L):
				break
			value = L[idx]
			insert = idx - 1
	return L

print sort_insertion([4,2,5,1,7,9,6,7])
