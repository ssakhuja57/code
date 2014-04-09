
# just a reference to follow for comparing strings. IMPORTANT to notice then presence of spaces after/before brackets
# and after/before the comparators...

test1="abc"
test2="def"
test3=""

if [ "$test1" == "abc" ] && [ "$test2" == "def" ] && ["$test3" == "" ]; then
  echo "test passed"
fi

# testing if a var is null, works on numbers, should work on strings too

if [ -z $number123 ]; then
  echo "variable is null"
fi

