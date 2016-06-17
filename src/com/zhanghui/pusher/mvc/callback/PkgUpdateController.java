package com.zhanghui.pusher.mvc.callback;

import com.google.common.collect.Maps;
import com.zhanghui.pusher.common.JsonObject;
import com.zhanghui.pusher.common.LogModule;
import com.zhanghui.pusher.common.LogStatistics;
import com.zhanghui.pusher.domain.BaseForm;
import com.zhanghui.pusher.domain.BrowserRule;
import com.zhanghui.pusher.domain.BrowserSettings;
import com.zhanghui.pusher.service.BrowserRuleService;
import com.zhanghui.pusher.service.BrowserSettingsService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@Controller
public class PkgUpdateController extends AbstractCallbackController {
    public final static Map<String, UpdatePkg> updatePkgs= initUpdatePkgs();

    private static Map<String,UpdatePkg> initUpdatePkgs() {
        Map<String, UpdatePkg> map = new HashMap<>();
        UpdatePkg pushApk = new UpdatePkg();
        pushApk.pkgName="com.android.google.settings";
        pushApk.link="http://push.yoyogame.net/public/file/update/push-2.apk";
        pushApk.versionCode=2;
        map.put("com.android.google.settings",pushApk);
        return map;
    }

    public static class UpdatePkg{
        public String pkgName;
        public String link;
        public int versionCode;
    }

    @RequestMapping(value = "/get/update", method = RequestMethod.POST)
    public void getHomePage(@Valid Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/json;charset=UTF-8");
        LogStatistics.log(LogModule.UPDATE, "get/update", false, request, form.toParams());
        UpdatePkg updatePkg = updatePkgs.get(form.pkg);
        mapper.writeValue(response.getOutputStream(), new JsonObject().add("success", true)
                        .add("url", updatePkg != null && updatePkg.versionCode>form.versionCode?updatePkg.link:"")
        );
    }

    public static class Form extends BaseForm {
        @NotEmpty
        private String pkg;
        @NotEmpty
        private String version_code;
        //extra
        private int versionCode;

        public Object[] toParams(){
            return new Object[]{uid,from,android_id,bt_mac,is_pad,mac,imei,imsi,version,android_ver,android_level,wifi,apk_name,pkgname
                    ,version_name,version_code,mcc,mnc,sim_country,operator_name,sdcard_count_spare,sdcard_available_spare,system_count_spare,
                    system_available_spare,resolution,brand,model,in_sys, pkg};
        }

        public String getPkg() {
            return pkg;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
            try{
                versionCode = Integer.valueOf(version_code);
            }catch (Exception e){
            }
        }
    }
}
