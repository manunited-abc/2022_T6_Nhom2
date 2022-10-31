package dao;

import java.util.ResourceBundle;

import javax.sql.rowset.JoinRowSet;

import org.jsoup.select.Evaluator.Id;

import io.WriteFile;

import model.Config;
import model.Log;
import utils.FormatDate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ConnectDatabase {
	ResourceBundle resourceBundle = ResourceBundle.getBundle("config\\config");
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
			WriteFile.writeError(e);
			e.printStackTrace();
			return null;
		}
	}

	public Config getConfig(String sql) {
		Config config = new Config();
		
		try {
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.execute();
			ResultSet rs = callableStatement.getResultSet();
			while(rs.next()) {
				int id = rs.getInt("id");
				String sourceData = rs.getString("source_data");
				String ftp = rs.getString("ftp");
				String header = rs.getString("header");
				String fileName = rs.getString("file_name");
				config.setId(id);
				config.setFileName(fileName);
				config.setHeader(header);
				config.setSourceData(sourceData);
				config.setFtp(ftp);
			}
			return config;
		} catch (SQLException e) {
			WriteFile.writeError(e);
			e.printStackTrace();
		}
		return null;
	}
	public Log getLog(String sql) {
		Log log = new Log();
		
		try {
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.execute();
			ResultSet rs = callableStatement.getResultSet();
			while(rs.next()) {
				int id = rs.getInt("id");
				Date date = rs.getDate("date_crawl");
				LocalDate dateCrawl =  new Date(date.getTime()).toLocalDate();
				String pathFile = rs.getString("path_file");
				String status = rs.getString("status");
				log.setDateCrawl(dateCrawl);
				log.setPathFile(pathFile);
				log.setId(id);
				log.setStatus(status);
			}
			return log;
		} catch (SQLException e) {
			WriteFile.writeError(e);
			e.printStackTrace();
		}
		return null;
	}
	public void insertFileLog(int idConfig, LocalDate dateCrawl,String fileName,String status) {
			try {
				CallableStatement callableStatement = connection.prepareCall("{call insert_filelog(?,?,?,?)}");
				callableStatement.setInt(1, idConfig);
				callableStatement.setDate(2, Date.valueOf(dateCrawl));
				callableStatement.setString(3, fileName);
				callableStatement.setString(4, status);
				callableStatement.execute();
			}catch (SQLException e) {
				WriteFile.writeError(e);
				e.printStackTrace();
			}
	}
	

}
