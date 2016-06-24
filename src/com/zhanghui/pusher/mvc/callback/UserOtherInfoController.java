package com.zhanghui.pusher.mvc.callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.google.common.base.Strings;
import com.zhanghui.pusher.common.*;
import org.codehaus.jackson.JsonNode;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhanghui.pusher.domain.User;
import com.zhanghui.pusher.domain.UserOtherInfo;
import com.zhanghui.pusher.service.UserService;
@Controller
public class UserOtherInfoController extends AbstractCallbackController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private UserService userService;

	@RequestMapping(value = "/user/other_info", method = RequestMethod.POST)
	public void register(@Valid Form form, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(errors.hasErrors()){
			simpleFailed(response);
			return;
		}
		LogStatistics.log(LogModule.USER_OTHER_INFO, "user/other_info", false, request, form.toParams());
		if(!Strings.isNullOrEmpty(form.getLbs())) {
			User user = userService.getByIdentity(form.getUid());
			user.setLoc(form.getLbs());
			userService.saveOrUpdate(user);
		}
		//½âÎöapp_click×Ö·û´®
		if(!Strings.isNullOrEmpty(form.app_click)) {
			List<UserOtherInfo> userOtherInfoList = new ArrayList<UserOtherInfo>();
			JsonNode jn = mapper.readTree(form.app_click);
			if(jn!=null) {
				Iterator<Entry<String, JsonNode>> dateIterator = jn.getFields();
				if(dateIterator!=null) {
					while (dateIterator.hasNext()) {
						Entry<String, JsonNode> dateField = dateIterator.next();
						Iterator<Entry<String, JsonNode>> appIterator = dateField.getValue().getFields();
						if(appIterator!=null) {
							while (appIterator.hasNext()) {
								Entry<String, JsonNode> appField = appIterator.next();
								UserOtherInfo userOtherInfo = new UserOtherInfo();
								userOtherInfo.setDate(dateField.getKey());
								userOtherInfo.setApp(appField.getKey());
								JsonNode clickCountNode = appField.getValue()==null?null:appField.getValue().get("click_count");
								userOtherInfo.setCount(clickCountNode==null?0:clickCountNode.getIntValue());
								userOtherInfoList.add(userOtherInfo);
							}
						}
					}
					IpSeeker.IpData ipData = IpSeeker.ipData(RequestUtils.getRemoteIp(request));
					String shortcut = ipData == null ? "cn" : ipData.shortcut;
					LogUserOtherInfo.log(form.getUid(), shortcut, userOtherInfoList);
				}
			}
		}
		response.setContentType("text/json;charset=UTF-8");
		mapper.writeValue(response.getOutputStream(), new Object() {
			public boolean success = true;
			public int day=10;
		});
	}

	public static class Form{
		@NotEmpty
		private String uid;
		private String app_click;
		private String lbs;

		public Object[] toParams(){
			return new Object[]{uid,null,lbs};
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getApp_click() {
			return app_click;
		}
		public void setApp_click(String app_click) {
			this.app_click = app_click;
		}
		public String getLbs() {
			return lbs;
		}
		public void setLbs(String lbs) {
			this.lbs = lbs;
		}

	}
}
