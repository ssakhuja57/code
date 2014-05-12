<?php

class db_mysql {

private $db_host;
private $db_user;
private $db_pass;
private $db_name;

function __construct($db_host, $db_user, $db_pass, $db_name){
        $this->db_host = $db_host;
        $this->db_user = $db_user;
        $this->db_pass = $db_pass;
        $this->db_name = $db_name;
        }

function read_query($query) {
        $con = mysqli_connect($this->db_host, $this->db_user, $this->db_pass, $this->db_name);
        $result = mysqli_query($con, $query) or die(mysqli_error($con));
        mysqli_close($con);

        $res_set = array();
        while ($row = mysqli_fetch_row($result))
                array_push($res_set, $row);

        $field_names = array();
        while ($fieldinfo=mysqli_fetch_field($result))
                array_push($field_names, $fieldinfo->name);

        return array($res_set, $field_names);

}

function write_query($query) {
        $con = mysqli_connect($this->db_host, $this->db_user, $this->db_pass, $this->db_name);
        mysqli_query($con, $query) or die(mysqli_error($con));
        mysqli_close($con);
}

/*
input:
- a query to execute
- a name for a css class
output:
- an html table that includes the column names in the header row and has the input
css class name specified in each element in the table
*/
function get_html_table($query, $css_class){

        $content = $this->read_query($query);
        $records = $content[0];
        $field_names = $content[1];
        $table = '';
        $table .= '<table class="' . $css_class . '">' . "\n";

        $table .= '<tr class="' . $css_class . '">' . "\n";
        foreach($field_names as $field_name)
                $table .= '<th class="'. $css_class . '">' . $field_name . '</th>' . "\n";
        $table .= '</tr>' . "\n";

        foreach($records as $record){
                $table .= '<tr class="' . $css_class . '">' . "\n";
                foreach($record as $field)
                        $table .= '<td class="' . $css_class . '">' . $field . '</td>' . "\n";
                $table .= '</tr>' . "\n";
                }
        $table .= '</table>' . "\n";
        return $table;
        }



/*
input:
- query to return list of options for select element (this should return only one column)
- name to assign to 'name' attribute for select element
- css class name for select element and each option element
output:
- html select tag with options from the output of the query
*/
function get_html_select($query, $name, $css_class){
        $options = $this->read_query($query)[0];
        $select = sprintf('<select name="%s" class="%s">', $name, $css_class) . "\n";
        foreach($options as $option)
                $select .= sprintf('<option value="%s" class="%s">%s</option>', $option[0], $css_class, $option[0]) . "\n";
        $select .= '</select>' . "\n";

        return $select;
        }

/*
Similar to above, but used for case where you want different values is to be
assigned for the value attribute of each option element. Query provided should
return two columns, the first being the value and second being the display value.
*/
function get_html_select_2($query, $name, $css_class){
        $options = $this->read_query($query)[0];
        $select = sprintf('<select name="%s" class="%s">', $name, $css_class) . "\n";
        foreach($options as $option)
                $select .= sprintf('<option value="%s" class="%s">%s</option>', $option[0], $css_class, $option[1]) . "\n";
        $select .= '</select>' . "\n";

        return $select;
        }

}

?>
