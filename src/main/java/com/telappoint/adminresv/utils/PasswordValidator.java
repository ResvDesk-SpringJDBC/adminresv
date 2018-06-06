package com.telappoint.adminresv.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

	  private static Pattern pattern;
	  private static Matcher matcher;
	  private static String min = "6";
	  private static String max = "25";
	  
	  private static final String PASSWORD_PATTERN_START = "^([";
	  private static final String PASSWORD_PATTERN_END = "]{MINIMUM_NUMBER,MAXIMUM_NUMBER})$";
	  
	  private static final String DIGITS_CHARACTERS_PATTERN = "((?=.*[0-9]{ATLEAST_NUMBER,}).+)";
	  private static final String LOWER_CASE_CHARACTERS_PATTERN = "((?=.*[a-z]{ATLEAST_NUMBER,}).+)";
	  private static final String UPPER_CASE_CHARACTERS_PATTERN = "((?=.*[A-Z]{ATLEAST_NUMBER,}).+)";
	  private static final String LETTERS_PATTERN = "((?=.*[a-zA-Z]{ATLEAST_NUMBER,}).+)";
	  private static final String SPECIAL_CHARACTERS_PATTERN =  "((?=.*[@#$%-_]{ATLEAST_NUMBER,}).+)";
	  
	  private static final String DIGITS_CHARACTERS = "0-9";
	  private static final String UPPER_CASE_CHARACTERS = "A-Z";
	  private static final String LOWER_CASE_CHARACTERS = "a-z";
	  private static final String SPECIAL_CHARACTERS =  "@#$%-_";
	  private static final String LETTERS =  "a-zA-Z";
	  
	  private static final String PASSWORD_MINIMUM_CHARACTERS_REQUIRED = "min";
	  private static final String PASSWORD_MAXIMUM_CHARACTERS_REQUIRED = "max";
	  
	  private static final String PASSWORD_DIGIT_CHARACTERS_REQUIRED =  "digit";
	  private static final String PASSWORD_UPPER_CHARACTERS_REQUIRED = "upper";
	  private static final String PASSWORD_LOWER_CHARACTERS_REQUIRED =  "lower";
	  private static final String PASSWORD_LETTER_CHARACTERS_REQUIRED =  "letter";
	  private static final String PASSWORD_SPECIAL_CHARACTERS_REQUIRED =  "splchar";
	  private static final String PASSWORD_NO_USER_NAME =  "no_username";
	  
	  public static String validatePassword(String passwordComplexityLogic,final String username,final String password){
		  String response = " Password Should contain atleast ";
		  String PASSWORD_PATTERN = PASSWORD_PATTERN_START;
		  boolean valid = true;		  
		  if(passwordComplexityLogic!=null && passwordComplexityLogic!=""){
			  String[] passwordComplexityArray = passwordComplexityLogic.split(",");
			  //pattern = Pattern.compile("/^[0-9a-zA-Z]+$/");
			  String regExStr = "";
			  for(String passwordComplexity : passwordComplexityArray){
				  String numStr =  extractNumber(passwordComplexity);
				  if(numStr!=null && numStr!=""){
					  String rule  = passwordComplexity.substring(numStr.toString().length());
					  if(Pattern.compile("[0-9]+").matcher(numStr).matches()){
						  if(PASSWORD_DIGIT_CHARACTERS_REQUIRED.equalsIgnoreCase(rule)){
							  regExStr = DIGITS_CHARACTERS_PATTERN;
							  PASSWORD_PATTERN = PASSWORD_PATTERN+DIGITS_CHARACTERS;
						  }else if(PASSWORD_UPPER_CHARACTERS_REQUIRED.equalsIgnoreCase(rule)){
							  regExStr = UPPER_CASE_CHARACTERS_PATTERN;
							  PASSWORD_PATTERN = PASSWORD_PATTERN+UPPER_CASE_CHARACTERS;
						  }else if(PASSWORD_LOWER_CHARACTERS_REQUIRED.equalsIgnoreCase(rule)){
							  regExStr = LOWER_CASE_CHARACTERS_PATTERN;
							  PASSWORD_PATTERN = PASSWORD_PATTERN+LOWER_CASE_CHARACTERS;
						  }else if(PASSWORD_LETTER_CHARACTERS_REQUIRED.equalsIgnoreCase(rule)){
							  regExStr = LETTERS_PATTERN;
							  PASSWORD_PATTERN = PASSWORD_PATTERN+LETTERS;
						  }else if(PASSWORD_SPECIAL_CHARACTERS_REQUIRED.equalsIgnoreCase(rule)){
							  regExStr = SPECIAL_CHARACTERS_PATTERN ;//.replaceAll("SPECIAL_CHARS", SPECIAL_CHARACTERS);
							  PASSWORD_PATTERN = PASSWORD_PATTERN+SPECIAL_CHARACTERS;
						  }else if(PASSWORD_MINIMUM_CHARACTERS_REQUIRED.equalsIgnoreCase(rule)){
							  min = numStr;
						  }else if(PASSWORD_MAXIMUM_CHARACTERS_REQUIRED.equalsIgnoreCase(rule)){
							  max = numStr;
						  }
						  if(regExStr!=null && regExStr!=""){
							  valid = regExValidation(password, regExStr,numStr,"","");
							  regExStr = "";
							  if(!valid){
								  response = response+numStr+" "+rule+" ";
							  }
						  }
					  }
					  if(PASSWORD_NO_USER_NAME.equalsIgnoreCase(passwordComplexity)){
						  if(username!=null && password!=null && username.equalsIgnoreCase(password)){
							  valid = false;
							  response = " password should not equal to username ";
						  }
					  }
				  }else{
					  if(PASSWORD_NO_USER_NAME.equalsIgnoreCase(passwordComplexity)){
						  if(username!=null && password!=null && username.equalsIgnoreCase(password)){
							  valid = false;
							  response = " password should not equal to username ";
						  }
					  }
				  }
			  }
			  if(valid && " Password Should contain atleast ".equalsIgnoreCase(response)){
				  PASSWORD_PATTERN = PASSWORD_PATTERN+PASSWORD_PATTERN_END;
				  valid = regExValidation(password, PASSWORD_PATTERN,"",min,max);
				  if(!valid){
					  response = " Password Should contain atleast "+passwordComplexityLogic;
				  }
			  }
		  }
		  if(" Password Should contain atleast ".equalsIgnoreCase(response)){
			  response = "";
		  }
		  return response ;

	  }

	 private static boolean regExValidation(final String password, String regExStr,String atleast,String min,String max ) {
		regExStr = regExStr.replaceAll("ATLEAST_NUMBER", atleast);
		regExStr = regExStr.replaceAll("MINIMUM_NUMBER", min);
		regExStr = regExStr.replaceAll("MAXIMUM_NUMBER", max);
		//System.out.println("regExStr  -----> "+regExStr);
		pattern = Pattern.compile(regExStr);		  
		matcher = pattern.matcher(password);
		return matcher.matches();
	 }
	  
	  private static String extractNumber(String data){
		  	StringBuilder myNumbers = new StringBuilder();
		    for (int i = 0; i < data.length(); i++) {
		        if (Character.isDigit(data.charAt(i))) {
		            myNumbers.append(data.charAt(i));
		        } else {
		           break;
		        }
		    } 
		   return myNumbers.toString();
	  }
	  
	  /*private static List<String> extractData(String data) {
	     List<String> output = new ArrayList<String>();
	     Matcher match = Pattern.compile("[0-9]+|[a-z]+|[A-Z]+").matcher("digits10ASD123Erew");
	     while (match.find()) {
	         output.add(match.group());
	     }
		 return output;		 
	  }*/
	  
	    public static void main(String[] args) {
		    String passwordComplexityLogic = "6min,1upper,1lower,1digit,1letter,3splchar,no_username";
		    String response = validatePassword(passwordComplexityLogic,"G124sdds@$G","G124sASDdds@$G");
			System.out.println("Response  ------> "+response);			  
		  }
	  
}
