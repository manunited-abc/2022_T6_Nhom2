package extract;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.WriteFile;

import model.Config;
import model.Lottery;
import model.Province;

public class Extract1 implements IExtract {
	Document document;
	// ConnectDatabase connectDatabase;
	Config config;

	public void run() {
		System.out.println("Fuck you");
	}

	public Extract1(Config config) {
		this.config = config;
		// Câu query lấy các record trong table file_configuration
	}

	public List<Lottery> extract() throws IOException {
		List<Province> provinces = getAllProvince();
		List<Lottery> lotteries = new ArrayList<>();
		for (Province province : provinces) {
			switch (province.getRegion()) {
			case "Xổ số miền Bắc":
				Lottery lottery0 = extracLotterySouth(getUrl() + "/" + province.getUrl());
				lottery0.setProvince(province.getName());
				lotteries.add(lottery0);
				break;
			case "Xổ số miền Nam":
				Lottery lottery = extractLotteryNorthAndCenter(getUrl() + "/" + province.getUrl());
				lottery.setProvince(province.getName());
				lotteries.add(lottery);
				break;
			case "Xổ số miền Trung":
				Lottery lottery2 = extractLotteryNorthAndCenter(getUrl() + "/" + province.getUrl());
				lottery2.setProvince(province.getName());
				lotteries.add(lottery2);
				break;
			default:
				break;
			}

		}
		System.out.println(" Extract 1: OK");
		return lotteries;

	}

	// 1. Lấy url website cần extract từ table file_configuration
	public String getUrl() {
		return config.getSourceData();
	}

	public int getIdUrl() {
		return config.getId();
	}

	public List<Province> getAllProvince() throws IOException {
		document = Jsoup.connect(getUrl()).get();
		List<Province> result = new ArrayList<>();
		Elements container = document.getElementsByTag("aside").first().getElementsByClass("box");
		for (int i = 0; i < 3; i++) {
			Elements liElements = container.get(i).getElementsByTag("li");
			String region = container.get(i).getElementsByClass("title-a").first().text();
			for (Element e : liElements) {
				String[] split = e.getElementsByTag("a").first().attr("href").split("/");
				Province province = new Province();
				province.setRegion(region);
				province.setUrl(split[split.length - 1]);
				province.setName(e.getElementsByTag("a").first().text());
				result.add(province);
			}
		}
		return result;

	}

	public Lottery extracLotterySouth(String url) throws IOException {

		Lottery lottery = new Lottery();
		document = Jsoup.connect(url).get();

		Element load = document.getElementsByClass("box").select("div.one-city").first();
		String date[] = load.attr("data-date").split("-");
		int year = Integer.valueOf(date[0]);
		int month = Integer.valueOf(date[1]);
		int day = Integer.valueOf(date[2]);

		String issueDate = LocalDate.of(year, month, day).toString();
		lottery.setRelaseDate(issueDate);

		Elements trElements = document.getElementsByClass("extendable").first().getElementsByTag("tr");

		lottery.setPrize0(getSerial(trElements.get(1)));
		lottery.setPrize1(getSerial(trElements.get(2)));
		lottery.setPrize2(getSerial(trElements.get(3)));
		lottery.setPrize3(getSerial(trElements.get(4)));
		lottery.setPrize4(getSerial(trElements.get(5)));
		lottery.setPrize5(getSerial(trElements.get(6)));
		lottery.setPrize6(getSerial(trElements.get(7)));
		lottery.setPrize7(getSerial(trElements.get(8)));
		lottery.setPrize8("");

		return lottery;

	}

	public Lottery extractLotteryNorthAndCenter(String url) throws IOException {

		document = Jsoup.connect(url).get();
		Element header = document.getElementsByClass("title-bor").first();
		Elements aElements = header.getElementsByTag("a");
		String split[] = aElements.get(2).text().split(" ");
		String dateString[] = split[split.length - 1].split("-");
		int day = Integer.valueOf(dateString[0]);
		int month = Integer.valueOf(dateString[1]);
		int year = Integer.valueOf(dateString[2]);
		String issueDate = LocalDate.of(year, month, day).toString();
		Elements trElements = document.getElementsByClass("extendable").first().getElementsByTag("tr");
		Lottery lottery = new Lottery();
		lottery.setRelaseDate(issueDate);
		lottery.setPrize8(getSerial(trElements.get(0)));
		lottery.setPrize7(getSerial(trElements.get(1)));
		lottery.setPrize6(getSerial(trElements.get(2)));
		lottery.setPrize5(getSerial(trElements.get(3)));
		lottery.setPrize4(getSerial(trElements.get(4)));
		lottery.setPrize3(getSerial(trElements.get(5)));
		lottery.setPrize2(getSerial(trElements.get(6)));
		lottery.setPrize1(getSerial(trElements.get(7)));
		lottery.setPrize0(getSerial(trElements.get(8)));
		return lottery;

	}

	public String getSerial(Element trowElements) {
		Elements serialsElementsPrize = trowElements.getElementsByTag("td").get(1).children();
		ArrayList<String> serialListPrize = new ArrayList<>();
		int count = 0;
		for (Element serialElement : serialsElementsPrize) {
			if (serialElement.text().isEmpty()) {
				count++;
			}
			serialListPrize.add(serialElement.text());
		}
		String serialPrize = "";
		if (count != serialListPrize.size()) {
			serialPrize = String.join("-", serialListPrize);
		}

		return serialPrize;
	}
}
