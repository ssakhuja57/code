#!/bin/bash

while read LINE 
do
<<<<<<< HEAD
	echo "$LINE" | sed -rn 's/<value[^>]*>(.+)<\/value>/\1/p' >> /cygdrive/d/exe007/input/revision_scheme.txt
done < /cygdrive/d/exe007/input/revision_scheme_raw.txt
=======
	echo "$LINE" | sed -rn 's/<value[^>]*>(.+)<\/value>/\1/p' >> /cygdrive/d/input/revision_scheme.txt
done < /cygdrive/d/input/revision_scheme_raw.txt

>>>>>>> cfd9833df39116f351a1a4e363e5358d1ea771a4
