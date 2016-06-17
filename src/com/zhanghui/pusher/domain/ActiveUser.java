package com.zhanghui.pusher.domain;

public class ActiveUser extends Id{
	private String country;
	private Integer activeUser;
	private Integer validActiveUser;
	private Integer addUser;
	private double retentionRate;
	private String date;
	private String from;
	
	public Integer getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(Integer activeUser) {
		this.activeUser = activeUser;
	}
	public Integer getAddUser() {
		return addUser;
	}
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getRetentionRate() {
		return retentionRate;
	}
	public void setRetentionRate(double retentionRate) {
		this.retentionRate = retentionRate;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Integer getValidActiveUser() {
		return validActiveUser;
	}
	public void setValidActiveUser(Integer validActiveUser) {
		this.validActiveUser = validActiveUser;
	}
	
}
