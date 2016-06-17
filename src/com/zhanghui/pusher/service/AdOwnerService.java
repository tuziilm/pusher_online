package com.zhanghui.pusher.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.domain.AdOwner;
import com.zhanghui.pusher.persistence.AdOwnerMapper;

@Service
public class AdOwnerService extends ObjectBasedGroupCacheSupportService<AdOwner> {
    private final static String LIST_ALL_KEY="list_all_key";
    private final static String MAP_ALL_KEY="map_all_key";
	private AdOwnerMapper adOwnerMapper;
	@Autowired
	public void setAdOwnerMapper(AdOwnerMapper adOwnerMapper) {
		this.mapper = adOwnerMapper;
		this.adOwnerMapper =adOwnerMapper;
	}

    @Override
    public String[] cacheGroupKeys() {
        return new String[]{LIST_ALL_KEY, MAP_ALL_KEY};
    }

    @Override
    public Object newObject(String cacheGroupKey) {
        if(cacheGroupKey.startsWith("map")){
            return new HashMap<String, AdOwner>();
        }else{
            return new ArrayList<AdOwner>();
        }
    }

    @Override
    public void updateCacheList(Map<String, Object> update, AdOwner adOwner) {
        ((List<AdOwner>)update.get(LIST_ALL_KEY)).add(adOwner);
        ((Map<Integer, AdOwner>)update.get(MAP_ALL_KEY)).put(adOwner.getId(), adOwner);
    }
    public List<AdOwner> getAllAdOwnersCache(){
        return (List<AdOwner>)getCache(LIST_ALL_KEY);
    }
    
    public Map<Integer, AdOwner> getAllAdOwnersMapCache(){
        return (Map<Integer, AdOwner>)getCache(MAP_ALL_KEY);
    }
}
