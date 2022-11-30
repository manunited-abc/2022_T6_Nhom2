package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ConnectDatabase;
import io.WriteFile;

import model.Config;
import model.Log;
import utils.Contanst;

public class StagingService {
	ConnectDatabase connectDatabase;
	List<Log> logs;

	public StagingService(ConnectDatabase connectDatabase) {
		this.connectDatabase = connectDatabase;
		// config = connectDatabase.getConfig("{call getConfig}");
		logs = connectDatabase.getLog("{call get_filelog}");

	}

	public void process() {
		Connection connection = null;

			try {
				connection = connectDatabase.getConnection();
				CallableStatement cs = null;
				connection.setAutoCommit(false);
				for (Log log : logs) {
					// load file to staging
					String loadData = "LOAD DATA INFILE '" + log.getPathFile() + "' \r\n" + "INTO TABLE staging\r\n"
							+ "FIELDS TERMINATED BY ',' \r\n" + "ENCLOSED BY '\"'\r\n"
							+ "LINES TERMINATED BY '\\r\\n'\r\n" + "IGNORE 1 ROWS\r\n"
							+ "(province,issue_date,prize0,prize1,prize2,prize3,prize4,prize5,prize6,prize7,prize8)\r\n";

					cs = connection.prepareCall(loadData);
					cs.execute();

				
					

				}
				cs = connection.prepareCall("{call tranform_staging}");
				cs.execute();
				
				cs = connection.prepareCall("{call update_filelog(?,?)}");
				cs.setString(1, "TR");
				cs.setString(2, "ER");
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
