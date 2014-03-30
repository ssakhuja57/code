#!/bin/bash

# this script uses find to locate all files under the current directory that match the
# given pattern and rename them with the given prefix

# important note that this will not work on any files that have a space (and possibly any other
# similar special characters in their name or path)

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
done
