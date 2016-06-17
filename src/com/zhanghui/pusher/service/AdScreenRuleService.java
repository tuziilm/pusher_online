package com.zhanghui.pusher.service;

import com.zhanghui.pusher.domain.AdScreenRule;
import com.zhanghui.pusher.domain.AdScreenRule;
import com.zhanghui.pusher.persistence.AdScreenRuleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class AdScreenRuleService extends ListBasedGroupCacheSupportService<AdScreenRule> {
    private final static String LIST_ALL_KEY="list_all_key";
    private final static String LIST_PROMOTION_KEY="list_promotion_key";
    private final static String LIST_PAUSE_KEY="list_pause_key";
    private final static String LIST_TEST_KEY="list_test_key";
    private final static String[] GROUP_KEYS=new String[]{LIST_ALL_KEY, LIST_PROMOTION_KEY, LIST_PAUSE_KEY, LIST_TEST_KEY};
    @Autowired
    public void setAdScreenRuleMapper(AdScreenRuleMapper adScreenRuleMapper) {
        this.mapper = adScreenRuleMapper;
    }

    @Override
    public String[] cacheGroupKeys() {
        return GROUP_KEYS;
    }

    @Override
    public void updateCacheList(Map<String, List<AdScreenRule>> update, AdScreenRule adScreenRule) {
        update.get(LIST_ALL_KEY).add(adScreenRule);
        if(adScreenRule.getStatus()==0){
            update.get(LIST_PAUSE_KEY).add(adScreenRule);
        }else if (adScreenRule.getStatus()==1){
            update.get(LIST_PROMOTION_KEY).add(adScreenRule);
        }else if (adScreenRule.getStatus()==2){
            update.get(LIST_TEST_KEY).add(adScreenRule);
        }
    }

    public List<AdScreenRule> getAllAdScreenRulesCache(){
        return getCache(LIST_ALL_KEY);
    }

    public List<AdScreenRule> getPromotionAdScreenRulesCache(){
        return getCache(LIST_PROMOTION_KEY);
    }

    public List<AdScreenRule> getPauseAdScreenRulesCache(){
        return getCache(LIST_PAUSE_KEY);
    }

    public List<AdScreenRule> getTestAdScreenRulesCache(){
        return getCache(LIST_TEST_KEY);
    }
}
