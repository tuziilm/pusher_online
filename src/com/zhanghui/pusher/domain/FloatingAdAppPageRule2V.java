package com.zhanghui.pusher.domain;

import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.base.Joiner;
import com.zhanghui.pusher.common.AbstractJsonObject;
import com.zhanghui.pusher.common.Country;
import org.codehaus.jackson.annotate.JsonProperty;

public class FloatingAdAppPageRule2V extends AbstractJsonObject {
    private final static Integer[] ANDROID_LEVELS=new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
    protected String activity;
    protected int x;
    protected int y;
    protected Set<String> from;
    protected String[] app_ver;
    protected Integer[] android_level;
    protected String[] resolution;
    protected boolean close;
    protected Set<String> countries;
    
    @JsonIgnore
    public String getJsonString(){
        return toJsonWithNoException(this);
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Set<String> getFrom(){
        return this.from;
    }

    @JsonIgnore
    public String getFromAsString() {
        if(from==null || from.size()<0){
            return null;
        }
        return Joiner.on(",").join(from);
    }

    @JsonProperty
    public void setFrom(String from) {
        this.from= Sets.newHashSet(Splitter.on(",").omitEmptyStrings().trimResults().split(from));
    }

	@JsonIgnore
    public String getApp_verAsString(){
        if(app_ver==null || app_ver.length<1){
            return "";
        }
        return Joiner.on(",").join(app_ver);
    }

    public String[] getApp_ver() {
        return app_ver;
    }

    public void setApp_ver(String[] app_ver) {
        this.app_ver = app_ver;
    }

    @JsonIgnore
    public String getAndroid_levelAsString(){
        if(android_level==null || android_level.length<1){
            return "";
        }
        return Joiner.on(",").join(android_level);
    }

    public Integer[] getAndroid_level() {
        return ANDROID_LEVELS;
    }

    public void setAndroid_level(Integer[] android_level) {
        this.android_level = android_level;
    }

    @JsonIgnore
    public String getResolutionAsString(){
        if(resolution==null || resolution.length<1){
            return "";
        }
        return Joiner.on(",").join(resolution);
    }

    public String[] getResolution() {
        return resolution;
    }

    public void setResolution(String[] resolution) {
        this.resolution = resolution;
    }

	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}
	@JsonIgnore
	public Set<String> getCountries() {
        return countries;
    }

    @JsonProperty
    public void setCountries(Set<String> countries) {
        this.countries = countries;
    }
    @JsonIgnore
    public String getCountriesAsString(){
        if(countries==null || countries.size()<0){
            return null;
        }
        String coutry2ChName = "";
        Iterator it = countries.iterator();
        String shortCut = "";
        while(it.hasNext()){
        	shortCut = it.next().toString();
        	if(coutry2ChName!=""){
        		coutry2ChName = coutry2ChName+","+Country.shortcut2CountryMap.get(shortCut).getChineseName();
        	}else{
        		coutry2ChName = Country.shortcut2CountryMap.get(shortCut).getChineseName();
        	}
        }
        return coutry2ChName;
    }
}
