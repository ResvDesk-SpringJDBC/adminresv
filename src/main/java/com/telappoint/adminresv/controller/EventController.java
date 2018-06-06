package com.telappoint.adminresv.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.telappoint.adminresv.restws.model.Event;
import com.telappoint.adminresv.restws.model.EventListResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class EventController extends CommonMessagesController{
	
	private Logger logger = Logger.getLogger(EventController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SHOW_EVENTS, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getEventList(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_SHOW_EVENTS;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				EventListResponse eventListResponse = adminResvService.getEventList(homeBean.getBaseReq());
				model.addAttribute("eventListResponse",eventListResponse);			
			}
			populateMessages(model);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENTS_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_SHOW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_SHOW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_SHOW.getDescription());
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
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_ADD_EVENT, method = RequestMethod.GET)
	public ModelAndView addEvent(@ModelAttribute("homeBean") HomeBean homeBean,ModelMap model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_ADD_EVENT;		
		ModelAndView modelView = new ModelAndView();	
		try{
			model.addAttribute("locationListResponse",adminResvService.getLocationListDropDown(homeBean.getBaseReq()));
			model.put("event",new Event());
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENTS_ADD.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_ADD.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_ADD.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_ADD.getDescription());
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
	
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SAVE_EVENT, method = RequestMethod.POST)
	public String saveEvent(@ModelAttribute("homeBean") HomeBean homeBean, ModelMap model,@ModelAttribute("event") Event event, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_EVENTS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		try{			
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				event.setLocationIds(AdminUtils.concatWithSeparator(event.getSelectedLocIdsList(),","));
				
				BaseResponse baseRes = adminResvService.addEvent(homeBean.getBaseReq(),event);
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){
					sucessesMessage = FrontEndMessageConstants.SAVE_EVENT_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.SAVE_EVENT_ERROR_MSG;
				}	
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENTS_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_SAVE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_SAVE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_SAVE.getDescription());
			model.put("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(event));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return targetPage;  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_EDIT_EVENT, method = RequestMethod.GET)
	public String showEditEventPage( @RequestParam("id") String id, @ModelAttribute("homeBean") HomeBean homeBean, Model model, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_EDIT_EVENT;	
		try{	
			EventListResponse eventListResponse = adminResvService.getEventById(homeBean.getBaseReq(),id);
			if(eventListResponse!=null && eventListResponse.isResponseStatus() && !eventListResponse.isErrorStatus() 
					&& eventListResponse.getEventList().size()>0){
				Event event = eventListResponse.getEventList().get(0);
				if(event.getLocationIds()!=null && !"".equals(event.getLocationIds()) && !",".equals(event.getLocationIds())){
					event.setSelectedLocIdsList(event.getLocationIds().split(","));
				}
				model.addAttribute("event",event);
			}else{
				model.addAttribute("event",new Event());
			}
			model.addAttribute("locationListResponse",adminResvService.getLocationListDropDown(homeBean.getBaseReq()));
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENTS_EDIT.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_EVENTS_EDIT.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_EDIT.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_EDIT.getDescription());	
			model.addAttribute("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("id=["+id+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}		
	    return targetPage;  
	} 
 
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_UPDATE_EVENT, method = RequestMethod.POST)
	public ModelAndView updateEvent(@ModelAttribute("homeBean") HomeBean homeBean, ModelMap model, @ModelAttribute("event")Event event) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_EVENTS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				
				event.setLocationIds(AdminUtils.concatWithSeparator(event.getUpdateSelectedLocIdsList(),","));
				
				BaseResponse baseRes = adminResvService.updateEvent(homeBean.getBaseReq(), event);
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){
					sucessesMessage = FrontEndMessageConstants.UPDATE_EVENT_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.UPDATE_EVENT_ERROR_MSG;
				}
			}			
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_EVENTS_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_UPDATE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_UPDATE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_EVENTS_UPDATE.getDescription());
			model.put("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(event));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}
	
	public @ResponseBody
		String deleteEvent(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("eventId") String eventId) throws Exception {
		String jsonResponse = "";	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				jsonResponse = adminResvService.deleteEvent(homeBean.getBaseReq(),eventId);
			}
		}catch (Exception e) {
			logger.error("Error while delete Event : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("eventId=["+eventId+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return jsonResponse;		
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_OPEN_CLOSE_EVENT, method = RequestMethod.GET)
	public String openCloseEvent(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("eventId") String eventId,@RequestParam("enable") String enable) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_EVENTS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				BaseResponse baseRes = adminResvService.openCloseEvent(homeBean.getBaseReq(),eventId,enable);
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){					
					if("Y".equals(enable)){
						sucessesMessage = FrontEndMessageConstants.ENABLE_EVENT_SUCESSES_MSG;
					}else{
						sucessesMessage = FrontEndMessageConstants.DISABLE_EVENT_SUCESSES_MSG;
					}
				}else{
					if("Y".equals(enable)){
						sucessesMessage = FrontEndMessageConstants.ENABLE_EVENT_ERROR_MSG;
					}else{
						sucessesMessage = FrontEndMessageConstants.DISABLE_EVENT_ERROR_MSG;
					}
				}
			}
		}catch (Exception e) {
			logger.error("Error while openCloseEvent : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("eventId=["+eventId+"],enable=["+enable+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}
}
