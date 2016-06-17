package com.zhanghui.pusher.mvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Strings;
import com.zhanghui.pusher.common.Activity;
import com.zhanghui.pusher.common.AdOwner;
import com.zhanghui.pusher.common.IdForm;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.Pkg;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.domain.AppIncome;
import com.zhanghui.pusher.service.AppIncomeService;

@Controller
@RequestMapping("/app_income")
public class AppIncomeController extends CRUDController<AppIncome, AppIncomeService, com.zhanghui.pusher.mvc.AppIncomeController.Form, AppIncomeController.Query>{
	private final Logger log=LoggerFactory.getLogger(getClass());
	public AppIncomeController() {
		super("app_income");
	}
	@Resource
	public void setAppIncomeService(AppIncomeService appIncomeService){
		this.service=appIncomeService;
	}
    @Override
    protected boolean preList(int page, Paginator paginator, Query query, Model model) {
        paginator.setNeedTotal(true);
        model.addAttribute("adOwners", AdOwner.adOwners);
        model.addAttribute("pkgs", Pkg.pkgs);
        model.addAttribute("pkgMap", Pkg.pkgMap);
        Activity[] activities = Pkg.pkgMap.get(query.pkgName)==null?new Activity[]{}:Pkg.pkgMap.get(query.pkgName).getActivities();
        model.addAttribute("activities", Arrays.asList(activities));
        Map<String,Activity> activitiesMap  = new HashMap<String,Activity>();
        for(int i = 0 ;i<activities.length;i++){
        	activitiesMap.put(activities[i].getClassName(), activities[i]);
        }
        model.addAttribute("activitiesMap", activitiesMap);
        return super.preList(page, paginator, query, model);
    }
    @Override
    protected void postList(int page, Paginator paginator, Query query, Model model) {
    	List<AppIncome> list = (List<AppIncome>)model.asMap().get("datas");
    	Iterator<AppIncome> it = list.iterator();
    	while(it.hasNext()){
    		AppIncome ta = it.next();
    		if(ta.getClickTimes()==0){
    			ta.setClickPrice(0);
    		}else{
    			ta.setClickPrice(new BigDecimal((double)ta.getIncome()/100.0/(double)ta.getClickTimes()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    		if(ta.getShowTimes()==0){
    			ta.setClickRate(0);
    			ta.setShowPrice(0);
    		}else{
    			ta.setShowPrice(new BigDecimal((double)ta.getIncome()/(double)ta.getShowTimes()*10).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
    			ta.setClickRate(new BigDecimal((double)ta.getClickTimes()/(double)ta.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    	}
    	model.addAttribute("datas", list);
    	super.postList(page, paginator, query, model);
    }

    @Override
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
    	AppIncome appIncome=form.toObj();
    	service.update(appIncome);
	}

	public static class Form extends IdForm<AppIncome> {
		private double income;

        @Override
        public AppIncome newObj() {
            return new AppIncome();
        }

        @Override
        public void populateObj(AppIncome adOwnerData) {
        	adOwnerData.setIncome((int)(income*100));
        }

		public double getIncome() {
			return income;
		}

		public void setIncome(double income) {
			this.income = income;
		}

    }
	public static class Query extends com.zhanghui.pusher.common.Query {
		protected String pkgName;
		protected String adOwner;
		protected String pageName;
        protected String startTime;
        protected String endTime;

        public Query() {
            this.startTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
            this.endTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
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
			this.endTime = endTime.replaceAll("/", "-");
			this.addItem("endTime", endTime);
		}

		public String getPkgName() {
			return pkgName;
		}

		public void setPkgName(String pkgName) {
			this.pkgName = pkgName;
			this.addItem("pkgName", pkgName);
		}

		public String getAdOwner() {
			return adOwner;
		}

		public void setAdOwner(String adOwner) {
			this.adOwner = adOwner;
			this.addItem("adOwner", adOwner);
		}

		public String getPageName() {
			return pageName;
		}

		public void setPageName(String pageName) {
			this.pageName = pageName;
			this.addItem("pageName", pageName);
		}

    }
    @RequestMapping("/export")
	private void exportAppIncomeDetailXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadAppIncomeDetailTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			paginator.setQuery(query);
            List<AppIncome> list = service.list(paginator);
            Activity[] activities = Pkg.pkgMap.get(query.pkgName).getActivities();
            Map<String,Activity> activitiesMap  = new HashMap<String,Activity>();
            for(int i = 0 ;i<activities.length;i++){
            	activitiesMap.put(activities[i].getClassName(), activities[i]);
            }
        	Iterator<AppIncome> it = list.iterator();
        	while(it.hasNext()){
        		AppIncome ta = it.next();
        		if(ta.getClickTimes()==0){
        			ta.setClickPrice(0);
        		}else{
        			ta.setClickPrice(new BigDecimal((double)ta.getIncome()/100.0/(double)ta.getClickTimes()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        		}
        		if(ta.getShowTimes()==0){
        			ta.setClickRate(0);
        			ta.setShowPrice(0);
        		}else{
        			ta.setShowPrice(new BigDecimal((double)ta.getIncome()/(double)ta.getShowTimes()*10).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
        			ta.setClickRate(new BigDecimal((double)ta.getClickTimes()/(double)ta.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        		}
        	}
			for(AppIncome ta : list ){
				XSSFRow row = sh.createRow(rowIdx);
                Object[] datas = new Object[]{
						String.class, ta.getDate(),
						String.class, Strings.isNullOrEmpty(Pkg.pkgMap.get(ta.getPkgName()).getAppName())?ta.getPkgName():Pkg.pkgMap.get(ta.getPkgName()).getAppName(),
						String.class, ta.getAdOwner(),
						String.class, Strings.isNullOrEmpty(activitiesMap.get(ta.getPageName()).getName())?ta.getPageName():activitiesMap.get(ta.getPageName()).getName(),
						double.class, ta.getRequestTimes().doubleValue(),
						double.class, ta.getShowTimes().doubleValue(),
						double.class, ta.getClickTimes().doubleValue(),
						double.class, ta.getClickRate(),
						double.class, ta.getClickPrice(),
						double.class, new BigDecimal((double)ta.getIncome()/100.0).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(),
						double.class, ta.getShowPrice()
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=app_income_detail.xlsx");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
