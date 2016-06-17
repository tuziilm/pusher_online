package com.zhanghui.pusher.service;

import com.zhanghui.pusher.domain.BrowserSettings;
import com.zhanghui.pusher.persistence.BrowserSettingsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class BrowserSettingsService extends ListBasedCacheSupportService<BrowserSettings> {
    @Autowired
    public void setBrowserSettingsMapper(BrowserSettingsMapper browserSettingsMapper) {
        this.mapper = browserSettingsMapper;
    }

    public BrowserSettings getBrowserSettingsCache(){
        Collection<BrowserSettings> settings= getCache();
        if(settings==null || settings.isEmpty()){
            return null;
        }
        return ((List<BrowserSettings>)settings).get(0);
    }
}
