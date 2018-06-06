package com.telappoint.adminresv.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.client.contants.ResvDeskContants;
import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.ReservationSearchResponse;
import com.telappoint.adminresv.restws.model.SearchFieldsResponse;
import com.telappoint.adminresv.restws.model.SearchRequestData;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationSearchController implements AdminResvConstants{
	
	private Logger logger = Logger.getLogger(ReservationSearchController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SEARCH_RESERVATIONS_CALENDAR, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchReservations(@ModelAttribute("homeBean") HomeBean homeBean, 
			@ModelAttribute("resvSearchRequest")SearchRequestData resvSearchRequest,@RequestParam(value="isSearchReq",required=false)String isSearchReq,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_SEARCH_RESERVATIONS_CALENDAR;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				ReservationSearchResponse resvSearchResponse = null;
				if(isSearchReq!=null && "yes".equals(isSearchReq)){
					resvSearchResponse = adminResvService.getReservationSearch(homeBean.getBaseReq(),resvSearchRequest);
				}else{
					resvSearchResponse = new ReservationSearchResponse();
				}
				model.addAttribute("resvSearchResponse",resvSearchResponse);
				SearchFieldsResponse searchFieldsResponse = adminResvService.getDynamicSearchFields(homeBean.getBaseReq(),ResvDeskContants.SEARCH_CATEGORY_RESERVATION_SEARCH.getValue());
				model.addAttribute("searchFieldsResponse",searchFieldsResponse);
				model.addAttribute("resvSearchRequest",resvSearchRequest);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_SEARCH_RESERVATIONS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_SEARCH_RESERVATIONS.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_SEARCH_RESERVATIONS.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_SEARCH_RESERVATIONS.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(resvSearchRequest));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
}
