package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import dao.ConnectDatabase;
import io.WriteFile;

public class DeleteStagingService {
	ConnectDatabase connectDatabase;
	public DeleteStagingService(ConnectDatabase connectDatabase) {
		this.connectDatabase = connectDatabase;
	}
	public void process() {
		Connection connection = null;		
		try {
			connection = connectDatabase.getConnection();
			CallableStatement cs = null;
			connection.setAutoCommit(false);
			String sql1 = "truncate staging";
			cs = connection.prepareCall(sql1);
			cs.execute();
			connection.commit();
			cs.close();
			connection.close();
			System.out.println("Success");
		} catch (SQLException e) {
			e.printStackTrace();					
			WriteFile.writeError(e);
			try {
				if (connection != null)
					connection.rollback();
			} catch (SQLException se2) {
				se2.printStackTrace();
			}
		}
	}
}
