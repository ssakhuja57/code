# below is an example of how to capture the output values of a sql script into a bash variable:

snapshot_end_date=`/opt/vertica/bin/vsql -h $target_db_host -d $target_db_name -U $target_db_username -w $target_db_pwd -At -c "select max(date) from ${target_db_star_schema}.date_dim where date <= (select now() from dual) and weekday='monday';"`

# where $target_db_... are the associated connection parameters

# note that the '-At' option causes the output to only be the value(s) (if there are multiple values output, the fields are delimited by
# by pipes and rows are delimited by new lines



# passing variable from shell script to vsql:

# test.sh:
test="abc"
export test
/opt/vertica/bin/vsql ... (connection params) ... -At -f test.sql

# test.sql:

\set test2 `echo $test`

insert into table123 values('aaa', :test2);

# note, a colon is used to expand the set variable in vsql
