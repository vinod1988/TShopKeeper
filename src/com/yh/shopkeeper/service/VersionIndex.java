package com.yh.shopkeeper.service;

public class VersionIndex {
	private String versionCode;
	private String versionNumber;
	private String deviceType;
	private String appName;
	private String installSource;
	
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getInstallSource() {
		return installSource;
	}
	public void setInstallSource(String installSource) {
		this.installSource = installSource;
	}
	
	
}
