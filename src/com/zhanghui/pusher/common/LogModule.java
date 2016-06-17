package com.zhanghui.pusher.common;


/**
 * 日志统计模块
 *
 */
public enum LogModule {
	SHORTCUT("桌面快捷方式","get/shortcut"),
    UPDATE("应用更新统计","get/update"),
    FLOATING_AD_GET_APP_RULE("悬浮广告统计", "get/get_ad"),
    PUSH_STAT("Push统计", "callback/stat"),
    GET_PUSH_DATA("Push任务数据", "get/get_push_data"),
    PUSH_REGISTER("Push用户信息", "user/register"),
    HOME_PAGE("浏览器主页", "get/get_home_page"),
    USER_OTHER_INFO("用户其它信息", "user/other_info"),
	AD_SCREEN_RULE("插屏广告规则", "user/ad_screen");
	
	private String title;
    private String link;

	private LogModule(String title,String link) {
		this.title = title;
        this.link = link;
	}

    public String getLink() {
        return link;
    }

    public String getTitle() {
		return title;
	}
}
