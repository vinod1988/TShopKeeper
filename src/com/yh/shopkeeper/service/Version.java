package com.yh.shopkeeper.service;

public class Version {
	private String returnCode;
	private String releaseDate;
	private String versionUpdateNews;
	private String versionUpdateUrl;
	private String indicator;
	private String remainDay;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getVersionUpdateNews() {
		return versionUpdateNews;
	}

	public void setVersionUpdateNews(String versionUpdateNews) {
		this.versionUpdateNews = versionUpdateNews;
	}

	public String getVersionUpdateUrl() {
		return versionUpdateUrl;
	}

	public void setVersionUpdateUrl(String versionUpdateUrl) {
		this.versionUpdateUrl = versionUpdateUrl;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getRemainDay() {
		return remainDay;
	}

	public void setRemainDay(String remainDay) {
		this.remainDay = remainDay;
	}
}
