package fatec.com.digital_library.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private Connection connection = null;

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String serverName = "localhost";
			String mydatabase = "library";
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "root";
			String password = "123456";
			
			connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Teste");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void main(String args[]){
		DatabaseConnection dbcon= new DatabaseConnection();
		dbcon.getConnection();
	}
}
