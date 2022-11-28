package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.text.DefaultEditorKit.CopyAction;

import model.Lottery;
import utils.FormatDate;

public class WriteFile {
//	static ResourceBundle resourceBundle = ResourceBundle.getBundle("config\\config");
//	static String path = resourceBundle.getString("pathError");
	static Properties properties = new Properties();
	public static void loadConfig() {
		File currentDirFile = new File(".");
		String helper = currentDirFile.getAbsolutePath();
		try (InputStream input = new FileInputStream("E:\\Program Files\\Data Warehouse\\Project\\Data Engineer\\datawarehouse\\src\\main\\java\\config\\config.properties")) {
			// load a properties file
			properties.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public static void writeCSV(String dirSource, List<String> lineDatas, String headers) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dirSource)))) {
			bw.write(headers);
			bw.newLine();	
			for (String line : lineDatas) {
				bw.write(line);
				bw.newLine();	
			}
			bw.close();
			System.out.println("[MESSAGE]: Write to file succesfully");
		}
		
		
		
	}
	 public static void writeError(Exception e) {
	        try {
	        	loadConfig();
	        	 e.printStackTrace();
	        	 System.out.println(properties.getProperty("pathError"));
	            FileWriter fileWriter = new FileWriter(properties.getProperty("pathError"), true);
	            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	            PrintWriter printWriter = new PrintWriter(bufferedWriter, true);	
	            LocalDateTime localDateTime = LocalDateTime.now();
	            printWriter.println("["+FormatDate.convertDateToString(localDateTime)+"]");
	            e.printStackTrace(printWriter);
	           fileWriter.close();
	           bufferedWriter.close();
	           printWriter.close();
	            
	        }
	        catch (Exception ie) {
	           ie.printStackTrace();
	        }
	   }
	
}
