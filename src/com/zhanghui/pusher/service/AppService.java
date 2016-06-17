package com.zhanghui.pusher.service;

import com.zhanghui.pusher.domain.App;
import com.zhanghui.pusher.persistence.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppService extends ObjectBasedGroupCacheSupportService<App> {
    private final static String LIST_ALL_KEY="list_all_key";
    private final static String MAP_ALL_KEY="map_all_key";
	private AppMapper appMapper;
	@Autowired
	public void setAppMapper(AppMapper appMapper) {
		this.mapper = appMapper;
		this.appMapper =appMapper;
	}

    @Override
    public String[] cacheGroupKeys() {
        return new String[]{LIST_ALL_KEY, MAP_ALL_KEY};
    }

    @Override
    public Object newObject(String cacheGroupKey) {
        if(cacheGroupKey.startsWith("map")){
            return new HashMap<String, App>();
        }else{
            return new ArrayList<App>();
        }
    }

    @Override
    public void updateCacheList(Map<String, Object> update, App app) {
        ((List<App>)update.get(LIST_ALL_KEY)).add(app);
        ((Map<Integer, App>)update.get(MAP_ALL_KEY)).put(app.getId(), app);
    }
    public List<App> getAllAppsCache(){
        return (List<App>)getCache(LIST_ALL_KEY);
    }

    public Map<Integer, App> getAllAppsMapCache(){
        return (Map<Integer, App>)getCache(MAP_ALL_KEY);
    }
}
