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
import extract.Extract2;
import extract.Extract3;
import extract.IExtract;
import io.WriteFile;

import model.Config;
import model.Lottery;
import utils.FormatDate;

public class ExtractService extends Thread {
	ConnectDatabase connectDatabase;
	IExtract extractService;
	Config config;
	int index;

	public ExtractService(ConnectDatabase connectDatabase, int index) throws Exception {
		this.connectDatabase = connectDatabase;
		config = connectDatabase.getConfig("{call getConfig(?)}", index);
		switch (index) {
		case 2:
			extractService = new Extract1(config);
			break;
		case 3:
			extractService = new Extract2(config);
			break;
		case 4:
			extractService = new Extract3(config);
			break;
		default:
			break;
		}
	}

	public void run() {
		LocalDateTime date = LocalDateTime.now();
		LocalDate localDate = LocalDate.now();
		String dateFormat = FormatDate.convertDateToString(date);
		String fileName = dateFormat + "." + config.getFileName();
		String dirSource = config.getFtp() + fileName;
		int idConfig = config.getId();
		String header = config.getHeader();
		try {
			List<Lottery> lotteries = extractService.extract();
			List<String> lineDatas = converLotteryToString(lotteries);
			WriteFile.writeCSV(dirSource, lineDatas, header);
			connectDatabase.insertFileLog(idConfig, localDate, dirSource, "ER");
		} catch (Exception e) {
			connectDatabase.insertFileLog(idConfig, localDate, dirSource, "ERROR");
			WriteFile.writeError(e);
			e.fillInStackTrace();
		}

	}

	public List<String> converLotteryToString(List<Lottery> lotteries) {
		List<String> result = new ArrayList<>();
		for (Lottery lottery : lotteries) {
			String line = lottery.getProvince() + "," + lottery.getRelaseDate() + "," + lottery.getPrize0() + ","
					+ lottery.getPrize1() + "," + lottery.getPrize2() + "," + lottery.getPrize3() + ","
					+ lottery.getPrize4() + "," + lottery.getPrize5() + "," + lottery.getPrize6() + ","
					+ lottery.getPrize7() + "," + lottery.getPrize8();
			result.add(line);
		}
		return result;
	}
}
