data = """

g fmnc wms bgblr rpylqjyrc gr zw fylb. rfyrq ufyr amknsrcpq ypc dmp. bmgle gr gl zw fylb gq glcddgagclr ylb rfyr'q ufw rfgq rcvr gq qm jmle. sqgle qrpgle.kyicrpylq() gq pcamkkclbcb. lmu ynnjw ml rfc spj. 

"""

# this function takes a letter and outputs the letter two positions after it
# examples: convert('a') -> 'c', convert('z') -> 'b'
def convert(char):
	return chr( (ord(char)-ord('a') + 2)%26 + ord('a'))

res = ""

for char in data:
	if char == " ":
		res += " "
	elif ord(char) >= ord('a') and ord(char) <= ord('z'):
		res += convert(char)


print res
