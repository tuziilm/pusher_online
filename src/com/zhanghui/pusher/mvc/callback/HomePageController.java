package com.zhanghui.pusher.mvc.callback;

import com.zhanghui.pusher.common.*;
import com.zhanghui.pusher.domain.BaseForm;
import com.zhanghui.pusher.domain.BrowserRule;
import com.zhanghui.pusher.domain.BrowserSettings;
import com.zhanghui.pusher.service.BrowserRuleService;
import com.zhanghui.pusher.service.BrowserSettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class HomePageController extends AbstractCallbackController {
    @Resource
    private BrowserSettingsService browserSettingsService;
    @Resource
    private BrowserRuleService browserRuleService;

    @RequestMapping(value = "/get/get_home_page", method = RequestMethod.POST)
    public void getHomePage(@Valid Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json;charset=UTF-8");
        LogStatistics.log(LogModule.HOME_PAGE,"get/get_home_page", false, request, form.toParams());
        //check ip
        IpSeeker.IpData ipData = IpSeeker.ipData(RequestUtils.getRemoteIp(request));
        if(ipData==null){
            simpleSuccess(response);
            return;
        }

        boolean testing= form.testing();
        if(!testing && form.needHideAd(ipData.shortcut)){
            simpleSuccess(response);
            return;
        }

        Collection<BrowserRule> rules= browserRuleService.getBrowserRulesCache();
        BrowserSettings browserSettings = browserSettingsService.getBrowserSettingsCache();
        if(browserSettings==null || rules==null || rules.isEmpty()){
            simpleSuccess(response);
            return;
        }

        List<JsonObject> rulesJsonObject= new ArrayList<>(rules.size());
        for(BrowserRule rule : rules){
            rulesJsonObject.add(new JsonObject().add("pkg_name",rule.getPackageName()).add("open_act", rule.getMainActivity()));
        }

        mapper.writeValue(response.getOutputStream(), new JsonObject().add("success",true)
                .add("home", browserSettings.getHomePage())
                .add("day", browserSettings.getAccessDayInterval())
                .add("browser", rulesJsonObject)
        );
    }

    public static class Form extends BaseForm {
        @NotNull
        private String uid;//必选参数，是接口一返回的值，用于标识用户唯一身份

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
