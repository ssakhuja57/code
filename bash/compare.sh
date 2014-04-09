#!/bin/bash

# just a reference to follow for comparing strings. IMPORTANT to notice then presence of spaces after/before brackets
# and after/before the comparators...

test1="abc"
test2="def"
test3=""

if [ "$test1" == "abc" ] && [ "$test2" == "def" ] && ["$test3" == "" ]; then
  echo "test passed"
fi
