package com.telappoint.adminresv.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.telappoint.adminresv.constants.ErrorCodeConstants;
import com.telappoint.adminresv.constants.FrontEndMessageConstants;
import com.telappoint.adminresv.constants.JspPageNameConstants;
import com.telappoint.adminresv.controller.common.CommonMessagesController;
import com.telappoint.adminresv.form.HomeBean;
import com.telappoint.adminresv.restws.model.BaseResponse;
import com.telappoint.adminresv.restws.model.ErrorBean;
import com.telappoint.adminresv.restws.model.EventDateTime;
import com.telappoint.adminresv.restws.model.EventDateTimeResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class EventDateTimeController extends CommonMessagesController{
	
	private Logger logger = Logger.getLogger(EventDateTimeController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SHOW_EVENT_DATE_TIME, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getEventDateTimeList(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_SHOW_EVENT_DATE_TIME;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				EventDateTimeResponse eventDateTimeResponse = adminResvService.getEventDateTimeList(homeBean.getBaseReq());
				model.addAttribute("eventDateTimeResponse",eventDateTimeResponse);	
				populateMessages(model);
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENTS_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SHOW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SHOW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SHOW.getDescription());
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
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_ADD_EVENT_DATE_TIME, method = RequestMethod.GET)
	public ModelAndView addEventDateTime(@ModelAttribute("homeBean") HomeBean homeBean,ModelMap model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_ADD_EVENT_DATE_TIME;		
		ModelAndView modelView = new ModelAndView();	
		try{
			model.addAttribute("locationListResponse",adminResvService.getLocationListDropDown(homeBean.getBaseReq()));				
			model.put("eventDateTime",new EventDateTime());
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_ADD.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_ADD.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_ADD.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_ADD.getDescription());
			model.put("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
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
	
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SAVE_EVENT_DATE_TIME, method = RequestMethod.POST)
	public String saveEventDateTime(@ModelAttribute("homeBean") HomeBean homeBean, ModelMap model,@ModelAttribute("eventDateTime")EventDateTime eventDateTime) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_EVENT_DATE_TIME+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		try{			
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				BaseResponse baseResponse = adminResvService.addEventDateTime(homeBean.getBaseReq(),eventDateTime);
				if(baseResponse.isResponseStatus() && !baseResponse.isErrorStatus()){
					sucessesMessage = FrontEndMessageConstants.UPDATE_EVENT_DATE_TIME_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.UPDATE_EVENT_SEATS_SUCESSES_MSG;
				}
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SAVE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SAVE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_SAVE.getDescription());
			model.put("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(eventDateTime));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return targetPage;  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_DELETE_EVENT_DATE_TIME, method = RequestMethod.GET)
	public @ResponseBody
		String deleteEventDateTime(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("eventDateTimeId") String eventDateTimeId) throws Exception {
		String jsonResponse = "";	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				//jsonResponse = adminResvService.deleteLocation(homeBean.getBaseReq(),eventDateTimeId);
			}
		}catch (Exception e) {
			logger.error("Error while delete Location : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("eventDateTimeId=["+eventDateTimeId+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return jsonResponse;		
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_EDIT_EVENT_DATE_TIME, method = RequestMethod.GET)
	public String editEventDateTime(@RequestParam("eventDateTimeId") String eventDateTimeId,
			@RequestParam("noOfNotifiedReservations") String noOfNotifiedReservations,@ModelAttribute("homeBean") HomeBean homeBean,	Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_EDIT_EVENT_DATE_TIME;	
		try{
			EventDateTime eventDateTime = adminResvService.getEventDateTimeById(homeBean.getBaseReq(),eventDateTimeId);
			model.addAttribute("eventDateTime",eventDateTime);
			model.addAttribute("noOfNotifiedReservations",noOfNotifiedReservations);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_EDIT.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_EDIT.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_EDIT.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_EDIT.getDescription());	
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("eventDateTimeId=["+eventDateTimeId+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
	    return targetPage;  
	} 

	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_UPDATE_EVENT_DATE_TIME, method = RequestMethod.POST)
	public ModelAndView updateEventDateTime(@ModelAttribute("homeBean") HomeBean homeBean, Model model,@ModelAttribute("eventDateTime")EventDateTime eventDateTime) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_EVENT_DATE_TIME+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				BaseResponse baseResponse = adminResvService.updateEventDateTime(homeBean.getBaseReq(),eventDateTime);
				if(baseResponse.isResponseStatus() && !baseResponse.isErrorStatus()){
					sucessesMessage = "Event Date Time updated Successfully!";
				}else{
					errorMessage = "Error while updating Event Date Time!";
				}
			}			
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_UPDATE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_UPDATE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_UPDATE.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(eventDateTime));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}

	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_EDIT_EVENT_SEATS, method = RequestMethod.GET)
	public String editEventSeats(@RequestParam("eventDateTimeId") String eventDateTimeId, @ModelAttribute("homeBean") HomeBean homeBean,Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_EDIT_EVENT_SEATS;	
		try{	
			EventDateTime eventDateTime = adminResvService.getEventDateTimeById(homeBean.getBaseReq(),eventDateTimeId);
			model.addAttribute("eventDateTime",eventDateTime);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_EDIT.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_EDIT.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_EDIT.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_EDIT.getDescription());	
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("eventDateTimeId=["+eventDateTimeId+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
	    return targetPage;  
	} 

	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_UPDATE_EVENT_SEATS, method = RequestMethod.POST)
	public ModelAndView updateEventSeats(@ModelAttribute("homeBean") HomeBean homeBean, Model model,@ModelAttribute("eventDateTime")EventDateTime eventDateTime) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_EVENT_DATE_TIME+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				BaseResponse baseResponse = adminResvService.updateEventSeats(homeBean.getBaseReq(),eventDateTime);
				if(baseResponse.isResponseStatus() && !baseResponse.isErrorStatus()){
					sucessesMessage = FrontEndMessageConstants.UPDATE_EVENT_SEATS_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.UPDATE_EVENT_SEATS_ERROR_MSG;
				}
			}			
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_UPDATE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_UPDATE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENT_DATE_TIME_UPDATE.getDescription());
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(eventDateTime));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_OPEN_CLOSE_EVENT_DATE_TIME, method = RequestMethod.GET)
	public String openCloseEventDateTime(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("eventDateTimeId") String eventDateTimeId,@RequestParam("enable") String enable) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_EVENT_DATE_TIME+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				BaseResponse baseRes = adminResvService.openCloseEventDateTime(homeBean.getBaseReq(),eventDateTimeId,enable);
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){					
					if("Y".equals(enable)){
						sucessesMessage = FrontEndMessageConstants.ENABLE_EVENT_DATE_TIME_SUCESSES_MSG;
					}else{
						sucessesMessage = FrontEndMessageConstants.DISABLE_EVENT_DATE_TIME_SUCESSES_MSG;
					}
				}else{
					if("Y".equals(enable)){
						sucessesMessage = FrontEndMessageConstants.ENABLE_EVENT_DATE_TIME_ERROR_MSG;
					}else{
						sucessesMessage = FrontEndMessageConstants.DISABLE_EVENT_DATE_TIME_ERROR_MSG;
					}
				}
			}
		}catch (Exception e) {
			logger.error("Error while openCloseEventDateTime : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("eventDateTimeId=["+eventDateTimeId+"],enable=["+enable+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}

}
