package run;

import java.io.IOException;

import dao.ConnectDatabase;
import service.ExtractService;

public class RunExtract {
	public static void main(String[] args) throws IOException {
		ConnectDatabase connectDatabase = new ConnectDatabase();
		ExtractService extractService1 = new ExtractService(connectDatabase,2);
		extractService1.start();
		ExtractService extractService2 = new ExtractService(connectDatabase,3);
		extractService2.start();
	}
}
