package com.zhanghui.pusher.mvc;

import com.zhanghui.pusher.common.JsonSupport;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.User;
import com.zhanghui.pusher.service.UserService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController extends ListController<User, UserService, UserController.Query> implements JsonSupport {
    public UserController() {
        super("user");
    }

    @Autowired
    private void setUserSerivce(UserService userSerivce){
        this.service = userSerivce;
    }

    @Override
    protected boolean preList(int page, Paginator paginator, Query query, Model model) {
        paginator.setNeedTotal(true);
        return super.preList(page, paginator, query, model);
    }

    //条件查询的参数对象
	public static class Query extends com.zhanghui.pusher.common.Query.NameQuery {
		private String identity;
        private String version;
        private String from;
        private String simCountry;
        private String startTime;
        private String endTime;

        public Query(){
            this.startTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
            this.endTime = this.startTime+" 24:00:00";
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
            this.addItem("version", version);
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
            this.addItem("from", from);
        }

        public String getSimCountry() {
            return simCountry;
        }

        public void setSimCountry(String simCountry) {
            this.simCountry = simCountry;
            this.addItem("simCountry", simCountry);
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime.replaceAll("/", "-");
            this.addItem("startTime", startTime);
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime.replaceAll("/","-")+" 24:00:00";
            this.addItem("endTime", endTime);
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
            this.addItem("identity", identity);
        }
    }
}
