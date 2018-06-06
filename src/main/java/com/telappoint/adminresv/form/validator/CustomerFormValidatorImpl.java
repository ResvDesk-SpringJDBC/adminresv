package com.telappoint.adminresv.form.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.telappoint.adminresv.constants.AdminResvValidationConstants;
import com.telappoint.adminresv.restws.model.Customer;
import com.telappoint.adminresv.restws.model.LoginField;
import com.telappoint.adminresv.utils.AdminUtils;

/** 
 * @author Murali
 */

@Service
public class CustomerFormValidatorImpl  {
	private Logger logger = Logger.getLogger(CustomerFormValidatorImpl.class);

	public boolean supports(Class<?> clazz) {
		return Customer.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		// LoginForm loginForm = (LoginForm) target;
	}

		
	private boolean check(String fieldValue, String reg, String fieldName, String message, StringBuilder validationResult) {
		fieldValue = fieldValue.replaceAll(AdminResvValidationConstants.HYPHEN.getValidateExp(), "");
		
		if(!fieldValue.matches(reg)) {
			validationResult.append(message);
			return false;
		}else{
			return true;
		}
	}
	
	
	
	/*Method to validate Registration Form*/
	public String validate(Object target,List<LoginField> loginFieldList) {
		StringBuilder validationResponse = new StringBuilder();
		Customer customer = (Customer) target;
		String fieldValueStr = null;
		String maxChars = null;
		String minChars = null;
		if (null==loginFieldList) return "Error while validating Reservation Form!";
		try {
			/*Iterating through loginField list*/
			for (LoginField loginField : loginFieldList) {
				maxChars = loginField.getValidateMaxChars();
				minChars = loginField.getValidateMinValue();
				String displayType = loginField.getDisplayType();
				String emptyErrorMsg = loginField.getEmptyErrorMessage();
				String invalidErrorMsg = loginField.getInvalidErrorMessage();
				//String fieldName = loginField.getFieldName(); //represents DB field name
				String fieldName = loginField.getJavaRef(); //represents java field name
				
				/*If display type not a button then only it will processes*/
				if (displayType != null && !displayType.contains("button")) {
					Object fieldValue = AdminUtils.getPropertyValue(customer, fieldName);
					
					if(loginField.getValidateRules().contains("phone")
							|| loginField.getValidateRules().contains("dob")) {
						String fieldVal = "";
						Object fieldVal1 = AdminUtils.getPropertyValue(customer, fieldName+"1");
						Object fieldVal2 = AdminUtils.getPropertyValue(customer, fieldName+"2");
						Object fieldVal3 = AdminUtils.getPropertyValue(customer, fieldName+"3");
						if(null!=fieldVal1){
							
							fieldVal = fieldVal + (String)fieldVal1;
						}
						if(null!=fieldVal2){
							fieldVal = fieldVal + (String)fieldVal2;
						}
						if(null!=fieldVal3){
							fieldVal = fieldVal + (String)fieldVal3;
						}
						if(!"".equals(fieldVal)){
							fieldValue = fieldVal;	
						}						
					}
					
					if(fieldValue!=null) {
						String validateFieldValue = "";
						//-1 represents select 
						if(!"-1".equals(fieldValue)){
							validateFieldValue = fieldValue.toString().replaceAll(AdminResvValidationConstants.HYPHEN.getValidateExp(), "");
						}else{
							validateFieldValue = fieldValue.toString();
						}
						
						if("Y".equalsIgnoreCase(loginField.getValidationRequired())){
							if (fieldValue instanceof String) {
								fieldValueStr = (String) fieldValue;
							}
							if(!"".equals(validateFieldValue)){
								validateNotEmptyField(validationResponse,fieldValueStr, maxChars, minChars,
										loginField, displayType,invalidErrorMsg, fieldName, fieldValue);
							
							}else{
								if (fieldValueStr == null || "".equals(fieldValueStr.trim())) {
									validationResponse.append(emptyErrorMsg);
									validationResponse.append("<br/>");
								}
							}
						}else{
							if(!"".equals(validateFieldValue)){
								validateNotEmptyField(validationResponse,fieldValueStr, maxChars, minChars,
										loginField, displayType,invalidErrorMsg, fieldName, fieldValue);
							}
						}						
					}else{
						//if(displayType.contains("radio")){	
							if("Y".equalsIgnoreCase(loginField.getValidationRequired())) {
								if (fieldValue == null || "".equals(fieldValue.toString().trim())) {
									validationResponse.append(emptyErrorMsg);
									validationResponse.append("<br/>");
								}
							}
						//}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error:" + e, e);
		}
		return validationResponse.toString();
	}

	private void validateNotEmptyField(StringBuilder validationResponse,String fieldValueStr, String maxChars, String minChars,
			LoginField loginField, String displayType,String invalidErrorMsg, String fieldName, Object fieldValue) {
		if(displayType.contains("select")){
			if ("-1".equals(fieldValueStr.trim())) {
				validationResponse.append(invalidErrorMsg);
				validationResponse.append("<br/>");
			}
		}else {		
			
			if ("Y".equalsIgnoreCase(loginField.getValidationRequired())) {
				
				if (displayType != null && displayType.contains("textbox-")) {
					if(loginField.getValidateRules().contains("phone")) {
						check(fieldValue.toString(), AdminResvValidationConstants.PHONE.getValidateExp(), fieldName, invalidErrorMsg, validationResponse);
					} else if ( minChars != null && fieldValueStr.length() < Integer.parseInt(minChars) ) {
						validationResponse.append(invalidErrorMsg);
						validationResponse.append("<br/>");
					} else if(loginField.getValidateRules().contains("numeric")) {
						check(fieldValue.toString(), AdminResvValidationConstants.NUMERIC.getValidateExp(), fieldName, invalidErrorMsg, validationResponse);
					}else if(loginField.getValidateRules().contains("dob")) {
						boolean isValid = check(fieldValue.toString(), AdminResvValidationConstants.NUMERIC.getValidateExp(), fieldName, invalidErrorMsg, validationResponse);
						if(isValid) {
							validateDOBDate(fieldValue.toString(), fieldName, invalidErrorMsg, validationResponse,true);
						}
					}else if(loginField.getValidateRules().contains("date")) {
						boolean isValid = check(fieldValue.toString(), AdminResvValidationConstants.NUMERIC.getValidateExp(), fieldName, invalidErrorMsg, validationResponse);
						if(isValid) {
							validateDOBDate(fieldValue.toString(), fieldName, invalidErrorMsg, validationResponse,false);
						}
					}
				} else if(!(fieldName.equals("date") || fieldName.equals("Date&Time"))) {										
					if ( minChars != null && fieldValueStr.length() < Integer.parseInt(minChars) ) {
						validationResponse.append(invalidErrorMsg);
						validationResponse.append("<br/>");
					}
					else if(maxChars != null && fieldValueStr.length() > Integer.parseInt(maxChars)) {
						validationResponse.append(invalidErrorMsg);
						validationResponse.append("<br/>");
					} else {
						boolean isValid = FormValidationUtils.validateFieldValue(loginField.getValidateRules(),fieldValue.toString());
						if(!isValid) {
							validationResponse.append(invalidErrorMsg);
							validationResponse.append("<br/>");
						}
					}
				}
			}
		}
	}
	
	private void validateDOBDate(String fieldValue, String fieldName, String message,StringBuilder validationResponse,boolean compareWithToday) {
		if(fieldValue!=null && fieldValue.length()==8) { //considering 8 characters
			String datePattern = "[0-9]{8}+";
		    boolean isValid = fieldValue.matches(datePattern);
		    if(isValid){	
		    	isValid = isValidDateAsRawData(fieldValue);
		    	if(isValid){
					String MMDDYYYY_DATE_FORMAT = "MMddyyyy";
					Date dob = null;
					try {
						DateFormat dateFormat = new SimpleDateFormat(MMDDYYYY_DATE_FORMAT);
						dob = dateFormat.parse(fieldValue);
						
						Date today = new Date();
						if(today.after(dob)){
							//nothing to do
						}else{
							validationResponse.append(message);
							validationResponse.append("<br/>");
						}				
					} catch (Exception e) {
						validationResponse.append(message);
						validationResponse.append("<br/>");
					}
		    	}else{
					validationResponse.append(message);
					validationResponse.append("<br/>");
				}
		    }else{
				validationResponse.append(message);
				validationResponse.append("<br/>");
			}
		}else{
			validationResponse.append(message);
			validationResponse.append("<br/>");
		}
	}
	
	private static boolean isValidDateAsRawData(String date) {		
		int month = Integer.parseInt(date.substring(0, 2));
		int day = Integer.parseInt(date.substring(2, 4));
		int year = Integer.parseInt(date.substring(4));
		if(month<=12){
		    switch (month) {
		        case 1: // fall through
		        case 3: // fall through
		        case 5: // fall through
		        case 7: // fall through
		        case 8: // fall through
		        case 10: // fall through
		        case 12:
		        	if(day<=31){
		            	return true;
		            }
		            break;
		        case 2:
		            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
		            	if(day<=29){
			            	return true;
			            }
		            } else {
		            	if(day<=28){
			            	return true;
			            }
		            }
		            break;
		        default:
		            if(day<=30){
		            	return true;
		            }
		    }
		}
	    return false;
	}

}
