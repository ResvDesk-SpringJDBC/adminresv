/*
 * 
 */
package com.telappoint.adminresv.restws.model;


/**
 * @author Murali G
 *
 */
public class ErrorBean {
	
    private String appCode = "";
    private int errorCode = 0;
    private String displayText = "";
    private long errorID = 0;    
    private String clientCode = "";
    private String errorDescription = "";
    private String transId = "";
    private String page = "";
    
   
    public ErrorBean(String appCode, int errorCode, String displayText) {
        this.appCode = appCode;
        this.errorCode = errorCode;
        this.displayText = displayText;
    }
    
    public ErrorBean(String clientCode,String appCode,String transId,String page, int errorCode, String errorDescription) {
        this.clientCode = clientCode;
        this.appCode = appCode;
        this.transId = transId;
        this.page = page;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;       
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
    
    public int getErrorCode() {
        return this.errorCode;
    }

    public String getErrorCodeStr() {
        return String.valueOf(this.errorCode);
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getDisplayText() {
        return this.displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
   
    public long getErrorID() {
        return this.errorID;
    }

    public String getErrorIDStr() {
        return String.valueOf(this.errorID);
    }
  
    public void setErrorID(long errorID) {
        this.errorID = errorID;
    }


	public String getClientCode() {
		return clientCode;
	}


	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}


	public String getErrorDescription() {
		return errorDescription;
	}


	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}


	public String getTransId() {
		return transId;
	}


	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
    

}
