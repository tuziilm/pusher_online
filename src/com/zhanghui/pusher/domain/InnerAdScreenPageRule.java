package com.zhanghui.pusher.domain;

import com.zhanghui.pusher.common.AbstractJsonObject;
import com.zhanghui.pusher.common.JsonObject;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Set;

public class InnerAdScreenPageRule extends AbstractJsonObject {
    private AdScreenPageRuleForExtend adScreenPageRuleForExtend;

    public InnerAdScreenPageRule(AdScreenPageRuleForExtend adScreenPageRuleForExtend) {
        this.adScreenPageRuleForExtend = adScreenPageRuleForExtend;
    }

    @JsonIgnore
    public String getJsonString() {
        return toJsonWithNoException(this);
    }

    public String getFrom() {
        return adScreenPageRuleForExtend.getFromAsString();
    }

    public Set<String> getCountries() {
        return adScreenPageRuleForExtend.getCountries();
    }

    public JsonObject getAd() {
        return adScreenPageRuleForExtend.getAd();
    }

    public int getSdk_type() {
        return adScreenPageRuleForExtend.getSdk_type();
    }

    public String getActivity() {
        return adScreenPageRuleForExtend.getActivity();
    }

    public Integer getMax_times() {
        return adScreenPageRuleForExtend.getMax_times();
    }

    public Integer getShow_time_interval() {
        return adScreenPageRuleForExtend.getShow_time_interval();
    }
}
