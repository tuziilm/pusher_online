package com.zhanghui.pusher.domain;

import com.zhanghui.pusher.common.AbstractJsonObject;

import java.util.List;

public class HighChartData extends AbstractJsonObject{
	public List<String> date;
	public List<Series> series;//ÇúÏßÊı¾İ
	
	public List<String> getDate() {
		return date;
	}
	public void setDate(List<String> date) {
		this.date = date;
	}
	public List<Series> getSeries() {
		return series;
	}
	public void setSeries(List<Series> series) {
		this.series = series;
	}
}
