#!/bin/bash

while read LINE 
do
	echo "$LINE" | sed -rn 's/<value[^>]*>(.+)<\/value>/\1/p' >> /cygdrive/d/exe007/input/revision_scheme.txt
done < /cygdrive/d/exe007/input/revision_scheme_raw.txt