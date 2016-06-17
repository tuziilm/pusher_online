package com.zhanghui.pusher.service;

import com.zhanghui.pusher.domain.FloatingAdAppRule;
import com.zhanghui.pusher.persistence.FloatingAdAppRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class FloatingAdAppRuleService extends ListBasedGroupCacheSupportService<FloatingAdAppRule> {
    private final static String LIST_ALL_KEY="list_all_key";
    private final static String LIST_PROMOTION_KEY="list_promotion_key";
    private final static String LIST_PAUSE_KEY="list_pause_key";
    private final static String LIST_TEST_KEY="list_test_key";
    private final static String[] GROUP_KEYS=new String[]{LIST_ALL_KEY, LIST_PROMOTION_KEY, LIST_PAUSE_KEY, LIST_TEST_KEY};
    @Autowired
    public void setFloatingAdAppRuleMapper(FloatingAdAppRuleMapper floatingAdAppRuleMapper) {
        this.mapper = floatingAdAppRuleMapper;
    }

    @Override
    public String[] cacheGroupKeys() {
        return GROUP_KEYS;
    }

    @Override
    public void updateCacheList(Map<String, List<FloatingAdAppRule>> update, FloatingAdAppRule floatingAdAppRule) {
        update.get(LIST_ALL_KEY).add(floatingAdAppRule);
        if(floatingAdAppRule.getStatus()==0){
            update.get(LIST_PAUSE_KEY).add(floatingAdAppRule);
        }else if (floatingAdAppRule.getStatus()==1){
            update.get(LIST_PROMOTION_KEY).add(floatingAdAppRule);
        }else if (floatingAdAppRule.getStatus()==2){
            update.get(LIST_TEST_KEY).add(floatingAdAppRule);
        }
    }

    public List<FloatingAdAppRule> getAllFloatingAdAppRulesCache(){
        return getCache(LIST_ALL_KEY);
    }

    public List<FloatingAdAppRule> getPromotionFloatingAdAppRulesCache(){
        return getCache(LIST_PROMOTION_KEY);
    }

    public List<FloatingAdAppRule> getPauseFloatingAdAppRulesCache(){
        return getCache(LIST_PAUSE_KEY);
    }

    public List<FloatingAdAppRule> getTestFloatingAdAppRulesCache(){
        return getCache(LIST_TEST_KEY);
    }
}
