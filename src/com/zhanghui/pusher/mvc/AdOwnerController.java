package com.zhanghui.pusher.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.Query;
import com.zhanghui.pusher.common.RemarkForm;
import com.zhanghui.pusher.domain.AdOwner;
import com.zhanghui.pusher.service.AdOwnerService;

@Controller
@RequestMapping("/adv/owner")
public class AdOwnerController extends CRUDController<AdOwner, AdOwnerService, com.zhanghui.pusher.mvc.AdOwnerController.Form, Query.NameQuery>{
	private final Logger log=LoggerFactory.getLogger(getClass());
	public AdOwnerController() {
		super("adv/owner");
	}
	@Resource
	public void setAdOwnerService(AdOwnerService adOwnerService){
		this.service=adOwnerService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query.NameQuery query, Model model) {
        paginator.setNeedTotal(true);
        return super.preList(page, paginator, query, model);
    }

    @Override
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
    	AdOwner adOwner=form.toObj();
    	service.saveOrUpdate(adOwner);
	}

	public static class Form extends RemarkForm<AdOwner> {
        @NotBlank(message="广告主名称不能为空")
		private String name;

        @Override
        public AdOwner newObj() {
            return new AdOwner();
        }

        @Override
        public void populateObj(AdOwner adOwner) {
        	adOwner.setName(name);
        	adOwner.setRemark(remark);
        }

        public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
    }
}
