package nlu.lotteries_api.payload;

import java.sql.Date;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LotteryReponse {
	int surrigateKey;
	int naturalKey;
	String resultSpecialPizes;
	String resultFirstPrizes;
	String resultSecondPrizes;
	String resultThirdPrizes;
	String resultFourthPrizes;
	String resultFifthPrizes;
	String resultSixthPrizes;
	String resultSeventhPrizes;
	String resultEighthPrizes;
	Date issueDate;
	String province;
	String region;
}
