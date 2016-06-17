package com.zhanghui.pusher.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.common.TaskType;
import com.zhanghui.pusher.domain.Desktop;
import com.zhanghui.pusher.persistence.DesktopMapper;

@Service
public class DesktopService extends ObjectBasedGroupCacheSupportService<Desktop> {
    private final static String LIST_ALL_KEY="list_all_key";
    private final static String LIST_SINGLE_APP_KEY="list_single_app_key";
    private final static String LIST_MULTI_APP_KEY="list_multi_app_key";
    private final static String LIST_TEST_SINGLE_APP_KEY="list_test_single_app_key";
    private final static String LIST_TEST_MULTI_APP_KEY="list_test_multi_app_key";
    private final static String MAP_ALL_KEY="map_all_key";
	@Autowired
	public void setDesktopMapper(DesktopMapper desktopMapper) {
		this.mapper = desktopMapper;
	}

    @Override
    public String[] cacheGroupKeys() {
        return new String[]{LIST_ALL_KEY, LIST_SINGLE_APP_KEY, LIST_MULTI_APP_KEY, MAP_ALL_KEY, LIST_TEST_MULTI_APP_KEY, LIST_TEST_SINGLE_APP_KEY};
    }

    @Override
    public Object newObject(String cacheGroupKey) {
        if(cacheGroupKey.startsWith("map")){
            return new HashMap();
        }else{
            return new ArrayList();
        }
    }

    @Override
    public void updateCacheList(Map<String, Object> update, Desktop desktop) {
        ((List<Desktop>)update.get(LIST_ALL_KEY)).add(desktop);
        ((Map<Integer, Desktop>)update.get(MAP_ALL_KEY)).put(desktop.getId(), desktop);
        boolean isForTesting=desktop.getPriority()==0;
        if (desktop.getType() == TaskType.SINGLE_APP) {
        	((List<Desktop>) update.get(isForTesting?LIST_TEST_SINGLE_APP_KEY:LIST_SINGLE_APP_KEY)).add(desktop);
        } else if (desktop.getType() == TaskType.MULTI_APP) {
        	((List<Desktop>) update.get(isForTesting?LIST_TEST_MULTI_APP_KEY:LIST_MULTI_APP_KEY)).add(desktop);
        }
    }

    public List<Desktop> getSingleAppCache(boolean testing){
        return (List<Desktop>)getCache(testing?LIST_TEST_SINGLE_APP_KEY:LIST_SINGLE_APP_KEY);
    }

    public List<Desktop> getMultiAppCache(boolean testing){
        return (List<Desktop>)getCache(testing?LIST_TEST_MULTI_APP_KEY:LIST_MULTI_APP_KEY);
    }

    public List<Desktop> getAllListCache(){
        return (List<Desktop>)getCache(LIST_ALL_KEY);
    }

    public Map<Integer, Desktop> getAllMapCache(){
        return (Map<Integer, Desktop>)getCache(MAP_ALL_KEY);
    }
}
