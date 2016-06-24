package com.zhanghui.pusher.mvc.callback;

import com.zhanghui.pusher.common.LogModule;
import com.zhanghui.pusher.common.LogStatistics;
import com.zhanghui.pusher.domain.BaseForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
public class PushStatController  extends AbstractCallbackController {
    @RequestMapping(value = "/callback/stat", method = RequestMethod.POST)
    public void stat(@Valid Form form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LogStatistics.log(LogModule.PUSH_STAT,"callback/stat", false, request, form.toParams());
        response.setContentType("text/json;charset=UTF-8");
        simpleSuccess(response);
    }
    public static class Form extends BaseForm{
        private String uid;//必选参数，是接口一返回的值，用于标识用户唯一身份
        private Integer msgType;//必选参数，这个是广告类型，请参考接口二的参数说明
        private String ad_id;//必选参数，这个是广告ID，请参考接口二的参数说明
        private Integer tid;//必选参数，管理后台建立的任务ID，请参考接口二的参数说明
        private Integer action_id;//必选参数，表示用户对广告作出的反应动作，请参考 备注6
        private Boolean auto_show;//必选参数，true|false（boolean型，值为true表示自动展示广告，相当于用户点击这个广告后看到的效果，只是免去了用户点击这个动作)
        private String pkgname;//pkgname,必选参数，除广告外，其他功能值为””
        private String classname;//classname,必选参数，除广告外，其他功能值为””
        public Object[] toParams(){
            return new Object[]{uid,from,android_id,bt_mac,is_pad,mac,imei,imsi,version,android_ver,android_level,wifi,apk_name,pkgname
                    ,version_name,version_code,mcc,mnc,sim_country,operator_name,sdcard_count_spare,sdcard_available_spare,system_count_spare,
                    system_available_spare,resolution,brand,model,in_sys,msgType,ad_id,tid,action_id,auto_show,pkgname,classname};
        }

        public Boolean getAuto_show() {
            return auto_show;
        }

        public void setAuto_show(Boolean auto_show) {
            this.auto_show = auto_show;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Integer getMsgType() {
            return msgType;
        }

        public void setMsgType(Integer msgType) {
            this.msgType = msgType;
        }

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public Integer getTid() {
            return tid;
        }

        public void setTid(Integer tid) {
            this.tid = tid;
        }

        public Integer getAction_id() {
            return action_id;
        }

        public void setAction_id(Integer action_id) {
            this.action_id = action_id;
        }

		public String getPkgname() {
			return pkgname;
		}

		public void setPkgname(String pkgname) {
			this.pkgname = pkgname;
		}

		public String getClassname() {
			return classname;
		}

		public void setClassname(String classname) {
			this.classname = classname;
		}
    }
}
