#!/bin/bash

# This simply takes a file-based revision scheme in the form of '<value.*>number/char</value>' and strips out the number/char from each line.
# It assumes that the source file is named revision_scheme_raw.txt and has UNIX format EOL characters and it will create the destination file as revision_scheme.txt

while read LINE 
do
	echo "$LINE" | sed -rn 's/<value[^>]*>(.+)<\/value>/\1/p' >> ./revision_scheme.txt
done < ./revision_scheme_raw.txt
