package com.zhanghui.pusher.mvc;

import com.google.common.base.Strings;
import com.zhanghui.pusher.common.Country;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.domain.HighChartData;
import com.zhanghui.pusher.domain.PageView;
import com.zhanghui.pusher.domain.TotalData;
import com.zhanghui.pusher.mvc.TotalDataController.Query;
import com.zhanghui.pusher.service.LinkNodeService;
import com.zhanghui.pusher.service.PageViewService;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * PV访问统计显示
 *
 */
@Controller
@RequestMapping("/pv")
public class PageViewController extends ListController<PageView, PageViewService, PageViewController.Query> {
    private final static Logger log = LoggerFactory.getLogger(PageViewController.class);
    @Resource
    private LinkNodeService linkNodeService;
    protected final String CHART_VIEW;

    public PageViewController() {
        super("pv");
        CHART_VIEW = String.format("/%s/chart", "pv");
    }

    @Resource
    public void setPageViewService(PageViewService service) {
        this.service = service;
    }

    @RequestMapping("/chart")
    public String chart(Model model, ChartQuery query) throws Exception {
        model.addAttribute("linkNodeMap", linkNodeService.getMap());
        model.addAttribute("linkNodes", linkNodeService.getList());
        model.addAttribute("countries", Country.countries);
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        Paginator paginator = new Paginator(1, 10000);
        paginator.setQuery(query);
        List<PageView> list = service.getChartDatas(paginator);
        HighChartData hcd = service.handleList(list, query.startTime, query.endTime);
        model.addAttribute("jsonStr", hcd.toString());
        return CHART_VIEW;
    }

    @Override
    protected boolean preList(int page, Paginator paginator, Query query,	Model model) {
        model.addAttribute("linkNodeMap", linkNodeService.getMap());
        model.addAttribute("linkNodes", linkNodeService.getList());
        model.addAttribute("countries", Country.countries);
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        return super.preList(page, paginator, query, model);
    }

    public static class Query extends com.zhanghui.pusher.common.Query {
        private String code;
        private String country;
        protected String module;
        protected String startTime;
        private String from;

        public Query() {
            this.startTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
            this.module = "LINK_NODE_DAY_1";
            setCountry("all");
            setFrom("all");
            setCode("all");
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
            this.addItem("from", from);
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
            this.addItem("country", country);
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime.replaceAll("/", "-");
            this.addItem("startTime", startTime);
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
            this.addItem("code", code);
        }
    }

    public static class ChartQuery extends Query {
        protected String endTime;

        public ChartQuery() {
            this.startTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -30), "yyyy-MM-dd");
            this.endTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime.replaceAll("/", "-");
            this.addItem("endTime", endTime);
        }

        @Override
        public void setFrom(String from) {
            if(Strings.isNullOrEmpty(from)){
                from="all";
            }
            super.setFrom(from);
        }

        @Override
        public void setCountry(String country) {
            if(Strings.isNullOrEmpty(country)){
                country="all";
            }
            super.setCountry(country);
        }

        @Override
        public void setCode(String code) {
            if(Strings.isNullOrEmpty(code)){
                code="all";
            }
            super.setCode(code);
        }
    }
    @RequestMapping("/export")
	private void exportPvUvDataXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadPvUvDataTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			paginator.setQuery(query);
            List<PageView> list = service.list(paginator);
			for(PageView pv : list ){
				XSSFRow row = sh.createRow(rowIdx);
                Object[] datas = new Object[]{
						String.class, pv.getCode().equalsIgnoreCase("all")?"所有链接":linkNodeService.getMap().get(pv.getCode()).getName(),
						String.class, pv.getCountry().equalsIgnoreCase("all")?"所有国家":Country.shortcut2CountryMap.get(pv.getCountry()).getChineseName(),
						String.class, pv.getFrom().equalsIgnoreCase("all")?"所有客户":pv.getFrom(),
						double.class, pv.getPv().doubleValue(),
						double.class, pv.getUv().doubleValue()
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=pvuv_data.xlsx");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
