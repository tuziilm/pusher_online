package com.zhanghui.pusher.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.pusher.common.Country;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.domain.TopApp;
import com.zhanghui.pusher.service.TopAppService;

@Controller
@RequestMapping("/top_app")
public class TopAppController extends ListController<TopApp, TopAppService, TopAppController.Query> {
	private final Logger log=LoggerFactory.getLogger(getClass());
	public TopAppController() {
		super("top_app");
	}
	@Resource
	public void setTopAppService(TopAppService topAppService){
		this.service=topAppService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query query, Model model) {
    	model.addAttribute("countries", Country.countries);
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        return super.preList(page, paginator, query, model);
    }
    
    public static class Query extends com.zhanghui.pusher.common.Query {
        protected String country;

        public Query() {
        	setCountry("all");
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
	private void exportTaskActionInfoXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadTopAppTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			paginator.setQuery(query);
            List<TopApp> list = service.list(paginator);
			for(TopApp ta : list ){
				XSSFRow row = sh.createRow(rowIdx);
                Object[] datas = new Object[]{
						String.class, ta.getAppName(),
						double.class, ta.getActiveUser().doubleValue(),
						double.class, ta.getUseTimes().doubleValue(),
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=top10_app.xlsx");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
