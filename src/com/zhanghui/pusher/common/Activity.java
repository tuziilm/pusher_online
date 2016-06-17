package com.zhanghui.pusher.common;

public class Activity {
	private String className;
	private String name;
	
	public Activity(String name, String className) {
		super();
		this.className = className;
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
