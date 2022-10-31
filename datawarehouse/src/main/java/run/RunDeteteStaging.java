package run;

import dao.ConnectDatabase;
import service.DeleteStagingService;

public class RunDeteteStaging {
	public static void main(String[] args) {
		ConnectDatabase connectDatabase = new ConnectDatabase();
		DeleteStagingService service = new DeleteStagingService(connectDatabase);
		service.process();
	}
}
