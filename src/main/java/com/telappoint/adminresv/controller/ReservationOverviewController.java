package com.telappoint.adminresv.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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

import com.telappoint.adminresv.constants.AdminResvConstants;
import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.CalendarOverviewDetailsResponse;
import com.telappoint.adminresv.restws.model.CalendarOverviewResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;
import com.telappoint.adminresv.utils.DateUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class ReservationOverviewController implements AdminResvConstants{
	
	private Logger logger = Logger.getLogger(ReservationOverviewController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_RESERVATION_OVERVIEW, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getReservationOverview(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_RESERVATION_OVERVIEW;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				String startDate = DateUtils.getStringFromDate(new Date(),DateUtils.MMDDYYYY_DATE_FORMAT);
				String endDate = DateUtils.getDateString(DateUtils.MMDDYYYY_DATE_FORMAT,30);
				CalendarOverviewResponse calOverviewResponse = adminResvService.getReservationOverview(homeBean.getBaseReq(),startDate,endDate);
				model.addAttribute("calOverviewResponse",calOverviewResponse);		
				model.addAttribute("startDate",startDate);		
				model.addAttribute("endDate",endDate);		
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No Input parameters"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_SEARCH_RESERVATION_OVERVIEW, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView searchReservationOverview(@ModelAttribute("homeBean") HomeBean homeBean,
			 @RequestParam String startDate, @RequestParam String endDate,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_RESERVATION_OVERVIEW;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				CalendarOverviewResponse calOverviewResponse = adminResvService.getReservationOverview(homeBean.getBaseReq(),startDate,endDate);
				model.addAttribute("calOverviewResponse",calOverviewResponse);
				model.addAttribute("startDate",startDate);		
				model.addAttribute("endDate",endDate);	
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
		
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No Input parameters"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_RESERVATIONS_RESERVATION_OVERVIEW_DETAILS, method = RequestMethod.GET)
	public ModelAndView getReservationOverviewDetails(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("eventDateTimeId") String eventDateTimeId,
			@RequestParam("locationName") String locationName,
			@RequestParam("eventName") String eventName,
			@RequestParam("eventDateTime") String eventDateTime,
			@RequestParam("totalSeats") String totalSeats,
			@RequestParam("bookedSeats") String bookedSeats,
			Model model, HttpServletRequest request) throws Exception {
		ModelAndView modelView = new ModelAndView();
		String targetPage = JspPageNameConstants.ADMIN_RESV_RESERVATIONS_RESERVATION_OVERVIEW_DETAILS;	
		try{
			CalendarOverviewDetailsResponse overviewDetailsResponse = adminResvService.getReservationOverviewDetails(homeBean.getBaseReq(),eventDateTimeId);
			modelView.addObject("overviewDetailsResponse",overviewDetailsResponse);
			
			modelView.addObject("locationName",locationName);
			modelView.addObject("eventName",eventName);
			modelView.addObject("eventDateTime",eventDateTime);
			modelView.addObject("totalSeats",totalSeats);
			modelView.addObject("bookedSeats",bookedSeats);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW_DETAILS.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW_DETAILS.getPage(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW_DETAILS.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_RESERVATIONS_RESERVATION_OVERVIEW_DETAILS.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+AdminUtils.getClientCode(homeBean);
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("No input parameters"));
			errorMsg.append("<br/> Caused By:"+e.getMessage()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
		return modelView;
		
	}
}
