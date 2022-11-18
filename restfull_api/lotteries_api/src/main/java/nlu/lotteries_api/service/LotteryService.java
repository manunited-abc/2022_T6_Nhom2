package nlu.lotteries_api.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nlu.lotteries_api.dao.LotteryDAO;
import nlu.lotteries_api.payload.LotteryReponse;

@Service
public class LotteryService {
	@Autowired
	LotteryDAO lotteryDAO;

	public List<LotteryReponse> getLotteryLatest(String region) {
		List<LotteryReponse> reponses = new ArrayList<>();
		LocalDateTime currneDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime currentDateTime2 = LocalDateTime.of(currneDateTime.getYear(), currneDateTime.getMonth(),
				currneDateTime.getDayOfMonth(), 19, 00, 00);
		if (currneDateTime.isAfter(currentDateTime2)) {

			String formattedString = currneDateTime.format(formatter);
			reponses = lotteryDAO.getLotteriesByRegionByIssueDate(region, formattedString);
		} else {
			currneDateTime = currneDateTime.minusDays(1);
			String formattedString = currneDateTime.format(formatter);
			reponses = lotteryDAO.getLotteriesByRegionByIssueDate(region, formattedString);
		}
		return reponses;

	}
}
