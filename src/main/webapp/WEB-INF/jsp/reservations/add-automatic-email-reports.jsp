<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<title> Automatic Email Reports</title>

<script type="text/javascript" src="static/js/validation/reservations/automaticEmailReports.js?version=1.0"></script>

	<form:form name="automaticEmailReports" id="automaticEmailReports"  commandName="resvReportConfig" action="save-automatic-email-reports.html" method="post">
		<h1>&nbsp; &nbsp;Automatic Email Reports</h1>
		<a href="#" class="jqmClose close" title="Close">Close</a>
		<div class="popup-content">
			<div class="popup-box">				
				<div class="required-indicator">
					<span class="required">*</span>indicates required field
				</div>
				<div class="error" id="reportNameError"></div>	
				<div class="error" id="email1Error"></div>	
				<div class="error" id="email2Error"></div>	
				<div class="error" id="email3Error"></div>	
				<dl> 		
					<dt><span class="required">*</span>Report Name:	</dt>
					<dd><form:input path="reportName" id="reportName" /></dd>
				</dl>
				<dl>
					<dt><span class="required">*</span>Email 1:</dt>
					<dd><form:input path="email1" id="email1" /></dd>
				</dl>
				<div class="clear-all"></div>
				<dl> 		
					<dt>	 Email 2:	</dt>
					<dd><form:input path="email2" id="email2" /></dd>
				</dl>
				<dl>
					<dt> Email 3:</dt>
					<dd><form:input path="email3" id="email3" /></dd>
				</dl>		 
				<dl class="clear-all">
					<dt>Location:</dt>
					<dd>
					  <form:select path="locationIds" id="locationId" class="leftFloat marginLTen paddingAll selectChangeClass" onchange="loadEvents();">
						<form:option value="-1" label="All Locations"/> 
						<c:if test="${locationListResponse.responseStatus && !locationListResponse.errorStatus}">
							<form:options items="${locationListResponse.locationList}" itemLabel="locationName" itemValue="locationId"></form:options>
						</c:if>
					  </form:select>
					</dd>
				</dl>
				<dl>
					<dt>Events:</dt>
					<dd class="noHeight" style="height:auto" id="eventIdDD">
					  <form:select path="eventIds" id="eventIds" class="leftFloat marginLTen paddingAll selectChangeClass">
						<form:option value="-1" label="All Events"/> 
						<c:if test="${eventListResponse.responseStatus && !eventListResponse.errorStatus}">
							<form:options items="${eventListResponse.eventList}" itemLabel="eventName" itemValue="eventId"></form:options>
						</c:if>
					  </form:select>
					</dd>
				</dl> 
				<dl class="clear-all">
					<dt>
						<span class="required">*</span>Reservation Status:
					</dt>
					<dd>					
						<!-- Please donot change check box values, If we want to change we need to updated in REST WS Query..!
							 If we want we can change labels 

							 Appointment Status values in Backed
							 -----------------------------------
							 No Show = 31  -- please dont chage this value we are using this value in backend as hard coded
							 CONFIRM = 11
							 CANCEL = 21
							 DISPLACED = 32
						-->
						<form:select path="otherResvStatusList"  multiple="true"  style='height: 100%;' 
							size="${fn:length(resvStatusList.resvStatusList) gt 8 ? (fn:length(resvStatusList.resvStatusList)/2) : 5}" >
							<form:options items="${resvStatusList.resvStatusList}" itemLabel="resvStatus" itemValue="statusVal" />
						</form:select>	
						<br/>
						<form:checkbox path="resvStatusFields" checked="${
						(resvReportConfig.selectedStatusFields == '' || resvReportConfig.selectedStatusFields == null) ==true ? 'checked' :
						fn:contains(resvReportConfig.selectedStatusFields,11)==true ? 'checked' : ''}" value="11" class="noWidth"/> Confirmed<br/>

						<form:checkbox path="resvStatusFields" checked="${
						(resvReportConfig.selectedStatusFields == '' || resvReportConfig.selectedStatusFields == null) ==true ? '' :
						fn:contains(resvReportConfig.selectedStatusFields,21)==true ? 'checked' : ''}" value="21" class="noWidth"/> Canceled<br/>

						<form:checkbox path="resvStatusFields" checked="${
						(resvReportConfig.selectedStatusFields == '' || resvReportConfig.selectedStatusFields == null) ==true ? '' :
						fn:contains(resvReportConfig.selectedStatusFields,24)==true ? 'checked' : ''}" value="24" class="noWidth"/> Displaced
					</dd>
				</dl>
				<div class="clear-all"></div>
				<span ><b style="color:red;">Note : </b><i>Multi Select Combo Box: Press & Hold CTRL key while clicking items</i></span>
				<div class="clear-all"></div>
			</div>
			<div class="popup-button">				
				<a href="#" class="btn jqmClose">Cancel</a>
				&nbsp; 
				<input name="btnSave" type="button" class="btn" value="Save" id="addAutomaticReportSaveBtn">
			</div>
			<!-- pop up ends -->
		</div>
	 </div>
  </form:form>