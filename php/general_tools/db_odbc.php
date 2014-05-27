<?php

/*Author: Shubham Sakhuja
*/

class db_odbc {

	private $dsn;
	private $user;
	private $pass;
	
	function __construct($dsn, $user, $pass){
		$this->dsn = $dsn;
		$this->user = $user;
		$this->pass = $pass;
	}
	
	/*
	input:
	- query to execute on
	output:
	- array with first element being query result as an array of arrays
		and second element being an array of field names for the query result
	*/
	function read_query($query) {
		
		$con = odbc_connect($this->dsn, $this->user, $this->pass);
		if(!$con)
			die('Connection Failed: ' . $con);
		
		$res = odbc_exec($con, $query);
		if(!$res)
			die('SQL Error');
		
		odbc_close($con);
		
		$res_set = array();
		
		while (odbc_fetch_into($res, $row)){
			array_push($res_set, $row);
			}
			
		$field_names = array();
		for ($i=1; $i<=odbc_num_fields($res); $i++)
			array_push($field_names, odbc_field_name($res, $i));
			
		return array($res_set, $field_names);
		}
	
	//executes query on db
	function write_query($query) {
	
		$con = odbc_connect($this->dsn, $this->user, $this->pass);
		if(!$con)
			die('Connection Failed: ' . $con);
		
		$res = odbc_exec($con, $query);
		$commit = odbc_exec($con, 'commit;');
		if(!$res or !$commit)
			die('SQL Error');
		
		odbc_close($con);
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
	function get_html_select($query, $css_class){
	        $options = $this->read_query($query)[0]
	        $res = '';
	        foreach($options as $option)
	                $res .= sprintf('<option value="%s" class="%s">%s</option>', $option[0], $css_class, $option[0]) . "\n";
	        
	        return $res;
	        }
	
	/*
	Similar to above, but used for case where you want different values is to be
	assigned for the value attribute of each option element. Query provided should
	return two columns, the first being the value and second being the display value.
	*/
	function get_html_select_2($query, $css_class){
	        $options = $this->read_query($query)[0];
	        $res = '';
	        foreach($options as $option)
	                $res .= sprintf('<option value="%s" class="%s">%s</option>', $option[0], $css_class, $option[1]) . "\n";
	
	        return $res;
	        }

	
	}
	
?>
