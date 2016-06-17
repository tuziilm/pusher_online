package com.zhanghui.pusher.domain;

import com.zhanghui.pusher.common.AbstractJsonObject;
import com.zhanghui.pusher.common.JsonObject;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Set;

public class InnerFloatingAdAppPageRule extends AbstractJsonObject{
    private FloatingAdAppPageRule3V floatingAdAppPageRule3V;

    public InnerFloatingAdAppPageRule(FloatingAdAppPageRule3V floatingAdAppPageRule3V) {
        this.floatingAdAppPageRule3V = floatingAdAppPageRule3V;
    }

    @JsonIgnore
    public String getJsonString() {
        return toJsonWithNoException(this);
    }

    public JsonObject getAd() {
        return floatingAdAppPageRule3V.getAd();
    }

    public int getSdk_type() {
        return floatingAdAppPageRule3V.getSdk_type();
    }

    public String getActivity() {
        return floatingAdAppPageRule3V.getActivity();
    }

    public int getX() {
        return floatingAdAppPageRule3V.getX();
    }

    public int getY() {
        return floatingAdAppPageRule3V.getY();
    }

    public String getFrom() {
        return floatingAdAppPageRule3V.getFromAsString();
    }

    public Set<String> getCountries() {
        return floatingAdAppPageRule3V.getCountries();
    }

    public boolean isClose() {
        return floatingAdAppPageRule3V.isClose();
    }

    public String[] getResolution() {
        return floatingAdAppPageRule3V.getResolution();
    }

    public Integer[] getAndroid_level() {
        return floatingAdAppPageRule3V.getAndroid_level();
    }

    public String[] getApp_ver() {
        return floatingAdAppPageRule3V.getApp_ver();
    }
}
