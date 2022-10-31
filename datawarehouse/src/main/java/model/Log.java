package model;

import java.time.LocalDate;

public class Log {
	int id;
	LocalDate dateCrawl;
	String pathFile;
	String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDateCrawl() {
		return dateCrawl;
	}
	public void setDateCrawl(LocalDate dateCrawl) {
		this.dateCrawl = dateCrawl;
	}
	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
