package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class Oracle {

	public static ResultSet readDB(String SQL, String hostname, String SID, String uname, String pword) throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+hostname+":1521:"+SID, uname, pword);
		PreparedStatement stmt = con.prepareStatement(SQL);
		ResultSet res = stmt.executeQuery();
		con.close();
		return res;
	}
	
	public static void writeDB(String SQL, String hostname, String SID, String uname, String pword) throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+hostname+":1521:"+SID, uname, pword);
		Statement stmt = con.createStatement();
		stmt.executeUpdate(SQL);
		con.close();
	}
	

	public static void main(String[] args) throws Exception{
		ResultSet res = readDB("select * from emp", "localhost", "xe", "wcadmin", "wcadmin");
		for (int i=1;i<11;i++){
			res.next();
			System.out.println(res.getString(1) + " " + res.getString(3));
		}
	}
	
}
