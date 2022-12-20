package dao;

import java.util.ResourceBundle;

import javax.sql.rowset.JoinRowSet;

import org.jsoup.select.Evaluator.Id;

import io.WriteFile;

import model.Config;
import model.Log;
import utils.FormatDate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
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
import java.util.Properties;

//1 . Kết nối database 
public class ConnectDatabase {
	Properties properties = new Properties();
	Connection connection;

	public void loadConfig() {
		try (InputStream input = new FileInputStream("E:\\Program Files\\Data Warehouse\\Project\\Data Engineer\\datawarehouse\\src\\main\\java\\config\\config.properties")) {
			// load a properties file
			properties.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ConnectDatabase() {
		connection = getConnection();
	}

	public Connection getConnection() {
		loadConfig();
		try {
			Class.forName(properties.getProperty("driverName"));
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// Ghi lỗi vào file error.txt
			WriteFile.writeError(e);
			e.printStackTrace();
			return null;
		}
	}
	//2 .Lấy thông tin trong bảng file_configuration
	public Config getConfig(String sql, int ids) {
		Config config = new Config();
		try {
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, ids);
			callableStatement.execute();
			ResultSet rs = callableStatement.getResultSet();
			while (rs.next()) {
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

	public List<Log> getLog(String sql) {
		List<Log> logs = new ArrayList<>();
		try {
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.execute();
			ResultSet rs = callableStatement.getResultSet();
			while (rs.next()) {
				Log log = new Log();
				int id = rs.getInt("id");
				Date date = rs.getDate("date_crawl");
				LocalDate dateCrawl = new Date(date.getTime()).toLocalDate();
				String pathFile = rs.getString("path_file");
				String status = rs.getString("status");
				log.setDateCrawl(dateCrawl);
				log.setPathFile(pathFile);
				log.setId(id);
				log.setStatus(status);
				logs.add(log);
			}
			return logs;
		} catch (SQLException e) {
			WriteFile.writeError(e);
			e.printStackTrace();
		}
		return null;
	}

	public void insertFileLog(int idConfig, LocalDate dateCrawl, String fileName, String status) {
		try {
			CallableStatement callableStatement = connection.prepareCall("{call insert_filelog(?,?,?,?)}");
			callableStatement.setInt(1, idConfig);
			callableStatement.setDate(2, Date.valueOf(dateCrawl));
			callableStatement.setString(3, fileName);
			callableStatement.setString(4, status);
			callableStatement.execute();
		} catch (SQLException e) {
			WriteFile.writeError(e);
			e.printStackTrace();
		}
	}

}
