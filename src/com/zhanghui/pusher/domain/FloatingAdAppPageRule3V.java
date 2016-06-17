package com.zhanghui.pusher.domain;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.zhanghui.pusher.common.JsonObject;
import org.codehaus.jackson.annotate.JsonProperty;

public class FloatingAdAppPageRule3V extends FloatingAdAppPageRule2V {
	public final static String ADMOB_KEY="ca-app-pub-2935814740346480/8759819455";
    protected int sdk_type;
    protected JsonObject ad;
    protected FloatingAdAppPageRule2V floatingAdAppPageRule2V=new FloatingAdAppPageRule2V();
    
    public JsonObject getAd(){
    	return ad;
    }
    
    public void setAd(JsonObject ad) {
    	this.ad = ad;
	}
    
	public int getSdk_type() {
		return sdk_type;
	}

	public void setSdk_type(int sdk_type) {
		this.sdk_type = sdk_type;
	}
	
	public void setAd_type(int sdk_type) {
        this.sdk_type = sdk_type;
    }

	@JsonIgnore
	public FloatingAdAppPageRule2V getFloatingAdAppPageRule2V() {
		return floatingAdAppPageRule2V;
	}

	public void setFloatingAdAppPageRule2V(
			FloatingAdAppPageRule2V floatingAdAppPageRule2V) {
		this.floatingAdAppPageRule2V = floatingAdAppPageRule2V;
	}

	@JsonIgnore
	public String getJsonString() {
		return toJsonWithNoException(this);
	}

	@Override
	public String getActivity() {
		return floatingAdAppPageRule2V.getActivity();
	}

	@Override
	public void setActivity(String activity) {
		floatingAdAppPageRule2V.setActivity(activity);
	}

	@Override
	public int getX() {
		return floatingAdAppPageRule2V.getX();
	}

	@Override
	public void setX(int x) {
		floatingAdAppPageRule2V.setX(x);
	}

	@Override
	public int getY() {
		return floatingAdAppPageRule2V.getY();
	}

	@Override
	public void setY(int y) {
		floatingAdAppPageRule2V.setY(y);
	}

	@Override
	@JsonIgnore
	public Set<String> getFrom() {
		return floatingAdAppPageRule2V.getFrom();
	}

	@Override
	@JsonProperty
	public void setFrom(String from) {
		floatingAdAppPageRule2V.setFrom(from);
	}

	@Override
	public String getFromAsString(){
		return floatingAdAppPageRule2V.getFromAsString();
	}
	@Override
	public String getApp_verAsString() {
		return floatingAdAppPageRule2V.getApp_verAsString();
	}

	@Override
	public String[] getApp_ver() {
		return floatingAdAppPageRule2V.getApp_ver();
	}

	@Override
	public void setApp_ver(String[] app_ver) {
		floatingAdAppPageRule2V.setApp_ver(app_ver);
	}

	@Override
	public String getAndroid_levelAsString() {
		return floatingAdAppPageRule2V.getAndroid_levelAsString();
	}

	@Override
	public Integer[] getAndroid_level() {
		return floatingAdAppPageRule2V.getAndroid_level();
	}

	@Override
	public void setAndroid_level(Integer[] android_level) {
		floatingAdAppPageRule2V.setAndroid_level(android_level);
	}

	@Override
	public String getResolutionAsString() {
		return floatingAdAppPageRule2V.getResolutionAsString();
	}

	@Override
	public String[] getResolution() {
		return floatingAdAppPageRule2V.getResolution();
	}

	@Override
	public void setResolution(String[] resolution) {
		floatingAdAppPageRule2V.setResolution(resolution);
	}

	@Override
	public boolean isClose() {
		return floatingAdAppPageRule2V.isClose();
	}

	@Override
	public void setClose(boolean close) {
		floatingAdAppPageRule2V.setClose(close);
	}
	
	@Override
	public Set<String> getCountries() {
		return floatingAdAppPageRule2V.getCountries();
	}

	@Override
	public void setCountries(Set<String> countries) {
		floatingAdAppPageRule2V.setCountries(countries);;
	}

	@Override
	public String getCountriesAsString() {
		return floatingAdAppPageRule2V.getCountriesAsString();
	}

}
