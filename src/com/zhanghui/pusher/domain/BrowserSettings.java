package com.zhanghui.pusher.domain;

public class BrowserSettings extends Id {
    private String homePage;
    private Integer accessDayInterval;

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public Integer getAccessDayInterval() {
        return accessDayInterval;
    }

    public void setAccessDayInterval(Integer accessDayInterval) {
        this.accessDayInterval = accessDayInterval;
    }
}
