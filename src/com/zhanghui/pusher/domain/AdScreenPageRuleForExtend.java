package com.zhanghui.pusher.domain;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.zhanghui.pusher.common.JsonObject;
import org.codehaus.jackson.annotate.JsonProperty;

public class AdScreenPageRuleForExtend extends AdScreenPageRule {
    private int sdk_type;
    private JsonObject ad;
    private AdScreenPageRule adScreenPageRule=new AdScreenPageRule();
    
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
	public AdScreenPageRule getAdScreenPageRule() {
		return adScreenPageRule;
	}

	public void setAdScreenPageRule(AdScreenPageRule adScreenPageRule) {
		this.adScreenPageRule = adScreenPageRule;
	}

	@JsonIgnore
	public String getJsonString() {
		return toJsonWithNoException(this);
	}

	@Override
	public String getActivity() {
		return adScreenPageRule.getActivity();
	}

	@Override
	public void setActivity(String activity) {
		adScreenPageRule.setActivity(activity);
	}

	@Override
	public Integer getMax_times() {
		return adScreenPageRule.getMax_times();
	}

	@Override
	public void setMax_times(Integer max_times) {
		adScreenPageRule.setMax_times(max_times);
	}

	@Override
	public Integer getShow_time_interval() {
		return adScreenPageRule.getShow_time_interval();
	}

	@Override
	public void setShow_time_interval(Integer show_time_interval) {
		adScreenPageRule.setShow_time_interval(show_time_interval);
	}

	@Override
	@JsonIgnore
	public Set<String> getFrom() {
		return adScreenPageRule.getFrom();
	}

	@Override
	@JsonProperty
	public void setFrom(String from) {
		adScreenPageRule.setFrom(from);
	}

	@Override
	public String getFromAsString(){
		return adScreenPageRule.getFromAsString();
	}

	@Override
    @JsonIgnore
	public Set<String> getCountries() {
		return adScreenPageRule.getCountries();
	}

	@Override
    @JsonProperty
	public void setCountries(Set<String> countries) {
		adScreenPageRule.setCountries(countries);
	}

	@Override
	public String getCountriesAsString() {
		return adScreenPageRule.getCountriesAsString();
	}
	
}
