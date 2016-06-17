package com.zhanghui.pusher.domain;

import java.util.Iterator;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.zhanghui.pusher.common.AbstractJsonObject;
import com.zhanghui.pusher.common.Country;
import org.codehaus.jackson.annotate.JsonProperty;

public class AdScreenPageRule extends AbstractJsonObject {
    protected String activity;
    protected Integer max_times;
    protected Integer show_time_interval;
    @JsonIgnore
    protected Set<String> from;
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

	public Integer getMax_times() {
		return max_times;
	}

	public void setMax_times(Integer max_times) {
		this.max_times = max_times;
	}

	public Integer getShow_time_interval() {
		return show_time_interval;
	}

	public void setShow_time_interval(Integer show_time_interval) {
		this.show_time_interval = show_time_interval;
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
	public Set<String> getCountries() {
        return countries;
    }

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
