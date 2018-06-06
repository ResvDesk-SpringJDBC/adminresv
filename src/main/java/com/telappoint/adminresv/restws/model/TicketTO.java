package com.telappoint.adminresv.restws.model;

import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Murali
 *
 */

@JsonSerialize(include = Inclusion.NON_NULL)
public class TicketTO {

	private Integer id;
	private Timestamp timestamp;
	private String subject;
	private Timestamp date_opened;
	private Timestamp date_updated;
	private String client_code;
	private String client_name;
	private String username;
	private String name;
	private int priority;	
	private int type_id;
	private int status;
	private String contact_phone;
	private String contact_email;
	private char escalate_request='N';
	private String comment;
	private String itfd_response_1;
	private char client_response_1_enable='N';
	private String client_response_1 ;
	private String itfd_response_2;
	private char client_response_2_enable='N';
	private String client_response_2 ;
	private String itfd_response_3;
	private char client_response_3_enable='N';
	private String client_response_3;
	private String itfd_response_4;
	private char client_response_4_enable='N';
	private String client_response_4 ;
	private String itfd_response_5;
	private char client_response_5_enable='N';
	private String client_response_5 ;
	private String file_attachment_1 ;
	private String file_attachment_2 ;
	private String file_attachment_3;
	private String file_attachment_4;
	private String file_attachment_5;
	
	private String contact_phone_1;
	private String contact_phone_2;
	private String contact_phone_3;
	
	private String timestamp_str;
	private String date_opened_str;
	private String date_updated_str;
	
	private String priority_str;	
	private String type_str;
	private String status_str;
	
	@JsonIgnore
	private CommonsMultipartFile error_file_1;
	@JsonIgnore
	private CommonsMultipartFile error_file_2;
	@JsonIgnore
	private CommonsMultipartFile error_file_3;
	@JsonIgnore
	private CommonsMultipartFile error_file_4;
	@JsonIgnore
	private CommonsMultipartFile error_file_5;
	
	private String displayable_client_response ;
	
	@JsonIgnore
	private boolean escalateRequest= false;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getEscalateRequest() {
		if(escalate_request=='Y'){
			escalateRequest= true;
		}else {
			escalateRequest= false;
		}
		return escalateRequest;
	}

	public void setEscalateRequest(boolean escalateRequest) {
		this.escalateRequest = escalateRequest;
	}

	public String getDisplayable_client_response() {
		return displayable_client_response;
	}

	public void setDisplayable_client_response(String displayable_client_response) {
		this.displayable_client_response = displayable_client_response;
	}

	public CommonsMultipartFile getError_file_1() {
		return error_file_1;
	}

	public void setError_file_1(CommonsMultipartFile error_file_1) {
		this.error_file_1 = error_file_1;
	}

	public CommonsMultipartFile getError_file_2() {
		return error_file_2;
	}

	public void setError_file_2(CommonsMultipartFile error_file_2) {
		this.error_file_2 = error_file_2;
	}

	public CommonsMultipartFile getError_file_3() {
		return error_file_3;
	}

	public void setError_file_3(CommonsMultipartFile error_file_3) {
		this.error_file_3 = error_file_3;
	}

	public CommonsMultipartFile getError_file_4() {
		return error_file_4;
	}

	public void setError_file_4(CommonsMultipartFile error_file_4) {
		this.error_file_4 = error_file_4;
	}

	public CommonsMultipartFile getError_file_5() {
		return error_file_5;
	}

	public void setError_file_5(CommonsMultipartFile error_file_5) {
		this.error_file_5 = error_file_5;
	}

	public String getPriority_str() {
		return priority_str;
	}

	public void setPriority_str(String priority_str) {
		this.priority_str = priority_str;
	}

	public String getType_str() {
		return type_str;
	}

	public void setType_str(String type_str) {
		this.type_str = type_str;
	}

	public String getStatus_str() {
		return status_str;
	}

	public void setStatus_str(String status_str) {
		this.status_str = status_str;
	}

	public String getTimestamp_str() {
		return timestamp_str;
	}

	public void setTimestamp_str(String timestamp_str) {
		this.timestamp_str = timestamp_str;
	}

	public String getDate_opened_str() {
		return date_opened_str;
	}

	public void setDate_opened_str(String date_opened_str) {
		this.date_opened_str = date_opened_str;
	}

	public String getDate_updated_str() {
		return date_updated_str;
	}

	public void setDate_updated_str(String date_updated_str) {
		this.date_updated_str = date_updated_str;
	}

	public String getContact_phone_1() {
		return contact_phone_1;
	}

	public void setContact_phone_1(String contact_phone_1) {
		this.contact_phone_1 = contact_phone_1;
	}

	public String getContact_phone_2() {
		return contact_phone_2;
	}

	public void setContact_phone_2(String contact_phone_2) {
		this.contact_phone_2 = contact_phone_2;
	}

	public String getContact_phone_3() {
		return contact_phone_3;
	}

	public void setContact_phone_3(String contact_phone_3) {
		this.contact_phone_3 = contact_phone_3;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Timestamp getDate_opened() {
		return date_opened;
	}

	public void setDate_opened(Timestamp date_opened) {
		this.date_opened = date_opened;
	}

	public Timestamp getDate_updated() {
		return date_updated;
	}

	public void setDate_updated(Timestamp date_updated) {
		this.date_updated = date_updated;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public char getEscalate_request() {
		if(escalateRequest){
			escalate_request = 'Y';
		}else{
			escalate_request = 'N';
		}
		return escalate_request;
	}

	public void setEscalate_request(char escalate_request) {
		this.escalate_request = escalate_request;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getItfd_response_1() {
		return itfd_response_1;
	}

	public void setItfd_response_1(String itfd_response_1) {
		this.itfd_response_1 = itfd_response_1;
	}

	public char getClient_response_1_enable() {
		return client_response_1_enable;
	}

	public void setClient_response_1_enable(char client_response_1_enable) {
		this.client_response_1_enable = client_response_1_enable;
	}

	public String getClient_response_1() {
		return client_response_1;
	}

	public void setClient_response_1(String client_response_1) {
		this.client_response_1 = client_response_1;
	}

	public String getItfd_response_2() {
		return itfd_response_2;
	}

	public void setItfd_response_2(String itfd_response_2) {
		this.itfd_response_2 = itfd_response_2;
	}

	public char getClient_response_2_enable() {
		return client_response_2_enable;
	}

	public void setClient_response_2_enable(char client_response_2_enable) {
		this.client_response_2_enable = client_response_2_enable;
	}

	public String getClient_response_2() {
		return client_response_2;
	}

	public void setClient_response_2(String client_response_2) {
		this.client_response_2 = client_response_2;
	}

	public String getItfd_response_3() {
		return itfd_response_3;
	}

	public void setItfd_response_3(String itfd_response_3) {
		this.itfd_response_3 = itfd_response_3;
	}

	public char getClient_response_3_enable() {
		return client_response_3_enable;
	}

	public void setClient_response_3_enable(char client_response_3_enable) {
		this.client_response_3_enable = client_response_3_enable;
	}

	public String getClient_response_3() {
		return client_response_3;
	}

	public void setClient_response_3(String client_response_3) {
		this.client_response_3 = client_response_3;
	}

	public String getItfd_response_4() {
		return itfd_response_4;
	}

	public void setItfd_response_4(String itfd_response_4) {
		this.itfd_response_4 = itfd_response_4;
	}

	public char getClient_response_4_enable() {
		return client_response_4_enable;
	}

	public void setClient_response_4_enable(char client_response_4_enable) {
		this.client_response_4_enable = client_response_4_enable;
	}

	public String getClient_response_4() {
		return client_response_4;
	}

	public void setClient_response_4(String client_response_4) {
		this.client_response_4 = client_response_4;
	}

	public String getItfd_response_5() {
		return itfd_response_5;
	}

	public void setItfd_response_5(String itfd_response_5) {
		this.itfd_response_5 = itfd_response_5;
	}

	public char getClient_response_5_enable() {
		return client_response_5_enable;
	}

	public void setClient_response_5_enable(char client_response_5_enable) {
		this.client_response_5_enable = client_response_5_enable;
	}

	public String getClient_response_5() {
		return client_response_5;
	}

	public void setClient_response_5(String client_response_5) {
		this.client_response_5 = client_response_5;
	}

	public String getFile_attachment_1() {
		return file_attachment_1;
	}

	public void setFile_attachment_1(String file_attachment_1) {
		this.file_attachment_1 = file_attachment_1;
	}

	public String getFile_attachment_2() {
		return file_attachment_2;
	}

	public void setFile_attachment_2(String file_attachment_2) {
		this.file_attachment_2 = file_attachment_2;
	}

	public String getFile_attachment_3() {
		return file_attachment_3;
	}

	public void setFile_attachment_3(String file_attachment_3) {
		this.file_attachment_3 = file_attachment_3;
	}

	public String getFile_attachment_4() {
		return file_attachment_4;
	}

	public void setFile_attachment_4(String file_attachment_4) {
		this.file_attachment_4 = file_attachment_4;
	}

	public String getFile_attachment_5() {
		return file_attachment_5;
	}

	public void setFile_attachment_5(String file_attachment_5) {
		this.file_attachment_5 = file_attachment_5;
	}

	public String toString() {
		return toString(true);
	}

	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getId());
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("Ticket[ ");
			sb.append("Id=").append(getId()).append(" ");
			sb.append("Timestamp=").append(getTimestamp()).append(" ");
			sb.append("Status=").append(getStatus()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}

}
