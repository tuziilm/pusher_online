package com.zhanghui.pusher.mvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.zhanghui.pusher.common.AdOwner;
import com.zhanghui.pusher.common.From;
import com.zhanghui.pusher.common.IdForm;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.domain.AdOwnerData;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.service.AdOwnerDataService;

@Controller
@RequestMapping("/data_statistics/adOwner")
public class AdOwnerDataController extends CRUDController<AdOwnerData, AdOwnerDataService, com.zhanghui.pusher.mvc.AdOwnerDataController.Form, AdOwnerDataController.Query>{
	private final Logger log=LoggerFactory.getLogger(getClass());
	public AdOwnerDataController() {
		super("data_statistics/adOwner");
	}
	@Resource
	public void setAdOwnerDataService(AdOwnerDataService adOwnerDataService){
		this.service=adOwnerDataService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query query, Model model) {
        paginator.setNeedTotal(true);
        model.addAttribute("adOwners", AdOwner.adOwners);
        model.addAttribute("froms", From.froms);
        return super.preList(page, paginator, query, model);
    }
    @Override
    protected void postList(int page, Paginator paginator, Query query, Model model) {
    	SumData sumData = service.getSum(paginator);
    	if(sumData!=null){
    		if(sumData.getClickTimes()==0){
    			sumData.setClickPrice(0);
    		}else{
    			sumData.setClickPrice(new BigDecimal((double)sumData.getIncome()/100.0/(double)sumData.getClickTimes()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    		if(sumData.getShowTimes()==0){
    			sumData.setClickRate(0);
    			sumData.setShowPrice(0);
    		}else{
    			sumData.setShowPrice(new BigDecimal((double)sumData.getIncome()/(double)sumData.getShowTimes()*10).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
    			sumData.setClickRate(new BigDecimal((double)sumData.getClickTimes()/(double)sumData.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    		if(sumData.getRequestTimes()==0){
    			sumData.setRequestSuccessRate(0);
    		}else{
    			sumData.setRequestSuccessRate(new BigDecimal((double)sumData.getShowTimes()/(double)sumData.getRequestTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    	}
    	List<AdOwnerData> list = (List<AdOwnerData>)model.asMap().get("datas");
    	Iterator<AdOwnerData> it = list.iterator();
    	while(it.hasNext()){
    		AdOwnerData ta = it.next();
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
    		if(ta.getRequestTimes()==0){
    			ta.setRequestSuccessRate(0);
    		}else{
    			ta.setRequestSuccessRate(new BigDecimal((double)ta.getShowTimes()/(double)ta.getRequestTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    	}
    	model.addAttribute("sum", sumData);
    	model.addAttribute("datas", list);
    	super.postList(page, paginator, query, model);
    }

    @Override
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
    	AdOwnerData totalData=form.toObj();
    	service.update(totalData);
	}

	public static class Form extends IdForm<AdOwnerData> {
		private double income;
		private Integer otherRequestTimes;
		private Integer otherShowTimes;
		private Integer otherClickTimes;

        @Override
        public AdOwnerData newObj() {
            return new AdOwnerData();
        }

        @Override
        public void populateObj(AdOwnerData adOwnerData) {
        	adOwnerData.setIncome((int)(income*100));
        	adOwnerData.setOtherRequestTimes(otherRequestTimes);
        	adOwnerData.setOtherShowTimes(otherShowTimes);
        	adOwnerData.setOtherClickTimes(otherClickTimes);
        }

		public double getIncome() {
			return income;
		}

		public void setIncome(double income) {
			this.income = income;
		}

		public Integer getOtherRequestTimes() {
			return otherRequestTimes;
		}

		public void setOtherRequestTimes(Integer otherRequestTimes) {
			this.otherRequestTimes = otherRequestTimes;
		}

		public Integer getOtherShowTimes() {
			return otherShowTimes;
		}

		public void setOtherShowTimes(Integer otherShowTimes) {
			this.otherShowTimes = otherShowTimes;
		}

		public Integer getOtherClickTimes() {
			return otherClickTimes;
		}

		public void setOtherClickTimes(Integer otherClickTimes) {
			this.otherClickTimes = otherClickTimes;
		}
		
    }
	public static class Query extends com.zhanghui.pusher.common.Query {
		protected String from;
		protected String adOwner;
		protected String showWay;
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

		public String getAdOwner() {
			return adOwner;
		}

		public void setAdOwner(String adOwner) {
			this.adOwner = adOwner;
			this.addItem("adOwner", adOwner);
		}

		public String getShowWay() {
			return showWay;
		}

		public void setShowWay(String showWay) {
			this.showWay = showWay;
			this.addItem("showWay", showWay);
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
			this.addItem("from", from);
		}
		
        
    }
    @RequestMapping("/export")
	private void exportAdOwnerDataXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadAdOwnerDataTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			paginator.setQuery(query);
            List<AdOwnerData> list = service.list(paginator);
        	Iterator<AdOwnerData> it = list.iterator();
        	while(it.hasNext()){
        		AdOwnerData ta = it.next();
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
        		if(ta.getRequestTimes()==0){
        			ta.setRequestSuccessRate(0);
        		}else{
        			ta.setRequestSuccessRate(new BigDecimal((double)ta.getShowTimes()/(double)ta.getRequestTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        		}
        	}
			for(AdOwnerData ta : list ){
				XSSFRow row = sh.createRow(rowIdx);
                Object[] datas = new Object[]{
						String.class, ta.getDate(),
						String.class, ta.getAdOwner(),
						String.class, ta.getShowWay().equals("all")?"合计":(ta.getShowWay().equals("banner")?"横幅广告":(ta.getShowWay().equals("screen")?"插屏广告":(ta.getShowWay().equals("video")?"插屏广告":"开锁屏广告"))),
						String.class, ta.getFrom(),
						double.class, ta.getActiveUser().doubleValue(),
						double.class, ta.getValidActiveUser().doubleValue(),
						double.class, ta.getOtherRequestTimes().doubleValue(),
						double.class, ta.getRequestTimes().doubleValue(),
						double.class, ta.getOtherShowTimes().doubleValue(),
						double.class, ta.getShowTimes().doubleValue(),
						double.class, ta.getRequestSuccessRate(),	
						double.class, ta.getOtherClickTimes().doubleValue(),
						double.class, ta.getClickTimes().doubleValue(),
						double.class, ta.getClickRate(),
						double.class, new BigDecimal((double)ta.getIncome()/100.0).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(),
						double.class, ta.getClickPrice(),
						double.class, ta.getShowPrice()
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=adowner_data.xlsx");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
