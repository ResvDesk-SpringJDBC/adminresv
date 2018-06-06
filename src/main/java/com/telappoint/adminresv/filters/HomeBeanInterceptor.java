package com.telappoint.adminresv.filters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.telappoint.adminresv.constants.JspPageNameConstants;

public class HomeBeanInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(HomeBeanInterceptor.class);
	
	 public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) 
	 	throws IOException, ServletException {
			
			logger.info("HomeBean in Session : "+request.getSession().getAttribute("homeBean") );
			if (request.getSession().getAttribute("homeBean") == null) {				
				String requestURI = request.getRequestURI();
				if(shouldRedirect(requestURI)) {
					response.sendRedirect(JspPageNameConstants.ADMIN_RESV_SESSION_EXPIRED+JspPageNameConstants.ADMIN_RESV_HTML_EXTENSION);
					logger.error("Redirecting to "+JspPageNameConstants.ADMIN_RESV_SESSION_EXPIRED+" - Because HomeBean in Session : "+request.getSession().getAttribute("homeBean") );
					return false; // request handled, no need to bother controller
				}
			}			 
			return true;
	 }
		
	 private boolean shouldRedirect(String requestURI) {
		boolean doNotRedirect = requestURI.startsWith("/adminresv/log") || requestURI.startsWith("/adminresv/sendResetPasswordRequest") 
			|| requestURI.startsWith("/adminresv/resetPasswordRequest")  || requestURI.startsWith("/adminresv/updatePassword") 
			|| requestURI.startsWith("/adminresv/resetpassword") || requestURI.startsWith("/adminresv/validateResetPassword")
			|| requestURI.startsWith("/adminresv/session-expired") 
			|| requestURI.contains(".css") || requestURI.contains(".js") 
			|| requestURI.contains(".png") || requestURI.contains(".jp")
			|| requestURI.endsWith("/adminresv/") || requestURI.endsWith("/adminresv");
		
		return !doNotRedirect;
	 }
}
