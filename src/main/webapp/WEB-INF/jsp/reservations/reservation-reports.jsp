<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<script type="text/javascript" src="static/js/validation/reservations/reservationReportsFormValidation.js?version=1.4"></script>

<title>Reservations > Reservation Reports</title>

<!-- Body starts --> 
    <h1>Reservations > Reservation Reports</h1>
    <div>
     
	<div class="error" id="startDateError"></div>
	<div class="error" id="endDateError"></div>
	<div class="error" id="locationIdError"></div>
	<div class="error" id="eventIdError"></div>
	<div class="error" id="eventDateTimeIdsError"></div>
	<div class="error" id="resvStatusError"></div>

    <!--Search bar starts -->
    <div class="search-bar">
	   <form:form action="search-reservation-reports.html" method="POST" id="resReportRequestForm" modelAttribute="resReportRequest"
		  onsubmit="return validateReservationReportsForm()">
	   <div>
		  <dl>
			 <dt> <span class="required">*</span>Reservation From Date: </dt>
			 <dd>
				<form:input path="startDate" id="startDate"/>
			 </dd>
		  </dl>
		  <dl>
			 <dt> <span class="required">*</span>To Date: </dt>
			 <dd>
				<form:input path="endDate" id="endDate"/>
			 </dd>
		  </dl>
		  <dl class="clear-all">
			 <dt>Location:</dt>
			 <dd>
				<form:select path="locationId" id="locationId" class="leftFloat marginLTen paddingAll selectChangeClass" onchange="loadEvents();">
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
				<form:select path="eventId" id="eventId" class="leftFloat marginLTen paddingAll selectChangeClass"  onchange="loadEventDateTimes(false);">
				   <form:option value="-2" label="Select Event"/>
				   <form:option value="-1" label="All Events"/>
				   <c:if test="${eventListResponse.responseStatus && !eventListResponse.errorStatus}">
					  <form:options items="${eventListResponse.eventList}" itemLabel="eventName" itemValue="eventId"></form:options>
				   </c:if>
				</form:select>
			 </dd>
		  </dl>
		  <div class="clear-all">			 
			 <div class="noHeight" style="height:auto" id="eventDateTimeDD">	
			 &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; Event Date Times:&nbsp;&nbsp;&nbsp;
				<c:if test="${resvReportCheckboxResponse.responseStatus && !resvReportCheckboxResponse.errorStatus}">
					<c:forEach var="reportDateTimeCheck"  items="${resvReportCheckboxResponse.reportDateTimeCheckBoxList}">						
						<form:checkbox path="eventDateTimeIds" checked="${
					   (resReportRequest.selectedEventDateTimeIds == '' || resReportRequest.selectedEventDateTimeIds == null) ==true ? '' :
					   fn:contains(resReportRequest.selectedEventDateTimeIds,reportDateTimeCheck.eventDateTimeId)==true ? 'checked' : ''}" 
					   value="${reportDateTimeCheck.eventDateTimeId}" class="noWidth"/>
					   ${reportDateTimeCheck.dateTime}
					</c:forEach>
				 </c:if>
			 </div>
		  </div>
		  <dl class="clear-all">
			 <dt>
				<span class="required">*</span>Reservation Status:
			 </dt>
			 <dd class="noHeight">
				<!-- Please donot change check box values, If we want to change we need to updated in REST WS Query..!
				   If we want we can change labels 
				   
				   Appointment Status values in Backed
				   -----------------------------------
				   No Show = 31  -- please dont chage this value we are using this value in backend as hard coded
				   CONFIRM = 11
				   CANCEL = 21
				   DISPLACED = 32
				   -->
				<div style="width:900px;">
				   <form:select path="otherResvStatusList"  multiple="true"  style='height: 100%;' 
					  size="${fn:length(resvStatusList.resvStatusList) gt 8 ? (fn:length(resvStatusList.resvStatusList)/2) : 5}" >
					  <form:options items="${resvStatusList.resvStatusList}" itemLabel="resvStatus" itemValue="statusVal" />
				   </form:select>
				</div>
				<form:checkbox path="resvStatusFields" checked="${
				   (resReportRequest.selectedStatusFields == '' || resReportRequest.selectedStatusFields == null) ==true ? 'checked' :
				   fn:contains(resReportRequest.selectedStatusFields,11)==true ? 'checked' : ''}" value="11" class="noWidth"/>
				Confirmed<br/>
				<form:checkbox path="resvStatusFields" checked="${
				   (resReportRequest.selectedStatusFields == '' || resReportRequest.selectedStatusFields == null) ==true ? '' :
				   fn:contains(resReportRequest.selectedStatusFields,21)==true ? 'checked' : ''}" value="21" class="noWidth"/>
				Canceled<br/>
				<form:checkbox path="resvStatusFields" checked="${
				   (resReportRequest.selectedStatusFields == '' || resReportRequest.selectedStatusFields == null) ==true ? '' :
				   fn:contains(resReportRequest.selectedStatusFields,24)==true ? 'checked' : ''}" value="24" class="noWidth"/>
				Displaced
			 </dd>
		  </dl>
		  <div class="clear-all"></div>
		  <span ><b style="color:red;">Note : </b><i>Multi Select Combo Box: Press & Hold CTRL key while clicking items</i></span>
		  <div class="float-right">
			 <input id="resetBtn" name="displayReportBtn" type="button" class="btn" value=" Reset "/>
			 <input id="displayReportBtn" name="displayReportBtn" type="submit" class="btn" value="Run Report"/>
		  </div>
		 </div>
	   </form:form>
	</div>
	<div class="clear-all"></div> 
		  <!--Search bar starts --> 

	<div class="options">
	  <div class="txt-bold">Report Results</div>
	  <div class="clear-all"></div>
	</div>

	<!-- Main tables starts -->
	<c:if test="${!resReportResponse.responseStatus && resReportResponse.errorStatus}">
		Nothing found to display.
	</c:if>
	<c:if test="${resReportResponse.responseStatus && !resReportResponse.errorStatus}">
		<c:set var = "titles" value="${fn:split(resReportResponse.title, ',')}"/>
		<c:set var = "javaRefs" value="${fn:split(resReportResponse.javaRef, ',')}"/>
		<c:set var = "hides" value="${fn:split(resReportResponse.hides, ',')}"/>

		<display:table id="resvReport" name="resReportResponse.reportsList" export="true" requestURI="" pagesize="100" class="main-table" >

			<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
				<c:if test="${fn:contains(hides[i], 'N')}">
					<display:column title="${titles[i]}" sortable="true">
						<%-- CompanyName,ProcedureName,DepartmentName  dummay we are not displaying --%>
						${ ( resvReport[javaRefs[i]] ne "dummy" && resvReport[javaRefs[i]]   ne "Dummy" 
							&& resvReport[javaRefs[i]] ne "dummay" && resvReport[javaRefs[i]]   ne "Dummay") ?  resvReport[javaRefs[i]] : " - " }
					</display:column>
				</c:if>
			</c:forEach>
			
			<display:setProperty name="export.excel.filename" value="Report Results.xls" />
			<display:setProperty name="export.csv.filename" value="Report Results.csv" />
			<display:setProperty name="export.pdf.filename" value="Report Results.pdf" />
			<display:setProperty name="export.xml.filename" value="Report Results.xml" />
			<display:setProperty name="export.rtf.filename" value="Report Results.rtf" />

		</display:table>
	</c:if>
    <!-- Main tables ends -->
	
	<%-- IMP : Dont delete
	//http://stackoverflow.com/questions/16302554/view-all-attributes-of-java-object-in-jsp-jstl
	<c:if test="${resReportResponse.responseStatus && !resReportResponse.errorStatus}">
		<c:set var = "titles" value="${fn:split(resReportResponse.title, ',')}"/>
		<c:set var = "javaRefs" value="${fn:split(resReportResponse.javaRef, ',')}"/>

		<c:forEach var="resvReport"  items="${resReportResponse.reportsList}">
				<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
					<b>${titles[i]} </b>  -   ${resvReport[javaRefs[i]]}
				</c:forEach>
				<ul/><br/>
		</c:forEach>
	</c:if>
	--%>
<!-- Body ends -->