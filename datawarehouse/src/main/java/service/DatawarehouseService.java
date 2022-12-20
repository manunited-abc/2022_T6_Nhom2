package service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import dao.ConnectDatabase;
import io.WriteFile;

public class DatawarehouseService {
	ConnectDatabase connectDatabase;
	public DatawarehouseService(ConnectDatabase connectDatabase) {
		//1. Kết nối database
		this.connectDatabase = connectDatabase;
	}
	public void process() {
		Connection connection = null;		
		try {
			connection = connectDatabase.getConnection();
			CallableStatement cs = null;
			connection.setAutoCommit(false);
			// 2. Cập nhật các hàng trong lottery_fact có ngày hết hạn = '9999-12-31' 
				//và nkey trùng với nkey trong staging thành ngày hết hạn = ngày hiện tại
			cs = connection.prepareCall("{call update_expired_date_lottery_fact}");
			cs.execute();
			//3. Insert các hàng trong staging vào lottery_fact
			cs = connection.prepareCall("{call insert_staging_to_lottery_fact}");
			cs.execute();
			//4. Cập nhật các hàng có status = 'TR' trong bảng file_log thành status = 'SU'
			cs = connection.prepareCall("{call update_filelog(?,?)}");
			cs.setString(1, "SU");
			cs.setString(2, "TR");
			cs.execute();
			
			connection.commit();
			cs.close();
			connection.close();
			System.out.println("Success");
		} catch (SQLException e) {
			e.printStackTrace();	
			// Ghi lỗi vào file erorr.txt
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
