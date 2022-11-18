package nlu.lotteries_api.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import nlu.lotteries_api.payload.LotteryReponse;

public class LotteyMapper implements RowMapper<LotteryReponse> {

	@Override
	public LotteryReponse mapRow(ResultSet rs) {
		try {
			LotteryReponse lotteryReponse = new LotteryReponse();
			lotteryReponse.setSurrigateKey(rs.getInt("skey"));
			lotteryReponse.setNaturalKey(rs.getInt("nkey"));
			lotteryReponse.setIssueDate(rs.getDate("full_date"));
			lotteryReponse.setRegion(rs.getString("region"));
			lotteryReponse.setProvince(rs.getString("name_provinces"));
			lotteryReponse.setResultSpecialPizes(rs.getString("prize0"));
			lotteryReponse.setResultFirstPrizes(rs.getString("prize1"));
			lotteryReponse.setResultSecondPrizes(rs.getString("prize2"));
			lotteryReponse.setResultThirdPrizes(rs.getString("prize3"));
			lotteryReponse.setResultFourthPrizes(rs.getString("prize4"));
			lotteryReponse.setResultFifthPrizes(rs.getString("prize5"));
			lotteryReponse.setResultSixthPrizes(rs.getString("prize6"));
			lotteryReponse.setResultSeventhPrizes(rs.getString("prize7"));
			lotteryReponse.setResultEighthPrizes(rs.getString("prize8"));
			return lotteryReponse;
		} catch (SQLException e) {
			return null;
		}
	}

}
