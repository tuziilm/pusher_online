package com.zhanghui.pusher.template;

import java.io.InputStream;

public class XlsxTemplate {
	public final static String TEMP_TASK_ACTION_INFO="task_action_info.xlsx";
	public final static String TOTAL_DATA="total_data.xlsx";
	public final static String ACTIVE_USER="active_user.xlsx";
	public final static String TOP_APP="top_app.xlsx";
	public final static String ADOWNER_DATA="adowner_data.xlsx";
	public final static String COUNTRY_DATA="country_data.xlsx";
	public final static String APP_INCOME_DETAIL="app_income_detail.xlsx";
	public final static String APP_INCOME_TOTAL="app_income_total.xlsx";
	public final static String PVUV_DATA="pvuv_data.xlsx";
	public final static InputStream loadTemplate(String filename){
		return XlsxTemplate.class.getResourceAsStream(filename);
	}
	public static void main(String[] args) {
		System.out.println(XlsxTemplate.loadTemplate("task_action_info.xlsx"));
	}
}
