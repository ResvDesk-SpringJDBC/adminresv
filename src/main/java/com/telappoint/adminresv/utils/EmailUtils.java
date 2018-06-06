package com.telappoint.adminresv.utils;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * @author Murali
 * 
 * 
 */

@Service("emailUtils")
public class EmailUtils {
	
	private static Logger log = Logger.getLogger(EmailUtils.class);
	@Async("resvMailExecutor")
	public void sendEmail(String subject, String body,String mailType) {
		log.error("Sending mail for ::: "+mailType);
		if("SCHEDULER_DETAILS_CHANGED".equals(mailType)){
			PropertyUtils.sendSchedulerDetailsChangedAlertMail(subject,body);
		}
	}

}
