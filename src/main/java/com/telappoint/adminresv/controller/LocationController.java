package com.telappoint.adminresv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.telappoint.adminresv.restws.model.Location;
import com.telappoint.adminresv.restws.model.LocationListResponse;
import com.telappoint.adminresv.service.IAdminResvService;
import com.telappoint.adminresv.utils.AdminUtils;


@Controller
@SessionAttributes(value="homeBean",types=HomeBean.class)
public class LocationController  extends CommonMessagesController{
	
	private Logger logger = Logger.getLogger(LocationController.class);
	
	@Autowired
	private IAdminResvService adminResvService;
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SHOW_LOCATIONS, method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView getLocations(@ModelAttribute("homeBean") HomeBean homeBean, Model model) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_SHOW_LOCATIONS;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				LocationListResponse locationListResponse = adminResvService.getLocationList(homeBean.getBaseReq());
				model.addAttribute("locationListResponse",locationListResponse);
				model.addAttribute("states", AdminUtils.getStatesMap());
			}
			populateMessages(model);
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_LOCATIONS_SHOW.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_SHOW.getPage(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_SHOW.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_SHOW.getDescription());
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
		
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_ADD_LOCATION, method = RequestMethod.GET)
	public ModelAndView addLocation(@ModelAttribute("homeBean") HomeBean homeBean,ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_ADD_LOCATION;		
		ModelAndView modelView = new ModelAndView();	
		try{
			Location location = new Location();
			location.setState(homeBean.getAdminInfoTO().getClientTO().getState());
			location.setZip(homeBean.getAdminInfoTO().getClientTO().getZip());
			location.setTimeZone(homeBean.getAdminInfoTO().getClinetTimeZone());
			location.populateDisplayFieldsData();
			model.put("location",location);
			model.addAttribute("states", AdminUtils.getStatesMap());
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_LOCATIONS_ADD.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_ADD.getPage(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_ADD.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_ADD.getDescription());
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
	
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_SAVE_LOCATION, method = RequestMethod.POST)
	public String saveLocation(@ModelAttribute("homeBean") HomeBean homeBean, ModelMap model,
			@ModelAttribute("location") Location location, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_LOCATIONS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;
		try{			
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				BaseResponse baseRes = adminResvService.addLocation(homeBean.getBaseReq(),location);				
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){
					sucessesMessage = FrontEndMessageConstants.SAVE_LOCATION_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.SAVE_LOCATION_ERROR_MSG;
				}	
			}
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_LOCATIONS_SAVE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_SAVE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_SAVE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_SAVE.getDescription());
			model.put("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(location));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
	    return targetPage;  
	}
	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_EDIT_LOCATION, method = RequestMethod.GET)
	public String showEditLocationPage(@RequestParam("id") String id, @ModelAttribute("homeBean") HomeBean homeBean, Model model, HttpServletRequest request) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_EDIT_LOCATION;	
		try{	
			LocationListResponse locationListResponse = adminResvService.getLocationById(homeBean.getBaseReq(),id);
			Location location = locationListResponse.getLocationList().get(0);
			location.populateDisplayFieldsData();
			model.addAttribute("location",location);
			model.addAttribute("states", AdminUtils.getStatesMap());
			/*List<ProcedureTO> procedureTOList = adminApptService.getProcedureList(homeBean.getBaseReq().getClientCode(),"N");
			model.addAttribute("procedureTOList", procedureTOList);*/
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_LOCATIONS_EDIT.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_EDIT.getPage(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_EDIT.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_EDIT.getDescription());	
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

	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_UPDATE_LOCATION, method = RequestMethod.POST)
	public ModelAndView updateLocation(@ModelAttribute("homeBean") HomeBean homeBean, ModelMap model, @ModelAttribute("locationTO")
		Location location, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_LOCATIONS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;		
		ModelAndView modelView = new ModelAndView();	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){	
				BaseResponse baseRes = adminResvService.updateLocation(homeBean.getBaseReq(),location);
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){
					sucessesMessage = FrontEndMessageConstants.UPDATE_LOCATION_SUCESSES_MSG;
				}else{
					errorMessage = FrontEndMessageConstants.UPDATE_LOCATION_ERROR_MSG;
				}	
			}			
		}catch (Exception e) {
			logger.error(ErrorCodeConstants.ERROR_CODE_LOCATIONS_UPDATE.getDescription()+" : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			ErrorBean errorBean = new ErrorBean(AdminUtils.getClientCode(homeBean),APPCODE,AdminUtils.getTransId(homeBean),
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_UPDATE.getPage(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_UPDATE.getErrorCode(), 
					ErrorCodeConstants.ERROR_CODE_LOCATIONS_UPDATE.getDescription());
			model.put("errorBean", errorBean);
			targetPage = JspPageNameConstants.ADMIN_RESV_ERROR_PAGE;
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString(location));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		modelView.setViewName(targetPage);
	    return modelView;  
	}

	
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_DELETE_LOCATION, method = RequestMethod.GET)
	public @ResponseBody
		String deleteLocation(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("locationId") String locationId) throws Exception {
		String jsonResponse = "";	
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				jsonResponse = adminResvService.deleteLocation(homeBean.getBaseReq(),locationId);
			}
		}catch (Exception e) {
			logger.error("Error while delete Location : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("locationId=["+locationId+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return jsonResponse;		
	}
 
	@RequestMapping(value=JspPageNameConstants.ADMIN_RESV_OPEN_CLOSE_LOCATION, method = RequestMethod.GET)
	public String openCloseLocation(@ModelAttribute("homeBean") HomeBean homeBean,@RequestParam("locationId") String locationId,@RequestParam("enable") String enable) throws Exception {
		String targetPage = JspPageNameConstants.ADMIN_RESV_REDIRECT+JspPageNameConstants.ADMIN_RESV_SHOW_LOCATIONS+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION;		
		try{
			if(homeBean!=null && homeBean.getBaseReq()!=null){
				BaseResponse baseRes = adminResvService.openCloseLocation(homeBean.getBaseReq(),locationId,enable);
				if(baseRes!=null && !baseRes.isErrorStatus() && baseRes.isResponseStatus()){					
					if("Y".equals(enable)){
						sucessesMessage = FrontEndMessageConstants.ENABLE_LOCATION_SUCESSES_MSG;
					}else{
						sucessesMessage = FrontEndMessageConstants.DISABLE_LOCATION_SUCESSES_MSG;
					}
				}else{
					if("Y".equals(enable)){
						errorMessage = FrontEndMessageConstants.ENABLE_LOCATION_ERROR_MSG;
					}else{
						errorMessage = FrontEndMessageConstants.DISABLE_LOCATION_ERROR_MSG;
					}
				}
			}
		}catch (Exception e) {
			logger.error("Error while openCloseLocation : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("locationId=["+locationId+"],enable=["+enable+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}
	
		
/*	
	@RequestMapping(value=JspPageNameConstants.APPTADMINDESK_UPDATE_LOCATION_OPEN_DETAILS, method = RequestMethod.GET)
	public String openLocation(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_REDIRECT+JspPageNameConstants.APPTADMINDESK_SHOW_LOCATIONS_PAGE+JspPageNameConstants.APPTADMINDESK_HTML_EXTENSION;	
		try{
			LocationTO locationTO =  new LocationTO();
			locationTO.setClientCode(homeBean.getBaseReq().getClientCode());
			locationTO.setId(Integer.parseInt(id));
			locationTO.setClosed("N");
			adminApptService.updateLocationClosedDetails(locationTO);
		}catch (Exception e) {
			logger.error("Error while opening Location : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			enableDisableResponseMsg = "Error while opening "+homeBean.getDisplayNamesTO().getLocation_name();
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("Id=["+id+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}
	
	@RequestMapping(value=JspPageNameConstants.APPTADMINDESK_UPDATE_LOCATION_CLOSE_DETAILS, method = RequestMethod.GET)
	public String closeLocation(@ModelAttribute("homeBean") HomeBean homeBean, @RequestParam("id") String id) throws Exception {
		String targetPage = JspPageNameConstants.APPTADMINDESK_REDIRECT+JspPageNameConstants.APPTADMINDESK_SHOW_LOCATIONS_PAGE+JspPageNameConstants.APPTADMINDESK_HTML_EXTENSION;	
		try{
			LocationTO locationTO =  new LocationTO();
			locationTO.setClientCode(homeBean.getBaseReq().getClientCode());
			locationTO.setId(Integer.parseInt(id));
			locationTO.setClosed("Y");
			adminApptService.updateLocationClosedDetails(locationTO);
			adminApptService.updateTransanctionState(homeBean.getBaseReq().getClientCode(),homeBean.getTransId(),ControllerHelper.getTransactionState(PageStatusConstants.PAGE_STATUS_LOC_CLOSED.getPage()));
		}catch (Exception e) {
			logger.error("Error while closing Location : "+((e.getMessage() !=null)?e.getMessage():"") +e.toString(),e);
			enableDisableResponseMsg = "Error while closing "+homeBean.getDisplayNamesTO().getLocation_name();
			
			String subject = "Error in AdminResv for Client - "+homeBean.getBaseReq().getClientCode();
			StringBuilder errorMsg =new StringBuilder();
			errorMsg.append("MethodName:"+AdminUtils.removeErrorNumber(AdminUtils.getMethodName(1)));
			errorMsg.append("<br/>");
			errorMsg.append(AdminUtils.getJSONString("Id=["+id+"]"));
			errorMsg.append("<br/> Caused By:"+((e.getMessage() !=null)?e.getMessage():"") +e.toString()+"<br/>");
			AdminUtils.sendErrorEmail(errorMsg,e,subject);
		}
		return targetPage;
	}*/
}
