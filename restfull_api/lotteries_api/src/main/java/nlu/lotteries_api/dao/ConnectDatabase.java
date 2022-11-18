package nlu.lotteries_api.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class ConnectDatabase {
	ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
	Connection connection;

	public ConnectDatabase() {
		connection = getConnection();
	}

	public Connection getConnection() {
		try {
			Class.forName(resourceBundle.getString("driverName"));
			String url = resourceBundle.getString("url");
			String user = resourceBundle.getString("user");
			String password = resourceBundle.getString("password");
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) throws SQLException {
		ConnectDatabase connectDatabase = new ConnectDatabase();
		
	}
}
