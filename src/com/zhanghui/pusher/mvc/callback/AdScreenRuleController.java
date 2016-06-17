package com.zhanghui.pusher.mvc.callback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.base.Strings;

import com.zhanghui.pusher.common.*;
import com.zhanghui.pusher.service.RedisSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhanghui.pusher.domain.AdScreenPageRuleForExtend;
import com.zhanghui.pusher.domain.AdScreenRule;
import com.zhanghui.pusher.domain.BaseForm;
import com.zhanghui.pusher.service.AdScreenRuleService;
import redis.clients.jedis.Jedis;

@Controller("cbAdScreenRuleController")
public class AdScreenRuleController extends AbstractCallbackController{
    @Resource
    private AdScreenRuleService adScreenRuleService;
    @Resource
    private RedisSupport redisSupport;

    @RequestMapping(value = "/user/ad_screen", method = RequestMethod.POST)
    public void adRules(@Valid Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LogStatistics.log(LogModule.AD_SCREEN_RULE, "user/ad_screen", false, request, form.toParams());

        IpSeeker.IpData ipData = IpSeeker.ipData(RequestUtils.getRemoteIp(request));
        String shortcut = ipData==null?"cn":ipData.shortcut;
        int versionCode = Strings.isNullOrEmpty(form.getVersion_code())?4:Integer.valueOf(form.getVersion_code());//发现在version_code为空的现象,默认为4版本
        JsonObject json=new JsonObject(4).add("success",true)
                .add("day",1).add("hide_time",6)
                .add("app_rules", toAppRules(form,shortcut, versionCode));
        mapper.writeValue(response.getOutputStream(), json);
    }
    private List<JsonObject> toAppRules(final Form form, String shortcut, int versionCode){
        boolean testing = form.testing();
        Collection<AdScreenRule> adScreenRules = testing?adScreenRuleService.getTestAdScreenRulesCache():adScreenRuleService.getPromotionAdScreenRulesCache();
        if(adScreenRules==null || adScreenRules.isEmpty() ){
            return Collections.EMPTY_LIST;
        }
        if(!testing && form.needHideAd(shortcut)){
            return Collections.EMPTY_LIST;
        }
        List<JsonObject> appRules = new ArrayList<>(adScreenRules.size());
        boolean hasLockScreen=false;
        AdScreenRule forceOpenLockScreenRule = null;
        for(AdScreenRule rule : adScreenRules){
            if(rule.getId()==76){//用于少于3次用户开锁屏
                forceOpenLockScreenRule = rule;
                continue;
            }
            Tuple<?, Boolean> tuple = filterPageRule(rule.getPageRulesObject(),shortcut,form.getFrom(),versionCode);
            if (tuple.second) {//只返回有页面规则的应用
                if("com.sad".equals(rule.getPkgName())){
                    hasLockScreen = true;
                }
                appRules.add(new JsonObject(2).add("pkg_name", rule.getPkgName())
                        .add("page_rules", tuple.getFirst()));
            }
        }
        if(!hasLockScreen && forceOpenLockScreenRule != null){
            //判断是否强制打开开锁屏
            final boolean forceOpenLockScreen = redisSupport.try2DoWithRedis(new RedisSupport.JedisHandler<Boolean>() {
                @Override
                public Boolean handle(Jedis jedis) {
                    return jedis.sismember(RedisKeys.getLast7DaysLessClickUsersSkey(), form.getUid());
                }
            }, RedisSupport.DATABASE_2, false, 1);

            if(forceOpenLockScreen){
                Tuple<?, Boolean> tuple = filterPageRule(forceOpenLockScreenRule.getPageRulesObject(),shortcut,form.getFrom(),versionCode, true);
                if (tuple.second) {//只返回有页面规则的应用
                    appRules.add(new JsonObject(2).add("pkg_name", forceOpenLockScreenRule.getPkgName())
                            .add("page_rules", tuple.getFirst()));
                }
            }
        }
        return appRules;
    }
    
    public Tuple<?, Boolean> filterPageRule(AdScreenPageRuleForExtend[] rules,String shortcut,String from,int version){
        return filterPageRule(rules, shortcut, from, version, false);
    }

    public Tuple<?, Boolean> filterPageRule(AdScreenPageRuleForExtend[] rules,String shortcut,String from,int version, boolean skipFrom){
        List<AdScreenPageRuleForExtend> adScreenPageRules = new ArrayList<>(3);//认为平均一个应用有3条页面记录
        for(AdScreenPageRuleForExtend rule :rules){
    		Set<String> countries = rule.getCountries();
            if(countries !=null && !countries.isEmpty() && !countries.contains(shortcut)){
    			continue;
    		}
            if(!skipFrom){
                Set<String> ruleFrom = rule.getFrom();
                if(ruleFrom!=null && !ruleFrom.isEmpty() && !ruleFrom.contains(from)){
                    continue;
                }
            }
//            if(VersionBase.screenSupport(version, rule.getSdk_type())){
                adScreenPageRules.add(rule);
//            }
    	}
    	return Tuple.valueOf(adScreenPageRules, adScreenPageRules.size()>0);
    }

    public static class Form extends BaseForm {
        @NotNull
        private String uid;

        public Object[] toParams(){
            return new Object[]{uid,from,android_id,bt_mac,is_pad,mac,imei,imsi,version,android_ver,android_level,wifi,apk_name,pkgname
                    ,version_name,version_code,mcc,mnc,sim_country,operator_name,sdcard_count_spare,sdcard_available_spare,system_count_spare,
                    system_available_spare,resolution,brand,model,in_sys};
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

    }
}
