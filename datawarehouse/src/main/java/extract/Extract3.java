package extract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Config;
import model.Lottery;

public class Extract3 implements IExtract {
	Config config;
	Document document;

	public Extract3(Config config) {
		this.config = config;

		// Câu query lấy các record trong table file_configuration

	}

	@Override
	public List<Lottery> extract() throws Exception {
		List<Lottery> lotteries = new ArrayList<>();
		Lottery lottery = extractNorth();
		lotteries.add(lottery);
		List<Lottery> list1 = extractCenter();
		List<Lottery> list2 = extractSouth();
		lotteries.addAll(list1);
		lotteries.addAll(list2);
		System.out.println("Extract 3 OK");
		return lotteries;
	}

	public List<Lottery> extractSouth() throws Exception {
		document = Jsoup.connect(config.getSourceData()).userAgent("Chrome").get();
		Elements root = document.getElementsByClass("section");
		String dateStr = root.get(3).getElementsByClass("site-link").get(0).getElementsByTag("a").get(2).text()
				.split(" ")[1];
		int size = root.get(3).getElementsByClass("prize-col3").size();
		Element tables = root.get(3).getElementsByClass("table-result").get(0).getElementsByTag("tbody").get(0);
		List<Lottery> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Lottery lottery = new Lottery();
			lottery.setRelaseDate(formatDate(dateStr));
			lottery.setProvince(root.get(3).getElementsByClass("prize-col3").get(i).text());
			lottery.setPrize8(
					tables.getElementsByTag("tr").get(0).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize7(
					tables.getElementsByTag("tr").get(1).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize6(
					tables.getElementsByTag("tr").get(2).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize5(
					tables.getElementsByTag("tr").get(3).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize4(
					tables.getElementsByTag("tr").get(4).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize3(
					tables.getElementsByTag("tr").get(5).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize2(
					tables.getElementsByTag("tr").get(6).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize1(
					tables.getElementsByTag("tr").get(7).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize0(
					tables.getElementsByTag("tr").get(8).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			list.add(lottery);

		}
		return list;
	}

	public List<Lottery> extractCenter() throws Exception {
		document = Jsoup.connect(config.getSourceData()).userAgent("Chrome").get();
		Elements root = document.getElementsByClass("section");
		String dateStr = root.get(4).getElementsByClass("site-link").get(0).getElementsByTag("a").get(2).text()
				.split(" ")[1];
		int size = root.get(4).getElementsByClass("prize-col2").size();
		Element tables = root.get(4).getElementsByClass("table-result").get(0).getElementsByTag("tbody").get(0);
		List<Lottery> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Lottery lottery = new Lottery();
			lottery.setRelaseDate(formatDate(dateStr));
			lottery.setProvince(root.get(4).getElementsByClass("prize-col2").get(i).text());
			lottery.setPrize8(
					tables.getElementsByTag("tr").get(0).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize7(
					tables.getElementsByTag("tr").get(1).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize6(
					tables.getElementsByTag("tr").get(2).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize5(
					tables.getElementsByTag("tr").get(3).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize4(
					tables.getElementsByTag("tr").get(4).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize3(
					tables.getElementsByTag("tr").get(5).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize2(
					tables.getElementsByTag("tr").get(6).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize1(
					tables.getElementsByTag("tr").get(7).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			lottery.setPrize0(
					tables.getElementsByTag("tr").get(8).getElementsByTag("td").get(i).text().replaceAll(" ", "-"));
			list.add(lottery);

		}
		return list;
	}

	public Lottery extractNorth() throws Exception {
		document = Jsoup.connect(config.getSourceData()).userAgent("Chrome").get();
		Elements root = document.getElementsByClass("section");
		String dateStr = root.get(1).getElementsByClass("site-link").get(0).getElementsByTag("a").get(2).text()
				.split(" ")[1];
		Element tables = root.get(1).getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);

		Lottery lottery = new Lottery();
		lottery.setRelaseDate(formatDate(dateStr));
		String p = root.get(1).getElementsByClass("site-link").get(0).text();
		lottery.setProvince(p.split("\\(")[1].replaceAll("\\)", "").trim());
		lottery.setPrize0(
				tables.getElementsByTag("tr").get(1).getElementsByTag("td").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize1(
				tables.getElementsByTag("tr").get(2).getElementsByTag("td").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize2(
				tables.getElementsByTag("tr").get(3).getElementsByTag("td").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize3(
				tables.getElementsByTag("tr").get(4).getElementsByTag("td").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize4(
				tables.getElementsByTag("tr").get(5).getElementsByTag("td").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize5(
				tables.getElementsByTag("tr").get(6).getElementsByTag("td").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize6(
				tables.getElementsByTag("tr").get(7).getElementsByTag("td").get(0).text().replaceAll(" ", "-"));
		lottery.setPrize7(
				tables.getElementsByTag("tr").get(8).getElementsByTag("td").get(0).text().replaceAll(" ", "-"));
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


}
