#!/bin/bash

# this script uses find to locate all files under the current directory that match the
# given pattern and rename them with the given prefix

# important note that this will not work on any files that have a space (and possibly any other
# similar special characters in their name or path). This apparently is because of the for blah in $(...) part
# using a loop over the raw output of '...' is apparently not best practice

pattern="*.*"
prefix="test_"

for file in $(find -L . -type f -name "$pattern")
do
        parent=$(echo $file | sed "s=\(.*/\).*=\1=")
        name=$(echo $file | sed "s=.*/\(.*\)=\1=")
        # do whatever actions here... like 'echo' below could be replaced by 'mv' or 'cp'
        echo "$file" "${parent}${prefix}${name}"
        #echo $file
        #echo $parent
        #echo $name
        #echo -------
done# so I guess below is the reason that file names with spaces won't be picked up (it's from a SO user):

# The internal field separator (IFS) in bash is generally set to something like 
# $'\t\n ' (whitespace), which means that bash will assume that a space delimits tokens, 
# so it will split filenames with spaces into separate tokens to loop over rather than one filename


