package com.mfcs.parser.objects;

public class RecordData {
	private String fileName;
	private String imageLocation;
	private String xmlLocation;
	private String jsonLocation;
	
	
	public RecordData(String fileName, String imageLocation, String xmlLocation, String jsonLocation) {
		super();
		this.fileName = fileName;
		this.imageLocation = imageLocation;
		this.xmlLocation = xmlLocation;
		this.jsonLocation = jsonLocation;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getImageLocation() {
		return imageLocation;
	}
	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	public String getXmlLocation() {
		return xmlLocation;
	}
	public void setXmlLocation(String xmlLocation) {
		this.xmlLocation = xmlLocation;
	}
	public String getJsonLocation() {
		return jsonLocation;
	}
	public void setJsonLocation(String jsonLocation) {
		this.jsonLocation = jsonLocation;
	}
	
}
