import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//SQL Connction Class
public class SQLConnection 
{
	public Connection makeConnection() 
	{
		String url = "jdbc:mysql://122.32.125.124:3306/bakery?autoReconnect=true&useSSL=false";
		//String url = "jdbc:mysql://localhost:3306/bakery?autoReconnect=true&useSSL=false";
		// "jdbc:mysql://IpAddress:3306/bakery?autoReconnect=true&useSSL=false";

		String id = "root";
		String password = "1234";
		Connection con = null;

		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, id, password);
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("����̹��� ã�� �� �����ϴ�.");
		} 
		catch (SQLException e) 
		{
			System.out.println("���ῡ �����Ͽ����ϴ�. ");
		}

		return con;
	}
}
// http://zetawiki.com/wiki/MySQL_%EC%9B%90%EA%B2%A9_%EC%A0%91%EC%86%8D_%ED%97%88%EC%9A%A9