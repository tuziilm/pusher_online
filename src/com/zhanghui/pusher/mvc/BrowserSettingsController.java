package com.zhanghui.pusher.mvc;

import com.zhanghui.pusher.common.IdForm;
import com.zhanghui.pusher.common.Query;
import com.zhanghui.pusher.domain.BrowserSettings;
import com.zhanghui.pusher.service.BrowserSettingsService;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/browser/settings")
public class BrowserSettingsController extends CRUDController<BrowserSettings, BrowserSettingsService, BrowserSettingsController.Form, Query.NameQuery> {
    public BrowserSettingsController() {
        super("browser/settings");
    }

    @Resource
    public void setBrowserSettingsService(BrowserSettingsService browserSettingsService) {
        this.service = browserSettingsService;
    }

    @Override
    public void innerSave(Form form, BindingResult errors, Model model,
                          HttpServletRequest request, HttpServletResponse response) {
        BrowserSettings browserSettings = form.toObj();
        try {
            service.saveOrUpdate(browserSettings);
        } catch (Exception e) {
            errors.addError(new ObjectError("ex", e.getMessage()));
        }
    }

    public static class Form extends IdForm<BrowserSettings> {
        @URL(message = "主页格式不正确")
        private String homePage;
        @NotNull(message = "时间间隔不能为空")
        @Min(value = 1, message = "时间间隔不能小于1")
        private Integer accessDayInterval;

        @Override
        public BrowserSettings newObj() {
            return new BrowserSettings();
        }

        @Override
        public void populateObj(BrowserSettings browserSettings) {
            browserSettings.setAccessDayInterval(accessDayInterval);
            browserSettings.setHomePage(homePage);
        }

        public String getHomePage() {
            return homePage;
        }

        public void setHomePage(String homePage) {
            this.homePage = homePage;
        }

        public Integer getAccessDayInterval() {
            return accessDayInterval;
        }

        public void setAccessDayInterval(Integer accessDayInterval) {
            this.accessDayInterval = accessDayInterval;
        }
    }
}
