package com.zhanghui.pusher.mvc.callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.google.common.base.Strings;
import com.zhanghui.pusher.common.*;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhanghui.pusher.domain.App;
import com.zhanghui.pusher.domain.BaseForm;
import com.zhanghui.pusher.domain.Desktop;
import com.zhanghui.pusher.domain.FloatingAdAppPageRule3V;
import com.zhanghui.pusher.service.AppService;
import com.zhanghui.pusher.service.DesktopService;

@Controller("callbackGetShortCutController")
public class GetShortCutController extends AbstractCallbackController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private AppService appService;
    @Resource
    private DesktopService desktopService;

    @RequestMapping(value = "/get/shortcut", method = RequestMethod.POST)
    public void getShortcut(@Valid Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LogStatistics.log(LogModule.SHORTCUT,"get/shortcut",false,request, form.toParams());
        response.setContentType("text/json;charset=UTF-8");
        //check ip
        IpSeeker.IpData ipData = IpSeeker.ipData(RequestUtils.getRemoteIp(request));
        if(ipData==null){
            successWithNoData(response);
            return;
        }
        //get desktop
        boolean testing= form.testing();
        if(!testing && form.needHideAd(ipData.shortcut)){
            successWithNoData(response);
            return;
        }
        final Desktop singleApp = filterApp(desktopService.getSingleAppCache(testing), ipData);
        final Desktop multiApp = filterApp(desktopService.getMultiAppCache(testing), ipData);
        int versionCode = Strings.isNullOrEmpty(form.getVersion_code())?3:Integer.valueOf(form.getVersion_code());//发现在version_code为空的现象,默认为3版本
        JsonObject singleApp2Json = makeAppObject(singleApp,versionCode);
        JsonObject multiApp2Json = makeAppObject(multiApp,versionCode);
        
        mapper.writeValue(response.getOutputStream(), new JsonObject(4).add("success", true).add("day",5).add("single_app", singleApp2Json).add("multi_apps", multiApp2Json));

    }

    private void successWithNoData(HttpServletResponse response) throws IOException {
        mapper.writeValue(response.getOutputStream(), new JsonObject(4).add("success", true).add("day",5).add("single_app", Collections.emptyList()).add("multi_apps", Collections.emptyList()));
    }

    private JsonObject makeAppObject(final Desktop desktop,int versionCode) {
        if(!checkTaskBeforeMakingObject(desktop)){
            return null;
        }
        final Map<Integer, App> linkMap= appService.getAllAppsMapCache();
        JsonObject jsonObject = new JsonObject();
        if(desktop.getType()==1003){
        	jsonObject.add("name", desktop.getMtitle())
        				.add("icon_path",Config.HOST_URL+"/public/file/"+desktop.getIconPath());
        	List<JsonObject> apps = new ArrayList<>(desktop.getRefIdsObject().size());
        	for(Integer refId : desktop.getRefIdsObject()){
        		final App _app = linkMap.get(refId);
        		if (_app == null){
        			continue;
        		}
        		apps.add(makeAppJsonObject(_app,versionCode));
        	}
        	jsonObject.add("apps", apps);
        }else{
        	for(Integer refId : desktop.getRefIdsObject()){
        		final App _app = linkMap.get(refId);
        		if (_app == null){
        			continue;
        		}
        		jsonObject = makeAppJsonObject(_app,versionCode);
        	}
        }
        return jsonObject;
    }

    private boolean checkTaskBeforeMakingObject(Desktop desktop){
    	if(desktop==null){
    		return false;
    	}
    	Set<Integer> refIds= desktop.getRefIdsObject();
    	if(refIds==null || refIds.isEmpty()){
    		log.warn("task[{}] refIds is empty", desktop);
    		return false;
    	}
    	return true;
    }
    
    private JsonObject makeAppJsonObject(final App _app,int versionCode){
    	JsonObject jo = new JsonObject(7);
        jo.add("app_id", _app.getId())
                .add("icon", Config.HOST_URL+"/public/file/"+_app.getAppIcon(IconType.M300))
                .add("name", _app.getAppLabel())
                .add("pkg", _app.getAppPackageName())
                .add("size", _app.getAppSize())
                .add("redirect_type", _app.getRedirectType())
                .add("auto_dl", _app._isAutoDl())
                .add("auto_open", _app._isAutoOpen())
                .add("net_environment", _app.getNetEnvironment());
        if(_app.getRedirectType()==0){
        	jo.add("apk", Config.HOST_URL+"/public/file/"+_app.getAppPath());
        }else{
        	jo.add("apk", _app.getAppPath());
        }
        return jo;
    }

    private Desktop filterApp(List<Desktop> desktops, IpSeeker.IpData ipData){
        if(desktops==null || desktops.isEmpty()){
            return null;
        }
        for(Desktop desktop : desktops){
            Set<String> countries= desktop.getCountriesObject();
            if(countries!=null && !countries.isEmpty() && countries.contains(ipData.shortcut)){
               return desktop;
            }
        }
        return null;
    }

    public static class Form extends BaseForm{
        @NotEmpty
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
