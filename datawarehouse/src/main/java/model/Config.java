package model;

public class Config {
	int id;
	String sourceData;
	String ip;
	String author;
	String ftp;
	String header;
	String fileName;
	String fileError;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSourceData() {
		return sourceData;
	}
	public void setSourceData(String sourceData) {
		this.sourceData = sourceData;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getFtp() {
		return ftp;
	}
	public void setFtp(String ftp) {
		this.ftp = ftp;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileError() {
		return fileError;
	}
	public void setFileError(String fileError) {
		this.fileError = fileError;
	}
	

}
