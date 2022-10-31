package run;

import dao.ConnectDatabase;
import service.ExtractService;
import service.StagingService;

public class RunStaging {
public static void main(String[] args) {
	ConnectDatabase connectDatabase = new ConnectDatabase();
	StagingService service = new StagingService(connectDatabase);
	service.process();
}
}
