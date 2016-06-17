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
import com.zhanghui.pusher.common.Country;
import com.zhanghui.pusher.common.IdForm;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.domain.CountryData;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.service.CountryDataService;

@Controller
@RequestMapping("/data_statistics/country")
public class CountryDataController extends CRUDController<CountryData, CountryDataService, com.zhanghui.pusher.mvc.CountryDataController.Form, CountryDataController.Query>{
	private final Logger log=LoggerFactory.getLogger(getClass());
	public CountryDataController() {
		super("data_statistics/country");
	}
	@Resource
	public void setCountryDataService(CountryDataService countryDataService){
		this.service=countryDataService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query query, Model model) {
        paginator.setNeedTotal(true);
        model.addAttribute("adOwners", AdOwner.adOwners);
        model.addAttribute("countries", Country.countries);
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        return super.preList(page, paginator, query, model);
    }
    @Override
    protected void postList(int page, Paginator paginator, Query query, Model model) {
    	SumData sumData = this.service.getSum(paginator);
    	if(sumData!=null){
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
    	List<CountryData> list = (List<CountryData>)model.asMap().get("datas");
    	Iterator<CountryData> it = list.iterator();
    	while(it.hasNext()){
    		CountryData ta = it.next();
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
    	CountryData totalData=form.toObj();
    	service.update(totalData);
	}

	public static class Form extends IdForm<CountryData> {
		private double income;
		private Integer otherRequestTimes;
		private Integer otherShowTimes;
		private Integer otherClickTimes;

        @Override
        public CountryData newObj() {
            return new CountryData();
        }

        @Override
        public void populateObj(CountryData countryData) {
        	countryData.setIncome((int)(income*100));
        	countryData.setOtherRequestTimes(otherRequestTimes);
        	countryData.setOtherShowTimes(otherShowTimes);
        	countryData.setOtherClickTimes(otherClickTimes);
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
		protected String adOwner;
		protected String country;
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

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
			this.addItem("country", country);
		}
        
    }
    @RequestMapping("/export")
	private void exportCountryDataXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadCountryDataTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			paginator.setQuery(query);
            List<CountryData> list = service.list(paginator);
        	Iterator<CountryData> it = list.iterator();
        	while(it.hasNext()){
        		CountryData ta = it.next();
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
			for(CountryData ta : list ){
				XSSFRow row = sh.createRow(rowIdx);
                Object[] datas = new Object[]{
						String.class, ta.getDate(),
						String.class, Country.shortcut2CountryMap.get(ta.getCountry()).getChineseName(),
						String.class, ta.getAdOwner(),
						double.class, ta.getActiveUser().doubleValue(),
						double.class, ta.getValidActiveUser().doubleValue(),
						double.class, ta.getAddUser().doubleValue(),
						double.class, ta.getOtherRequestTimes().doubleValue(),
						double.class, ta.getRequestTimes().doubleValue(),
						double.class, ta.getOtherShowTimes().doubleValue(),
						double.class, ta.getShowTimes().doubleValue(),
						double.class, ta.getRequestSuccessRate(),	
						double.class, ta.getOtherClickTimes().doubleValue(),
						double.class, ta.getClickTimes().doubleValue(),
						double.class, ta.getClickRate(),
						double.class, new BigDecimal((double)ta.getIncome()/100.0).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(),
						double.class, ta.getShowPrice()
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=country_data.xlsx");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
