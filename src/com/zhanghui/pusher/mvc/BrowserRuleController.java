package com.zhanghui.pusher.mvc;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.Query;
import com.zhanghui.pusher.common.RemarkForm;
import com.zhanghui.pusher.domain.BrowserRule;
import com.zhanghui.pusher.service.BrowserRuleService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/browser/rule")
public class BrowserRuleController extends CRUDController<BrowserRule, BrowserRuleService, BrowserRuleController.Form, Query.NameQuery>{
	public BrowserRuleController() {
		super("browser/rule");
	}
	@Resource
	public void setBrowserRuleService(BrowserRuleService browserRuleService){
		this.service=browserRuleService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query.NameQuery query, Model model) {
        paginator.setNeedTotal(true);
        return super.preList(page, paginator, query, model);
    }

    @Override
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		BrowserRule browserRule=form.toObj();
		try{
            service.saveOrUpdate(browserRule);
		} catch (Exception e) {
			errors.addError(new ObjectError("ex", e.getMessage()));
		} 
	}

	public static class Form extends RemarkForm<BrowserRule> {
        @NotBlank(message = "名称不能为空")
        private String name;
        @NotBlank(message = "包名不能为空")
        private String packageName;
        @NotBlank(message = "主Activity不能为空")
        private String mainActivity;

        @Override
        public BrowserRule newObj() {
            return new BrowserRule();
        }

        @Override
        public void populateObj(BrowserRule browserRule) {
            browserRule.setName(name);
            browserRule.setPackageName(packageName);
            browserRule.setMainActivity(mainActivity);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(String mainActivity) {
            this.mainActivity = mainActivity;
        }
    }
}
