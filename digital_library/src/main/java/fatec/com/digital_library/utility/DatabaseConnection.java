package fatec.com.digital_library.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private Connection connection = null;

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String serverName = "192.168.1.105";
			String mydatabase = "library";
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "library";
			String password = "welcome1";
			
			connection = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
