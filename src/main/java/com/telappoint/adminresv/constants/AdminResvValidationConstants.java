package com.telappoint.adminresv.constants;

/**
 * @author Murali
 */
public enum AdminResvValidationConstants {
	/*
	 * Possible validation rules are:
		alpha - letters
		digits - numbers
		comma - ,
		space - space
		single-quote - '
		hypen - -
		period - .
		ampersand - &
		percentage - %
		pound - #
		at - @
		apostrophe - !
		dollar - $
		star - *
	 */
	ALPHA("a-zA-Z"), 
	DIGITS("0-9"),
	COMMA("\\,"),
	SPACE("\\s+"),
	//SINGLE_QUOTE("\\'"),
	HYPHEN("\\-"),
	PERIOD("\\."),
	AMPERSAND("\\&"),
	PERCENTAGE("\\%"),
	POUND("\\#"),
	AT("\\@"),
	APOSTROPHE("\\!"),
	DOLLER("\\$"),
	STAR("\\*"),
	//QUOTE("\\'"), 
	
	NUMERIC("[0-9]+"),
	PHONE("[0-9]{10}+"), 	
	EMAIL("[a-z0-9\\-_\\.]++@[a-z0-9\\-]++(\\.[a-z0-9\\-]++)++");

	private String validateExp;

	AdminResvValidationConstants(String validateExp) {
		this.validateExp = validateExp;
	}

	public String getValidateExp() {
		return validateExp;
	}
}
