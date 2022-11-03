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
	Log log;

	public StagingService(ConnectDatabase connectDatabase) {
		this.connectDatabase = connectDatabase;
		//config  = connectDatabase.getConfig("{call getConfig}");
		log = connectDatabase.getLog("{call get_filelog}");
	
	}

	public void process() {
		Connection connection = null;
		if (log != null) {
			if (log.getStatus().equals("ER")) {
				try {
					connection = connectDatabase.getConnection();
					CallableStatement cs = null;
					connection.setAutoCommit(false);
					// load file to staging				
					String loadData = "LOAD DATA INFILE '" +log.getPathFile()+"' \r\n"
							+ "INTO TABLE staging_temp\r\n"
							+ "FIELDS TERMINATED BY ',' \r\n"
							+ "ENCLOSED BY '\"'\r\n"
							+ "LINES TERMINATED BY '\\r\\n'\r\n"
							+ "IGNORE 1 ROWS\r\n"
							+ "(province,issue_date,prize0,prize1,prize2,prize3,prize4,prize5,prize6,prize7,prize8)\r\n"
							;
					//System.out.println(loadData);
					cs = connection.prepareCall(loadData);
					cs.execute();
					
					// update status file_log
					String sqlUpdate = "update file_log set status = 'TR' where id = " + log.getId();
					cs = connection.prepareCall(sqlUpdate);
					cs.execute();
				
					
					// map issue_date to date_dim
					String sqlUpdate1 = "update staging join date_dim on staging.issue_date=date_dim.full_date\r\n"
							+ "set staging.date_sk = date_dim.date_sk;";
					cs = connection.prepareCall(sqlUpdate1);
					cs.execute();
					
//					 
					String sqlUpdate3 = "update staging join province on staging_temp.province=province.name_provinces\r\n"
							+ "set staging_temp.nkey = province.code_region;";
					cs = connection.prepareCall(sqlUpdate3);
					cs.execute();
					

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
	}



}
