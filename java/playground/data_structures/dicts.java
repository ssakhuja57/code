package data_structures;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class dicts {

	
	public static void main(String[] args){	
		Map<String, Integer> dict = new HashMap<String, Integer>();
		dict.put("mysql", 3306);
		dict.put("oracle", 1521);
		System.out.println(dict.get("mysql"));
		System.out.println(dict);
		for (Entry<String, Integer> entry : dict.entrySet())
			System.out.println(entry.getKey() + ":" + entry.getValue());
	}
	
	
	
}
