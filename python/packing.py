#!/usr/bin/python

def add(*nums):
	res = 0
	for num in nums:
		res += num
	return res

print add(4,9,1,0)
nums = [4,9,1,0]
print add(*nums)

def packing_example(kind, *arguments, **keywords):
	print "Hello there", kind
	print '-'*50
	for arg in arguments: print arg
	print '-'*50
	keys = keywords.keys()
	# Note that the sort() method of the list of keyword argument names is called before printing the contents 
	# of the keywords dictionary; if this is not done, the order in which the arguments are printed is undefined. 
	keys.sort()
	for key in keys: print key, ':', keywords[key]


packing_example('Mr. Rogers', "Test Item 1", "Test Item 2", user='user123', place='Cali', something='something else')

