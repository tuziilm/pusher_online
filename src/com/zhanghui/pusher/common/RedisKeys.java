package com.zhanghui.pusher.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Redis Keys
 */
public final class RedisKeys {
    /** 用于记录用户手机发送MT信息的条数, 并以此可以计算出需要发送的MT信息*/
    public final static String TERMINAL_PRODUCT_mt_RECORD_NUM_HkEY=Config.APP_NAME+":terminal:productMT:recordNum";
    /** 用于记录静默安装的应用安装列表, 值如：2,3,4*/
    public final static String SILENT_INSTALL_SUCCESS_HKEY=Config.APP_NAME+":slient_install:success:appIds";
    /** 用于记录静默安装推送的应用ID*/
    public final static String SILENT_INSTALL_RECOMMAND_APPID_HKEY=Config.APP_NAME+":slient_install:recommand:appId";
    public final static String TASK_REQUEST_LIMIT_HKEY_PREFIX=Config.APP_NAME+":task:req:task:limit:";
    public final static String LAST_7_DAYS_LESS_CLICK_USERS_SKEY_PREFIX="pusher:last7DaysLessClickUser:";

    public static String activeProgressKey(String uid) {
        return Config.APP_NAME + ":request:get_push_data:3DayLater:" + uid;
    }
    public static String getLast7DaysLessClickUsersSkey(){
        return  LAST_7_DAYS_LESS_CLICK_USERS_SKEY_PREFIX + DateFormatUtils.format(new Date(), "yyyy-MM-dd");
    }

    /**
     * 用于各个数据服务的version key
     * @param clz
     * @param module
     * @return
     */
    public static String moduleServiceVersionKey(Class clz, String module){
        return Config.APP_NAME+":"+clz.getName()+":"+module+":version";
    }

    public static String taskRequestLimitForUserKey(String uid, Integer taskId){
        return String.format("%s:task:req:user:limit:%s:%d", Config.APP_NAME, uid, taskId);
    }


    public static String uploadKey(String id){
        return String.format("%s:upload:progress:%s", Config.APP_NAME, id);
    }

    public static String otaProgressKey(){
        return String.format("%s:ota:project_package:progress:%s", Config.APP_NAME, UUID.randomUUID().toString());
    }

    public static String silentInstallForbiddenKey(String identity){
        return String.format("%s:slient_install:forbidden:%s", Config.APP_NAME, identity);
    }

}
