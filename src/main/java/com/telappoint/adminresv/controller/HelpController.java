package com.telappoint.adminresv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;

@Controller
public class HelpController implements AdminResvConstants {
		
	@RequestMapping(value = JspPageNameConstants.ADMIN_RESV_HELP_PAGE, method = RequestMethod.GET)
	public ModelAndView showHelpPage() throws Exception {
		ModelAndView modelView = new ModelAndView();

		modelView.setViewName(JspPageNameConstants.ADMIN_RESV_HELP_PAGE);
		return modelView;
	}
}
