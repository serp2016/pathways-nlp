package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

	public static Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		String dbURL="jdbc:mysql://127.0.0.1:3306/test";
		String Username = "root";
		String Password = "root";
		Connection lConnection = DriverManager.getConnection(dbURL, Username, Password);
		return lConnection;
	}
}
