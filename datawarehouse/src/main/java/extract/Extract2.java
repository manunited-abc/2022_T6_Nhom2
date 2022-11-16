package extract;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dao.ConnectDatabase;
import model.Config;
import model.Lottery;

public class Extract2 implements IExtract {
	Config config;
	Document document;
	// ConnectDatabase connectDatabase;

	public Extract2(Config config) {
		this.config = config;

		// Câu query lấy các record trong table file_configuration

	}

	public List<Lottery> extract() throws IOException {
		List<Lottery> lotteries = new ArrayList<>();
		Lottery lottery = extractNorth();
		lotteries.add(lottery);
		List<Lottery> list1 = extractCenter();
		List<Lottery> list2 = extractSouth();
		lotteries.addAll(list1);
		lotteries.addAll(list2);
		System.out.println("Extract 2 OK");
		return lotteries;

	}

	public List<Lottery> extractSouth() throws IOException {
		document = Jsoup.connect(config.getSourceData()).get();
		Elements tables = document.getElementsByClass("bkqmiennam");
		String dateStr = tables.get(0).getElementsByClass("ngay").get(0).text();
		int size = tables.get(0).getElementsByClass("tinh").size();
		List<Lottery> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Lottery lottery = new Lottery();
			lottery.setRelaseDate(formatDate(dateStr));
			lottery.setProvince(tables.get(0).getElementsByClass("tinh").get(i).text());
			lottery.setPrize8(tables.get(0).getElementsByClass("giai8").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize7(tables.get(0).getElementsByClass("giai7").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize6(tables.get(0).getElementsByClass("giai6").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize5(tables.get(0).getElementsByClass("giai5").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize4(tables.get(0).getElementsByClass("giai4").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize3(tables.get(0).getElementsByClass("giai3").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize2(tables.get(0).getElementsByClass("giai2").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize1(tables.get(0).getElementsByClass("giai1").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize0(tables.get(0).getElementsByClass("giaidb").get(i + 1).text().replaceAll(" ", "-"));
			list.add(lottery);

		}
		return list;

	}

	public List<Lottery> extractCenter() throws IOException {
		document = Jsoup.connect(config.getSourceData()).get();
		Elements tables = document.getElementsByClass("bkqmiennam");
		String dateStr = tables.get(2).getElementsByClass("ngay").get(0).text();
		int size = tables.get(2).getElementsByClass("tinh").size();
		List<Lottery> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Lottery lottery = new Lottery();
			lottery.setRelaseDate(formatDate(dateStr));
			lottery.setProvince(tables.get(2).getElementsByClass("tinh").get(i).text());
			lottery.setPrize8(tables.get(2).getElementsByClass("giai8").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize7(tables.get(2).getElementsByClass("giai7").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize6(tables.get(2).getElementsByClass("giai6").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize5(tables.get(2).getElementsByClass("giai5").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize4(tables.get(2).getElementsByClass("giai4").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize3(tables.get(2).getElementsByClass("giai3").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize2(tables.get(2).getElementsByClass("giai2").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize1(tables.get(2).getElementsByClass("giai1").get(i + 1).text().replaceAll(" ", "-"));
			lottery.setPrize0(tables.get(2).getElementsByClass("giaidb").get(i + 1).text().replaceAll(" ", "-"));
			list.add(lottery);

		}
		return list;

	}

	public Lottery extractNorth() throws IOException {
		String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		document = Jsoup.connect(config.getSourceData() + "/kqxs/mien-bac/" + currentDate + ".html").get();
		Elements tables = document.getElementsByClass("bkqtinhmienbac");
		Lottery lottery = new Lottery();
		lottery.setProvince(document.getElementsByClass("title").get(0).getElementsByTag("a").get(0).text()
				.replaceAll("KẾT QUẢ XỔ SỐ ", ""));
		lottery.setRelaseDate(formatDate( tables.get(0).getElementsByClass("ngay").get(0).getElementsByTag("a").get(0).text()
				));
		lottery.setPrize0(tables.get(0).getElementsByClass("giaidb").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize1(tables.get(0).getElementsByClass("giai1").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize2(tables.get(0).getElementsByClass("giai2").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize3(tables.get(0).getElementsByClass("giai3").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize4(tables.get(0).getElementsByClass("giai4").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize5(tables.get(0).getElementsByClass("giai5").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize6(tables.get(0).getElementsByClass("giai6").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize7(tables.get(0).getElementsByClass("giai7").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize8("");
		return lottery;

	}

	public String formatDate(String date) {
		String[] split = date.split("/");
		int day = Integer.parseInt(split[0]);
		int month = Integer.parseInt(split[1]);
		int year = Integer.parseInt(split[2]);
		return year + "-" + month + "-" + day;
	}

	public static void main(String[] args) throws IOException {

	}

}
