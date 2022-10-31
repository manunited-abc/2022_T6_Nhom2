package utils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class FormatDate {

	public static String convertDateToString(LocalDateTime date) {
		DateTimeFormatter fmDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return date.format(fmDateTimeFormatter);
	}
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	public static Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
