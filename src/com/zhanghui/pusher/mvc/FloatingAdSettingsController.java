package com.zhanghui.pusher.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.pusher.common.IdForm;
import com.zhanghui.pusher.common.Query;
import com.zhanghui.pusher.common.RemarkForm;
import com.zhanghui.pusher.domain.FloatingAdSettings;
import com.zhanghui.pusher.service.FloatingAdSettingsService;

@Controller
@RequestMapping("/floating_ad/settings")
public class FloatingAdSettingsController extends CRUDController<FloatingAdSettings, FloatingAdSettingsService, FloatingAdSettingsController.Form, Query.NameQuery> {
    public FloatingAdSettingsController() {
        super("floating_ad/settings");
    }

    @Resource
    public void setFloatingAdSettingsService(FloatingAdSettingsService floatingAdSettingsService) {
        this.service = floatingAdSettingsService;
    }

    @Override
    public void innerSave(Form form, BindingResult errors, Model model,
                          HttpServletRequest request, HttpServletResponse response) {
    	FloatingAdSettings floatingAdSettings = form.toObj();
        try {
            service.saveOrUpdate(floatingAdSettings);
        } catch (Exception e) {
            errors.addError(new ObjectError("ex", e.getMessage()));
        }
    }

    public static class Form extends RemarkForm<FloatingAdSettings> {
        @NotNull(message = "百分比不能为空")
        @Range(min=0,max=100,message="百分比只能在0到100之间")
        private int percent;
        
        public int getPercent() {
			return percent;
		}

		public void setPercent(int percent) {
			this.percent = percent;
		}

		@Override
        public FloatingAdSettings newObj() {
            return new FloatingAdSettings();
        }

		@Override
		public void populateObj(FloatingAdSettings floatingAdSettings) {
			floatingAdSettings.setPercent(percent);
		}
    }
}
