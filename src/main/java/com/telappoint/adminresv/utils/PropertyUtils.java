package com.telappoint.adminresv.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtils {
	private static final Log logger = LogFactory.getLog(PropertyUtils.class);

	private static Map<String, Properties> propsMap = new HashMap<String, Properties>();
	
	public static final String ADMIN_RESV_PROPFILE_FILE_PATH = "adminresv.properties";
	public static final String MAIL_PROPFILE = "mail.properties";
	
	public static InputStream getResourceAsStream(String fileName) throws Exception {
		InputStream propsIn = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if (propsIn == null) {
			propsIn = getResourceAsStream(fileName);
		}
		if (propsIn == null) {
			propsIn = ClassLoader.getSystemResourceAsStream(fileName);
		}

		if (propsIn == null) {
			logger.error(fileName + " not found");
			throw new Exception(fileName + " file is not found");
		}
		return propsIn;
	}
	
	public static Properties getProperties(String fileName) throws Exception {

		Properties properties = propsMap.get(fileName);
		if (properties != null) {
			return properties;
		}
		try {
			properties = new Properties();
			properties.load(getResourceAsStream(fileName));
			propsMap.put(fileName, properties);
		} catch (IOException e) {
			logger.error(e);
			throw new Exception(fileName + " file is not found");
		}

		return properties;
	}

	public static Properties refreshProperties(String fileName) throws Exception {
		Properties properties = new Properties();
		try {
			properties.load(getResourceAsStream(fileName));
			propsMap.put(fileName, null);
		} catch (IOException e) {
			logger.error(e);
			throw new Exception(fileName + " file is not found");
		}
		return properties;
	}

	public static String getValueFromProperties(String key, String fileName) throws Exception {
		Properties properties = getProperties(fileName);
		return (String) properties.get(key);
	}

	public static Properties getResvProperties() {
		Properties properties = new Properties();
		if (propsMap.get("adminResvProperties") == null) {
			try {
				properties.load(new FileInputStream(ADMIN_RESV_PROPFILE_FILE_PATH));
			} catch (Exception e) {
				logger.error("Error:" + e, e);
				try {
					properties = getProperties(ADMIN_RESV_PROPFILE_FILE_PATH);
				} catch (Exception e1) {
					logger.error("Error:" + e1, e1);
				}
			}
			propsMap.put("adminResvProperties", properties);
		} else {
			properties = propsMap.get("adminResvProperties");
		}
		return properties;
	}


	public static Properties getMailProperties() {
		Properties properties = new Properties();
		if (propsMap.get("mailProperties") == null) {
			try {
				properties.load(new FileInputStream(MAIL_PROPFILE));
			} catch (Exception e) {
				logger.error("Error:" + e, e);
				try {
					properties = getProperties(MAIL_PROPFILE);
				} catch (Exception e1) {
					logger.error("Error:" + e1, e1);
				}
			}
			propsMap.put("mailProperties", properties);
		} else {
			properties = propsMap.get("mailProperties");
		}
		return properties;
	}
	
	public static String getCommonRestWSEndPointURL() {
		Properties properties = PropertyUtils.getResvProperties();
		String commonRestWSEndPointURL = properties.getProperty("COMMON_RESTWS_ENDPOINT_URL");
		return commonRestWSEndPointURL;
	}

	public static String getRESVDESKRestWSEndPointURL() {
		Properties properties = PropertyUtils.getResvProperties();
		String notifyRestWSEndPointURL = properties.getProperty("RESVDESK_RESTWS_SERVICE_ENDPOINT_URL");
		return notifyRestWSEndPointURL;
	}
	
	public static String getRESVDESKRestWSOnlineEndPointURL() {
		Properties properties = PropertyUtils.getResvProperties();
		String notifyRestWSEndPointURL = properties.getProperty("ONLINE_RESVDESK_REST_SERVICE_URL_ENDPOINT");
		return notifyRestWSEndPointURL;
	}

	private static Properties getMailSettings(Properties mailProperties) {
		logger.debug("getMailSettings method entered.");
		String debug = mailProperties.getProperty("mail.smtp.debug");
		String userName = mailProperties.getProperty("mail.smtp.user");
		String port = mailProperties.getProperty("mail.smtp.port");
		String starttls = mailProperties.getProperty("mail.smtp.starttls.enable");
		String auth = mailProperties.getProperty("mail.smtp.auth");
		String socketFactoryClass = "javax.net.ssl.SSLSocketFactory";
		String fallback = "false";

		Properties props = new Properties();
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.host", "localhost");

		if (!"".equals(port)) {
			props.put("mail.smtp.port", port);
		}
		if (!"".equals(starttls)) {
			props.put("mail.smtp.starttls.enable", starttls);
		}
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.debug", debug);

		if (!"".equals(port)) {
			props.put("mail.smtp.socketFactory.port", port);
		}
		props.put("mail.smtp.socketFactory.class", socketFactoryClass);
		props.put("mail.smtp.socketFactory.fallback", fallback);
		logger.debug("getMailSettings method exit.");
		return props;
	}

	public static void sendErrorEmail(String subject, String body) {
		logger.debug("sendEmail method entered.");
		Properties mailProperties = PropertyUtils.getMailProperties();
		
		String sendMail = mailProperties.getProperty("error.mail.send");		
		String fromAddress = mailProperties.getProperty("mail.fromaddress");
		String toAddress = mailProperties.getProperty("error.mail.to");
		String ccAddress = mailProperties.getProperty("error.mail.cc");
		String replyAddress = mailProperties.getProperty("mail.replayaddress");
		
		if (sendMail != null && "yes".equalsIgnoreCase(sendMail)) {
			sendMail(subject, body, mailProperties, toAddress,fromAddress, ccAddress, replyAddress);
		}
	}
	
	public static void sendSchedulerDetailsChangedAlertMail(String subject, String body) {
		logger.debug("SchedulerDetailsChangedAlertMail method entered.");
		String toAddress = "support@itfrontdesk.com";		
		sendMail(subject, body,PropertyUtils.getMailProperties(),toAddress,"", "","");
	}

	private static void sendMail(String subject, String body,Properties mailProperties, 
			String toAddress,String fromAddress, String ccAddress, String replyAddress) {
		
		String userName = mailProperties.getProperty("mail.smtp.user");
		String passWord = mailProperties.getProperty("mail.smtp.password");
		String host = mailProperties.getProperty("mail.smtp.hostname");

		Properties props = getMailSettings(mailProperties);
		try {
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage msg = new MimeMessage(session);
			msg.setSubject(subject);
			if(fromAddress!=null && !"".equals(fromAddress)){
				msg.setFrom(new InternetAddress(fromAddress));
			}
			if(replyAddress!=null && !"".equals(replyAddress)){
				InternetAddress[] replyAddresses = new InternetAddress[1];
				replyAddresses[0] = new InternetAddress(replyAddress);
				msg.setReplyTo(replyAddresses);
			}
			// This part for CC Address Details
			if (ccAddress != null && !"".equals(ccAddress) && ccAddress.length() > 0) {
				InternetAddress[] inetAddress = getInetAddress(ccAddress);
				msg.setRecipients(Message.RecipientType.CC, inetAddress);
			}
			// This part for TO Address Details
			if (toAddress != null && !"".equals(toAddress) && toAddress.length() > 0) {
				InternetAddress[] inetAddress = getInetAddress(toAddress);
				msg.setRecipients(Message.RecipientType.TO, inetAddress);
			}

			msg.setContent(body, "text/html");
			msg.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, userName, passWord);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (AddressException ade) {
			logger.error("Error:" + ade, ade);
		} catch (MessagingException me) {
			logger.error("Error:" + me, me);
		}
	}

	public static InternetAddress[] getInetAddress(String address) throws AddressException {
		InternetAddress[] inetAddress = null;
		StringTokenizer tokens = new StringTokenizer(address, ",");
		if (tokens != null && tokens.hasMoreTokens()) {
			inetAddress = new InternetAddress[tokens.countTokens()];
			int i = 0;
			while (tokens.hasMoreTokens()) {
				String token = tokens.nextToken();
				inetAddress[i++] = new InternetAddress(token.trim());
			}
		}
		return inetAddress;
	}
}
