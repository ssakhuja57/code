<?php

//require '/var/www/tools/db_odbc.php';
require '/var/www/tools/db_mysql.php';


if (count($argv) != 1){
        echo 'please specify one of the following arguments, create_users or generate_permissions';
        exit(1);
        }

if ($argv[1] == 'create_users'){

        $output_file = './create_users.sql';

        /*
        To make it simple, all users will be granted usage on all schemas listed in the dss_permissions tables.
        It is okay to do this since just usage on the schema alone does not give access to its objects.
        */
        $query_create_users = "select u.uname, u.password from users u;";

        $query_grant_usage = "select s.owning_schema, u.uname
                               from users u cross join
                                (select distinct owning_schema from views) s;";

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
}


elseif ($argv[1] == 'generate_permissions'){


        $output_file = './grants.sql';


        /*
        The query below builds a table with a subquery in the from clause that joins users with
        the roles they are mapped to, and joins those roles with views those roles are mapped to.
        It then unions that with a cross (cartesian) join with all users belonging to any
        'master roles' (e.g. Leadership, Ecosystem, etc.) and all views in the views table, giving
        anyone in a 'master role' access to every view.
        */
        $query = "select * from (
                        select vrm.owning_schema, vrm.view_name, u.uname
                        from users u join user_roles ur on u.uname = ur.uname
                        join view_role_mapping vrm on ur.role_name = vrm.role_name
                        union
                        select v.owning_schema, v.view_name, ur.uname
                        from (select * from roles where master_role = 'Y') master_users
                        join user_roles ur on master_users.role_name = ur.role_name
                        cross join views v
                        ) x
                  order by owning_schema, view_name, uname;";

        $db = new db_mysql('localhost', 'root', 'pass123', 'dss_permissions');

        $result = $db->read_query($query)[0];

        shell_exec('echo "-- *** this is an auto-generated file, please do not edit *** \n" > '. $output_file);

        foreach($result as $row){
                $grant = sprintf('GRANT SELECT ON %s.%s TO %s;', $row[0], $row[1], $row[2]);
                shell_exec(sprintf('echo "%s\n" >> %s', $grant, $output_file));
                }
}

else {
        echo 'not a valid argument';
        exit(1);
        }

?>

