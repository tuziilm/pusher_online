package com.zhanghui.pusher.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.domain.FloatingAdSettings;
import com.zhanghui.pusher.persistence.FloatingAdSettingsMapper;

@Service
public class FloatingAdSettingsService extends ListBasedCacheSupportService<FloatingAdSettings> {
    @Autowired
    public void setFloatingAdSettingsMapper(FloatingAdSettingsMapper floatingAdSettingsMapper) {
        this.mapper = floatingAdSettingsMapper;
    }

    public FloatingAdSettings getfloatingAdSettingsCache(){
        Collection<FloatingAdSettings> settings= getCache();
        if(settings==null || settings.isEmpty()){
            return null;
        }
        return ((List<FloatingAdSettings>)settings).get(0);
    }
}
