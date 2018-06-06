package com.telappoint.adminresv.restws.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.telappoint.adminresv.form.BaseRequest;

/**
 * 
 * @author Murali G
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Customer  extends BaseRequest {
	private Long customerId = (long)-1;
	private String accountNumber;
	private String firstName;
	private String lastName;
	private String middleName;
	private String contactPhone;
	private String homePhone;
	private String workPhone;
	private String cellPhone;
	private String email;
	private String attrib1;
	private String attrib2;
	private String attrib3;
	private String attrib4;
	private String attrib5;
	private String attrib6;
	private String attrib7;
	private String attrib8;
	private String attrib9;
	private String attrib10;
	private String attrib11;
	private String attrib12;
	private String attrib13;
	private String attrib14;
	private String attrib15;
	private String attrib16;
	private String attrib17;
	private String attrib18;
	private String attrib19;
	private String attrib20;
	
	private String dob;
	@JsonIgnore
	private String dob1;
	@JsonIgnore
	private String dob2;
	@JsonIgnore
	private String dob3;
	
	@JsonIgnore
	private String contactPhone1;
	@JsonIgnore
	private String contactPhone2;
	@JsonIgnore
	private String contactPhone3;
	
	@JsonIgnore
	private String homePhone1;
	@JsonIgnore
	private String homePhone2;
	@JsonIgnore
	private String homePhone3;
	
	@JsonIgnore
	private String workPhone1;
	@JsonIgnore
	private String workPhone2;
	@JsonIgnore
	private String workPhone3;

	@JsonIgnore
	private String cellPhone1;
	@JsonIgnore
	private String cellPhone2;
	@JsonIgnore
	private String cellPhone3;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getContactPhone() {
		return contactPhone1 == null ? contactPhone : contactPhone1+""+contactPhone2+""+contactPhone3;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getHomePhone() {
		return homePhone1 == null ? homePhone : homePhone1+""+homePhone2+""+homePhone3;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getWorkPhone() {
		return workPhone1 == null ? workPhone : workPhone1+""+workPhone2+""+workPhone3;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getCellPhone() {
		return cellPhone1 == null ? cellPhone : cellPhone1+""+cellPhone2+""+cellPhone3;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAttrib1() {
		return attrib1;
	}
	public void setAttrib1(String attrib1) {
		this.attrib1 = attrib1;
	}
	public String getAttrib2() {
		return attrib2;
	}
	public void setAttrib2(String attrib2) {
		this.attrib2 = attrib2;
	}
	public String getAttrib3() {
		return attrib3;
	}
	public void setAttrib3(String attrib3) {
		this.attrib3 = attrib3;
	}
	public String getAttrib4() {
		return attrib4;
	}
	public void setAttrib4(String attrib4) {
		this.attrib4 = attrib4;
	}
	public String getAttrib5() {
		return attrib5;
	}
	public void setAttrib5(String attrib5) {
		this.attrib5 = attrib5;
	}
	public String getAttrib6() {
		return attrib6;
	}
	public void setAttrib6(String attrib6) {
		this.attrib6 = attrib6;
	}
	public String getAttrib7() {
		return attrib7;
	}
	public void setAttrib7(String attrib7) {
		this.attrib7 = attrib7;
	}
	public String getAttrib8() {
		return attrib8;
	}
	public void setAttrib8(String attrib8) {
		this.attrib8 = attrib8;
	}
	public String getAttrib9() {
		return attrib9;
	}
	public void setAttrib9(String attrib9) {
		this.attrib9 = attrib9;
	}
	public String getAttrib10() {
		return attrib10;
	}
	public void setAttrib10(String attrib10) {
		this.attrib10 = attrib10;
	}
	public String getAttrib11() {
		return attrib11;
	}
	public void setAttrib11(String attrib11) {
		this.attrib11 = attrib11;
	}
	public String getAttrib12() {
		return attrib12;
	}
	public void setAttrib12(String attrib12) {
		this.attrib12 = attrib12;
	}
	public String getAttrib13() {
		return attrib13;
	}
	public void setAttrib13(String attrib13) {
		this.attrib13 = attrib13;
	}
	public String getAttrib14() {
		return attrib14;
	}
	public void setAttrib14(String attrib14) {
		this.attrib14 = attrib14;
	}
	public String getAttrib15() {
		return attrib15;
	}
	public void setAttrib15(String attrib15) {
		this.attrib15 = attrib15;
	}
	public String getAttrib16() {
		return attrib16;
	}
	public void setAttrib16(String attrib16) {
		this.attrib16 = attrib16;
	}
	public String getAttrib17() {
		return attrib17;
	}
	public void setAttrib17(String attrib17) {
		this.attrib17 = attrib17;
	}
	public String getAttrib18() {
		return attrib18;
	}
	public void setAttrib18(String attrib18) {
		this.attrib18 = attrib18;
	}
	public String getAttrib19() {
		return attrib19;
	}
	public void setAttrib19(String attrib19) {
		this.attrib19 = attrib19;
	}
	public String getAttrib20() {
		return attrib20;
	}
	public void setAttrib20(String attrib20) {
		this.attrib20 = attrib20;
	}
	public String getContactPhone1() {
		this.contactPhone1 = getPhoneSubPart(contactPhone,0, 3);
		return this.contactPhone1;
	}
	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}
	public String getContactPhone2() {
		this.contactPhone2 = getPhoneSubPart(contactPhone,3, 6);
		return this.contactPhone2;
	}
	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}
	public String getContactPhone3() {
		this.contactPhone3 = getPhoneSubPart(contactPhone,6, -1);
		return this.contactPhone3;
	}
	public void setContactPhone3(String contactPhone3) {
		this.contactPhone3 = contactPhone3;
	}
	public String getHomePhone1() {
		this.homePhone1 = getPhoneSubPart(homePhone,0,3);
		return this.homePhone1;
	}
	public void setHomePhone1(String homePhone1) {
		this.homePhone1 = homePhone1;
	}
	public String getHomePhone2() {
		this.homePhone2 = getPhoneSubPart(homePhone,3,6);
		return this.homePhone2;
	}
	public void setHomePhone2(String homePhone2) {
		this.homePhone2 = homePhone2;
	}
	public String getHomePhone3() {
		this.homePhone3 = getPhoneSubPart(homePhone,6,-1);
		return this.homePhone3;
	}
	public void setHomePhone3(String homePhone3) {
		this.homePhone3 = homePhone3;
	}
	public String getWorkPhone1() {
		this.workPhone1 = getPhoneSubPart(workPhone,0,3);
		return this.workPhone1;
	}
	public void setWorkPhone1(String workPhone1) {
		this.workPhone1 = workPhone1;
	}
	public String getWorkPhone2() {
		this.workPhone2 = getPhoneSubPart(workPhone,3,6);
		return this.workPhone2;
	}
	public void setWorkPhone2(String workPhone2) {
		this.workPhone2 = workPhone2;
	}
	public String getWorkPhone3() {
		this.workPhone3 = getPhoneSubPart(workPhone,6,-1);
		return this.workPhone3;
	}
	public void setWorkPhone3(String workPhone3) {
		this.workPhone3 = workPhone3;
	}
	public String getCellPhone1() {
		this.cellPhone1 = getPhoneSubPart(cellPhone,0,3);
		return this.cellPhone1;
	}
	public void setCellPhone1(String cellPhone1) {
		this.cellPhone1 = cellPhone1;
	}
	public String getCellPhone2() {
		this.cellPhone2 = getPhoneSubPart(cellPhone,3,6);
		return this.cellPhone2;
	}
	public void setCellPhone2(String cellPhone2) {
		this.cellPhone2 = cellPhone2;
	}
	public String getCellPhone3() {
		this.cellPhone3 = getPhoneSubPart(cellPhone,6,-1);
		return this.cellPhone3;
	}
	public void setCellPhone3(String cellPhone3) {
		this.cellPhone3 = cellPhone3;
	}
	
	private String getPhoneSubPart(String phone, int beginIndex, int endIndex) {
		try{
			if(phone!=null && !"".equals(phone)){
				if(phone.contains("-")){
					phone = phone.replaceAll("-", "");
				}
				if(phone.contains("/")){
					phone = phone.replaceAll("/", "");
				}
				if(endIndex==-1){
					endIndex =  phone.length();
				}
				if(phone!=null && !"".equals(phone) && !"--".equals(phone) && phone.length()>=(endIndex)){
					return phone.substring(beginIndex,endIndex);
				}	
			}
		}catch(Exception e){
		}
		return "";
	}
	public String getDob() { //MM=dob1,DD=dob2,YYYY=dob3
		return (dob1 == null || "".equals(dob1)) ? dob : dob1+"/"+dob2+"/"+dob3;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getDob1() {
		this.dob1 = (dob1 != null && !"".equals(dob1))  ? dob1 : getPhoneSubPart(dob,0,2);
		return dob1;
	}
	public void setDob1(String dob1) {
		this.dob1 = dob1;
	}
	public String getDob2() {
		this.dob2 = (dob2 != null && !"".equals(dob2))  ? dob2 : getPhoneSubPart(dob,2,4);
		return dob2;
	}
	public void setDob2(String dob2) {
		this.dob2 = dob2;
	}
	public String getDob3() {
		this.dob3 = (dob3 != null && !"".equals(dob3))  ? dob3 : getPhoneSubPart(dob,4,-1);
		return dob3;
	}
	public void setDob3(String dob3) {
		this.dob3 = dob3;
	}
}
