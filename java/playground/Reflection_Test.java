import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Reflection_Test {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws NoSuchMethodException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, NoSuchMethodException {
		Class c = Class.forName("multi.Race");
		Field[] f_list = c.getFields();
		for (Field fie: f_list) System.out.println(fie.getName());
		Method[] m_list = c.getMethods();
		for (Method met: m_list) System.out.println(met.getName());
		
		//get a field by name
		Field f = c.getField("field_name");
		//get a method by name and what argument types it has
		Method m = c.getMethod("method_name", null);
		//get a method by name and what argument types it has
		Method m2 = c.getMethod("method_name", String.class, String.class, int.class);
		
		//note, only accessible fields/methods will be viewable (e.g. no private fields/methods)
		

	}

}
