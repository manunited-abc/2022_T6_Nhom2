package run;

import dao.ConnectDatabase;
import service.DatawarehouseService;
import service.StagingService;

public class RunDatawarehouse {
	public static void main(String[] args) {
		ConnectDatabase connectDatabase = new ConnectDatabase();
		DatawarehouseService service = new DatawarehouseService(connectDatabase);
		service.process();
	}
}
