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
        private String uid;//��ѡ�������ǽӿ�һ���ص�ֵ�����ڱ�ʶ�û�Ψһ���
        private Integer msgType;//��ѡ����������ǹ�����ͣ���ο��ӿڶ��Ĳ���˵��
        private String ad_id;//��ѡ����������ǹ��ID����ο��ӿڶ��Ĳ���˵��
        private Integer tid;//��ѡ�����������̨����������ID����ο��ӿڶ��Ĳ���˵��
        private Integer action_id;//��ѡ��������ʾ�û��Թ�������ķ�Ӧ��������ο� ��ע6
        private Boolean auto_show;//��ѡ������true|false��boolean�ͣ�ֵΪtrue��ʾ�Զ�չʾ��棬�൱���û����������󿴵���Ч����ֻ����ȥ���û�����������)
        private String pkgname;//pkgname,��ѡ������������⣬��������ֵΪ����
        private String classname;//classname,��ѡ������������⣬��������ֵΪ����
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
