#!/usr/bin/python

def add(*nums):
	res = 0
	for num in nums:
		res += num
	return res

print add(4,9,1,0)
nums = [4,9,1,0]
print add(*nums)

def cheeseshop(kind, *arguments, **keywords):
	print "-- Do you have any", kind, '?'
	print "-- I'm sorry, we're all out of", kind
	for arg in arguments: print arg
	print '-'*40
	keys = keywords.keys()
	# Note that the sort() method of the list of keyword argument names is called before printing the contents 
	# of the keywords dictionary; if this is not done, the order in which the arguments are printed is undefined. 
	keys.sort()
	for kw in keys: print kw, ':', keywords[kw]


cheeseshop('Limburger', "It's very runny, sir.",
       "It's really very, VERY runny, sir.",
       client='John Cleese',
       shopkeeper='Michael Palin',
       sketch='Cheese Shop Sketch')

