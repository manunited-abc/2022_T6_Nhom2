package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import dao.ConnectDatabase;
import io.WriteFile;

public class DatawarehouseService {
	ConnectDatabase connectDatabase;
	public DatawarehouseService(ConnectDatabase connectDatabase) {
		this.connectDatabase = connectDatabase;
	}
	public void process() {
		Connection connection = null;		
		try {
			connection = connectDatabase.getConnection();
			CallableStatement cs = null;
			connection.setAutoCommit(false);
			String sql1 = "insert into temp select nkey from staging\r\n"
					+ "where nkey not in (select nkey from staging \r\n"
					+ "where nkey in (select nkey from lottery_fact ) and staging.date_sk  in (select date_sk from lottery_fact)) ;";
			cs = connection.prepareCall(sql1);
			cs.execute();
			String sql2 = "update lottery_fact set expired_date = CURDATE() where nkey in (select nkey from temp);";
			cs = connection.prepareCall(sql2);
			cs.execute();
			String sql3 = "insert into lottery_fact(nkey,prize0, prize1,prize2,prize3,prize4,prize5,prize6,prize7,prize8,date_sk,id_province,expired_date) \r\n"
					+ "select nkey,prize0, prize1,prize2,prize3,prize4,prize5,prize6,prize7,prize8,date_sk,id_province,expired_date from staging\r\n"
					+ "where nkey not in (select nkey from staging \r\n"
					+ "where staging.nkey in (select nkey from lottery_fact ) and staging.date_sk  in (select date_sk from lottery_fact)) ;";
			cs = connection.prepareCall(sql3);
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
