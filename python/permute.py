#!/usr/bin/python

def permute(word, res=[]):
	if len(word) == 1:
		return word
	for char in word:
		word2 = rm_char(word, char)
		res += [char + permute(word2, res)]

def permute2(word):
	if len(word) <= 1:
		return word
	word = str_to_list(word)
	

def str_to_list(string):
	res = []
	for i in string:
		res += i
	return res

def rm_char(word, char):
	word2 = ''
	done = False
	for i in word:
		if i != char or done:
			word2 += i
		if i == char:
			done = True
	return word2

#print rm_char('coded', 'd')
print permute('abcd')
