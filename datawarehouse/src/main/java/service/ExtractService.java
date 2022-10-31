package service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import dao.ConnectDatabase;
import extract.Extract1;
import io.WriteFile;

import model.Config;
import model.Lottery;
import utils.FormatDate;

public class ExtractService {
	ConnectDatabase connectDatabase;
	Extract1 extractService;
	Config config;

	public ExtractService(ConnectDatabase connectDatabase) throws IOException {
		this.connectDatabase = connectDatabase;
		config  = connectDatabase.getConfig("{call getConfig}");
		extractService = new Extract1(config);
		
	}

	public void process() throws IOException {
		LocalDateTime date = LocalDateTime.now();
		LocalDate localDate = LocalDate.now();
		String dateFormat = FormatDate.convertDateToString(date);
		String fileName = dateFormat+"."+config.getFileName();
		String dirSource =config.getFtp()+fileName;
		int idConfig = config.getId();
		String header = config.getHeader();
		List<Lottery> lotteries = extractService.extract();
		if (lotteries != null) {
			try {
				List<String> lineDatas = converLotteryToString(lotteries);
				WriteFile.writeCSV(dirSource,lineDatas,header);
//				File sourceFile = new File(dirSource);
//				File desFile = new File(header);
//				Files.copy(null, null);
				connectDatabase.insertFileLog(idConfig,localDate ,dirSource,"ER");
			} catch (IOException e) {
				WriteFile.writeError(e);
				e.fillInStackTrace();

			}
		}
		System.out.println("Success");
	}
	public  List<String> converLotteryToString(List<Lottery> lotteries){
		List<String> result = new ArrayList<>();
		for(Lottery lottery : lotteries) {
			String line = lottery.getProvince()+","+lottery.getRelaseDate()+","+lottery.getPrize0()+","+lottery.getPrize1()
			+","+lottery.getPrize2()+","+lottery.getPrize3()+","+lottery.getPrize4()+","+lottery.getPrize5()+","+lottery.getPrize6()
			+","+lottery.getPrize7()+","+lottery.getPrize8();
			result.add(line);
		}
		return result;
	}
}
