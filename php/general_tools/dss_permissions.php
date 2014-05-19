<?php

//require '/var/www/tools/db_odbc.php';
require '/var/www/tools/db_mysql.php';

$output_file = './create_users.sql'

/*
To make it simple, all users will be granted usage on all schemas listed in the dss_permissions tables.
It is okay to do this since just usage on the schema alone does not give access to its objects.
*/
$query_create_users = "select u.uname, u.password from users u;"

$query_grant_usage = "select s.owning_schema, u.uname
                        from users u cross join
                        (select distinct owning_schema from views) s;"

$db = new db_mysql('localhost', 'root', 'pass123', 'dss_permissions');

$result_create_users = $db->read_query($query_create_users)[0];

shell_exec('echo "-- *** this is an auto-generated file, please do not edit *** \n" > '. $output_file);

foreach($result_create_users as $row){
        $create = sprintf('CREATE USER %s IDENTIFIED BY \'%s\';', $row[0], $row[1]);
        shell_exec(sprintf('echo "%s\n" >> %s', $create, $output_file));
        }

foreach($result_grant_usage as $row){
        $grant = sprintf('GRANT USAGE ON SCHEMA %s TO %s;', $row[0], $row[1]);
        shell_exec(sprintf('echo "%s\n" >> %s', $grant, $output_file));
        }

?>
