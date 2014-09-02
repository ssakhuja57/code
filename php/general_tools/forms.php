<?php

require '/var/www/lib/db/db_odbc.php';
include '/var/www/eco/includes/header.html';

/* sample values for add_form function */

$schema_name = 'ecodb';
$table_name = 'integration_test_master';
$fields = array('test_short_name', 'test_group_key', 'description', 'hint');
$data_types = array('text', 'numeric', 'text', 'text');
$required = array('test_short_name', 'test_group_key', 'description');
$labels = array('Test Name (no spaces)', 'Test Group', 'Description', 'Hint');
$input_types = array(1, 3, 2, 2);
$values = array('', 'select test_group_key, test_group_short_name from ecodb.integration_test_group order by test_group_short_name', '', '');

add_form($schema_name, $table_name, $fields, $data_types, $required, $labels, $input_types, $values);

/* end sample values for add_form function */


function add_form($schema_name, $table_name, $fields, $data_types, $required, $labels, $input_types, $values){

        $db = new db_odbc('verticamktdb', 'uidbadmin', 'uidbadmin');

        $submitted = true;

        foreach($required as $require){
                if ($_POST[$require] == ''){
                        $submitted = false;
                        break;
                }
        }

        if ($submitted){

                $values = '';

                $field_value_0 = $_POST[$fields[0]];
                $data_type_0 = $data_types[0];
                switch ($data_type_0) {
                        case 'numeric':
                                $values .= $field_value_0;
                                break;
                        case 'text':
                                $values .= "'" . $field_value_0 . "'";
                                break;
                }

                for($i=1; $i<count($fields); $i++){
                        $field_value = $_POST[$fields[$i]];
                        $data_type = $data_types[$i];
                        switch ($data_type) {
                                case 'numeric':
                                        $values .= "," . $field_value;
                                        break;
                                case 'text':
                                        $values .= ",'" . $field_value . "'";
                                        break;
                        }
                }
                $insert_query = "insert into " . $schema_name . '.' . $table_name . "(" . implode(",", $fields) . ") values(" . $values . ");";
                //echo $insert_query;
                $db->write_query($insert_query);
                echo '<h4>Test Created Successfully</h4> <br>
                        (Please do not refresh this page)';
        }

        else {

                echo '
                <style>
                table, td, th {
                border: 1px solid black;
                }
                </style>
                ';

                echo '<h3>Add ' . $table_name . '</h3>
                <br>
                <form method="POST">
                <table>';
                for($i=0; $i<count($fields); $i++){
                        $label = $labels[$i];
                        $require = in_array($fields[$i], $required) ? '*' : '';
                        $input = get_input_element($input_types[$i], $fields[$i], $values[$i]);
                        echo sprintf('<tr><td>%s%s</td><td>%s</td></tr>', $label, $require, $input);
                        echo "\n";
                }
                echo '<tr><td></td><td><input type="submit" value="Create"></td></tr>';
                echo '</table></form>';
                echo'<br>Notes:
                        <ul>
                        <li>An asterisk (*) denotes a required field.</li>
                        <li>You will see a confirmation screen upon successful submission.</li>
                        </ul>';

        }

}

function get_input_element($type, $name, $value){
        switch ($type){
                //text
                case 1:
                        return '<input type="text" name="' . $name . '">';
                //textarea
                case 2:
                        return '<textarea name="' . $name . '"></textarea>';
                //select list
                case 3:
                        $db = new db_odbc('verticamktdb', 'uidbadmin', 'uidbadmin');
                        return '<select name="' . $name . '">' . $db->get_html_options_2($value, 'test') . '</select>';
                default:
                        return 'need to specify a valid input type (integer)';
        }
}

?>


</html>
