package run;

import java.io.IOException;

import dao.ConnectDatabase;
import service.ExtractService;

public class RunExtract {
	public static void main(String[] args) throws IOException {
		ConnectDatabase connectDatabase = new ConnectDatabase();
		ExtractService extractService = new ExtractService(connectDatabase);
		extractService.process();
	}
}
