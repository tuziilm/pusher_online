package com.zhanghui.pusher.mvc.callback;

import com.google.common.base.Strings;
import com.zhanghui.pusher.common.*;
import com.zhanghui.pusher.domain.*;
import com.zhanghui.pusher.service.AppService;
import com.zhanghui.pusher.service.LinkService;
import com.zhanghui.pusher.service.RedisSupport;
import com.zhanghui.pusher.service.TaskService;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.*;
import java.util.Collections;

@Controller("callbackGetPushDataController")
public class GetPushDataController extends AbstractCallbackController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static int MAX_PUSH_DATA_SIZE=3;
    private final static long MILLSECONDS_PER_3_DAYS=1000*60*60*24*3;
    @Resource
    private LinkService linkService;
    @Resource
    private AppService appService;
    @Resource
    private TaskService taskService;
    @Resource
    protected RedisSupport redisSupport;

    @RequestMapping(value = "/get/get_push_data", method = RequestMethod.POST)
    public void getPushData(@Valid Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LogStatistics.log(LogModule.GET_PUSH_DATA,"get/get_push_data",false,request, form.toParams());
        response.setContentType("text/json;charset=UTF-8");
        //check ip
        IpSeeker.IpData ipData = IpSeeker.ipData(RequestUtils.getRemoteIp(request));
        if(ipData==null){
            successWithNoData(response);
            return;
        }
        //get tasks
        boolean testing= form.testing();
        if(!testing && form.needHideAd(ipData.shortcut)){
            successWithNoData(response);
            return;
        }
        if(!Config.BLOCKING_CHANNEL.contains(form.getFrom())) {
            boolean reacherRule = isRequestAfter3Days(form.getUid());
            if(!reacherRule){
                successWithNoData(response);
                return;
            }
        }
        List<Task> tasks = filterTask(testing,form, taskService.getPromptTasksCache(testing), ipData);
        int len = Math.min(MAX_PUSH_DATA_SIZE, tasks.size());
        final List<JsonObject> advs = new ArrayList<>(len);
        int versionCode = Strings.isNullOrEmpty(form.getVersion_code())?3:Integer.valueOf(form.getVersion_code());//??????version_code????????,????3?汾
        for(int i=0;i<len;i++){
            Task task = tasks.get(i);
            switch (task.getType()){
                case TaskType.SINGLE_LINK:
                    addTaskObject(advs, makeSingleLinkTaskObject(task,versionCode));
                    break;
                case TaskType.SINGLE_APP:
                    addTaskObject(advs, makeSingleAppTaskObject(task,versionCode));
                    break;
                case TaskType.MULTI_APP:
                    addTaskObject(advs, makeMultiAppTaskObject(task,versionCode));
                    break;
            }
            if(task.getMaxReqCount()>0) {
                taskService.incrLimitRequestForTask(task.getId());
            }
        }
        if(len>0){
            taskService.flagUserAccessTask(form.uid, tasks);
        }
        mapper.writeValue(response.getOutputStream(), new JsonObject(2).add("success", true).add("advs", advs));
    }

    /**
     * 判断是否是三天后请求
     * @return
     */
    private boolean isRequestAfter3Days(String uid) {
        final String key = RedisKeys.activeProgressKey(uid);
        String requestTime = redisSupport.doWithRedis(new RedisSupport.JedisHandler<String>(){
            @Override
            public String handle(Jedis jedis) {
                return jedis.get(key);
            }});
        if(requestTime==null) {
            saveFirstRequestTime(key, String.valueOf(System.currentTimeMillis()));
            return false;
        }else {
            Long firstRequestTime = Long.parseLong(requestTime);
            return firstRequestTime + MILLSECONDS_PER_3_DAYS < System.currentTimeMillis();
        }
    }
    protected Boolean saveFirstRequestTime(final String key,final String value) {
        try {
            return redisSupport.doWithRedis(new RedisSupport.JedisHandler<Boolean>() {
                @Override
                public Boolean handle(Jedis jedis) {
                    jedis.set(key, value);
                    return true;
                }
            });
        } catch (Exception e) {
            log.error("falure to access redis.", e);
            return false;
        }
    }
    private void addTaskObject(List<JsonObject> advs, JsonObject taskObj){
        if(taskObj!=null){
            advs.add(taskObj);
        }
    }

    private void successWithNoData(HttpServletResponse response) throws IOException {
        mapper.writeValue(response.getOutputStream(), new JsonObject(2).add("success", true).add("advs", Collections.emptyList()));
    }

    private JsonObject makeMultiAppTaskObject(final Task task,int versionCode) {
        if(!checkTaskBeforeMakingObject(task)){
            return null;
        }
        final Map<Integer, App> linkMap= appService.getAllAppsMapCache();
        JsonObject jsonObject = new JsonObject();
        fillTaskCommonProps(jsonObject, task);
        jsonObject.add("mtitle", task.getMtitle());

        List<JsonObject> apps = new ArrayList<>(task.getRefIdsObject().size());
        for(Integer refId : task.getRefIdsObject()){
            final App _app = linkMap.get(refId);
            if (_app == null){
                continue;
            }
            apps.add(makeAppJsonObject(_app,versionCode));
        }
        jsonObject.add("apps", apps);
        return jsonObject;
    }

    private JsonObject makeAppJsonObject(final App _app,int versionCode){
    	JsonObject jo = new JsonObject(13);
        jo.add("appName", _app.getName())
                .add("pkgname", _app.getAppPackageName())
                .add("version", _app.getVersion())
                .add("size", _app.getAppSize())
                .add("downsum", _app.getDlNum())
                .add("desc", _app.getDesc())
                .add("ad_id", _app.getId())
        		.add("icon", Config.randomDownloadURL("/public/file/",_app.getAppIcon(IconType.ORI)))
                .add("auto_dl", _app._isAutoDl())
                .add("auto_open", _app._isAutoOpen())
                .add("net_environment", _app.getNetEnvironment())
                .add("redirect_type", _app.getRedirectType())
        ;
        if(_app.getRedirectType()==0){
        	jo.add("link", Config.randomDownloadURL("/public/file/",_app.getAppPath()));
        }else{
        	jo.add("link",_app.getAppPath());
        }
        return jo;
    }

    private JsonObject makeSingleAppTaskObject(final Task task,int versionCode) {
        if(!checkTaskBeforeMakingObject(task)){
            return null;
        }
        final Map<Integer, App> linkMap= appService.getAllAppsMapCache();
        final App _app = linkMap.get(task.getRefIdsObject().iterator().next());
        if(_app==null){
            log.warn("app of task[{}] not found", task);
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        fillTaskCommonProps(jsonObject, task);
        jsonObject.add("app", makeAppJsonObject(_app,versionCode));
        return jsonObject;
    }

    private JsonObject makeSingleLinkTaskObject(final Task task,int versionCode) {
        if(!checkTaskBeforeMakingObject(task)){
            return null;
        }
        final Map<Integer, Link> linkMap= linkService.getAllLinksMapCache();
        final Link link = linkMap.get(task.getRefIdsObject().iterator().next());
        if(link==null){
            log.warn("link of task[{}] not found", task);
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        fillTaskCommonProps(jsonObject, task);
        jsonObject.add("image", Config.randomDownloadURL("/public/file/",link.getImgPath()))
                .add("url", link.getUrl())
                .add("ad_id", link.getId())
                .add("redirect_type", link.getRedirectType());
        return jsonObject;
    }

    private boolean checkTaskBeforeMakingObject(Task task){
        if(task==null){
            return false;
        }
        Set<Integer> refIds= task.getRefIdsObject();
        if(refIds==null || refIds.isEmpty()){
            log.warn("task[{}] refIds is empty", task);
            return false;
        }
        return true;
    }

    private void fillTaskCommonProps(JsonObject jsonObject, Task task){
        jsonObject.add("msgType", task.getType())
                .add("tid", task.getId())
                .add("show_time", task.getShowTime())
                .add("auto_show", task._isAutoShow())
                .add("tips", task.getTips())
                .add("title", task.getTitle())
                .add("icon", Config.randomDownloadURL("/public/file/",task.getIconPath()))
                .add("big_icon", Config.randomDownloadURL("/public/file/",task.getPicPath()))
                .add("auto_clear", task._isShutDown())
                .add("content", task.getContent());
    }

    private List<Task> filterTask(boolean testing,Form form, List<Task> tasks, IpSeeker.IpData ipData){
        if(tasks==null || tasks.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        long beginningOfTheDate = DateUtils.beginningOfTheDate().getTimeInMillis();

        List<Task> filteredtasks = new ArrayList<>();
        for(Task task : tasks){
            if(filteredtasks.size()>=MAX_PUSH_DATA_SIZE){
                break;
            }
            if (form.firstDays != null && form.firstDays < task.getStartDays()) {
                continue;
            }
            Set<String> countries= task.getCountriesObject();
            if(countries!=null && !countries.isEmpty() && !countries.contains(ipData.shortcut)){
                continue;
            }
            if(!task.isAvailable(beginningOfTheDate)){
                continue;
            }
            Set<String> mccmncs = task.getMccmncsObject();
            if(mccmncs!=null && !mccmncs.isEmpty()){
                if(Strings.isNullOrEmpty(form.getMcc())){
                    continue;
                }
                if(!mccmncs.contains(form.getMcc()+"00")){
                    if(Strings.isNullOrEmpty(form.getMnc()) || !mccmncs.contains(form.getMcc()+form.getMnc())){
                        continue;
                    }
                }
            }
            if(!testing){
	            if(taskService.taskLimitForUser(form.uid, task.getId())){
	                continue;
	            }
	            if(taskService.limitRequestForTask(task)){
	                continue;
	            }
            }
            filteredtasks.add(task);
        }
        return filteredtasks;
    }

    public static class Form extends BaseForm{
        @NotEmpty
        private String uid;
        private Integer firstDays;
        public Object[] toParams(){
            return new Object[]{uid,from,android_id,bt_mac,is_pad,mac,imei,imsi,version,android_ver,android_level,wifi,apk_name,pkgname
                    ,version_name,version_code,mcc,mnc,sim_country,operator_name,sdcard_count_spare,sdcard_available_spare,system_count_spare,
                    system_available_spare,resolution,brand,model,in_sys,firstDays};
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

		public Integer getFirstDays() {
			return firstDays;
		}

		public void setFirstDays(Integer firstDays) {
			this.firstDays = firstDays;
		}
        
    }
}
