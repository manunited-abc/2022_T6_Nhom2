package nlu.lotteries_api.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import nlu.lotteries_api.payload.LotteryReponse;

@Component
public class LotteryDAO {
	ConnectDatabase connectDatabase;
	Connection connection;
	String sqlSelect = "select lottery_fact.skey, lottery_fact.nkey, lottery_fact.prize0,lottery_fact.prize1, lottery_fact.prize2,\r\n"
			+ "		lottery_fact.prize3, lottery_fact.prize4,lottery_fact.prize5,lottery_fact.prize6,lottery_fact.prize7\r\n"
			+ "        ,lottery_fact.prize8,province_dim.name_provinces, province_dim.region ,date_dim.full_date\r\n"
			+ " from lottery_fact inner join date_dim on lottery_fact.date_sk = date_dim.date_sk\r\n"
			+ "					inner join province_dim on lottery_fact.province_sk = province_dim.skey";

	public LotteryDAO() {
		connectDatabase = new ConnectDatabase();
		connection = connectDatabase.getConnection();
	}

	public List<LotteryReponse> getLotteriesByRegionLatest(String region) {

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect
					+ "                    where   ( lottery_fact.active = 1 or  lottery_fact.active =2 ) and "
					+ "lottery_fact.expired_date = '9999-12-31' and province_dim.region = ?");
			preparedStatement.setString(1, region);
			ResultSet rs = preparedStatement.executeQuery();
			List<LotteryReponse> lotteryReponses = new ArrayList<>();
			while (rs.next()) {
				LotteryReponse lotteryReponse = getResultSet(rs);
				lotteryReponses.add(lotteryReponse);

			}
			return lotteryReponses;
		} catch (SQLException e) {
			// WriteFile.writeError(e);
			e.printStackTrace();
		}
		return null;
	}

	public List<LotteryReponse> getLotteriesByRegionByIssueDate(String region, String date) {

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect
					+ "                    where   ( lottery_fact.active = 1 or  lottery_fact.active =2 ) and "
					+ "lottery_fact.expired_date = '9999-12-31' and province_dim.region = ? and date_dim.full_date = ? ");
			preparedStatement.setString(1, region);
			preparedStatement.setString(2, date);
			ResultSet rs = preparedStatement.executeQuery();
			List<LotteryReponse> lotteryReponses = new ArrayList<>();
			while (rs.next()) {
				LotteryReponse lotteryReponse = getResultSet(rs);
				lotteryReponses.add(lotteryReponse);

			}
			return lotteryReponses;
		} catch (SQLException e) {
			// WriteFile.writeError(e);
			e.printStackTrace();
		}
		return null;
	}

	public LotteryReponse getResultLotteryByProvinceAndIssueDate(String province, String date) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect
					+ "                    where   ( lottery_fact.active = 1 or  lottery_fact.active =2 ) and "
					+ "province_dim.name_provinces = ? and date_dim.full_date = ? ");
			preparedStatement.setString(1, province);
			preparedStatement.setString(2, date);
			ResultSet rs = preparedStatement.executeQuery();
			List<LotteryReponse> lotteryReponses = new ArrayList<>();
			while (rs.next()) {
				LotteryReponse lotteryReponse = getResultSet(rs);
				lotteryReponses.add(lotteryReponse);

			}
			return lotteryReponses.get(0);
		} catch (SQLException e) {
			// WriteFile.writeError(e);
			e.printStackTrace();
		}
		return null;

	}

	public LotteryReponse getResultLotteryByProvinceLatest(String province) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect
					+ "                    where   ( lottery_fact.active = 1 or  lottery_fact.active =2 ) and "
					+ "province_dim.name_provinces = ? and lottery_fact.expired_date = '9999-12-31' ");
			preparedStatement.setString(1, province);
			ResultSet rs = preparedStatement.executeQuery();
			List<LotteryReponse> lotteryReponses = new ArrayList<>();
			while (rs.next()) {
				LotteryReponse lotteryReponse = getResultSet(rs);
				lotteryReponses.add(lotteryReponse);

			}
			return lotteryReponses.get(0);
		} catch (SQLException e) {
			// WriteFile.writeError(e);
			e.printStackTrace();
		}
		return null;

	}

	public LotteryReponse getResultSet(ResultSet rs) throws SQLException {
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
	}

	public static void main(String[] args) {
		LotteryDAO lotteryDAO = new LotteryDAO();
//		for (LotteryReponse lotteryReponse : lotteryDAO.getLotteriesByRegionByIssueDate("Miền Bắc", "2022-11-18")) {
//			System.out.println(lotteryReponse);
//		}
		System.out.println(lotteryDAO.getResultLotteryByProvinceLatest(null));
	}
}
