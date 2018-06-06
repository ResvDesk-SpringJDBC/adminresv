<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<title>Welcome</title>

<script type="text/javascript" src="static/js/graphs/highcharts/highcharts.js"></script>
<script type="text/javascript" src="static/js/graphs/highcharts/highcharts-more.js"></script>
<script type="text/javascript" src="static/js/graphs/highcharts/modules/exporting.js"></script>

<script type="text/javascript" src="static/js/graphs/graphs.js?version=1.3"></script>
<script type="text/javascript" src="static/js/validation/homeHelper.js?version=1.1"></script>

<script type="text/javascript" src="static/js/validation/JSCalenderHelper.js?version=1.3"></script>

<!-- Body starts -->
  <div class="dailyCal_head">
    <div class="cal_head-new">Welcome ${homeBean.adminInfoTO.firstName} ${homeBean.adminInfoTO.lastName}</div>
    <div class="float-right"> 
      <input type="hidden" id="schedulerClosedStatus" value="${homeBean.resvSysConfig.schedulerClosed}"/>
	  <!-- Success Message new starts -->
	  <div class="home_openstatus float-right" id="home_openstatusDiv" style="display: none;">Scheduler is Currently Open</div>
      <!-- Success Message new ends --> 
      <!-- Close Message new starts -->
      <div class="home_closestatus float-right" id="home_closestatusDiv" style="display: none;">Scheduler is Currently Closed</div>
      <!-- Close Message new ends --> 
      
      <!-- Succesfull message-->
      <div class="home_green float-right" id="schedularValChangeResponce"></div>
    </div>
    <div class="clear-all"></div>
  </div>
  <div class="clear-all"></div>

  <div class="homeMainContainer">
    <h2>${homeBean.adminInfoTO.clientTO.client_name}</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table homeContent">
      <tr>
        <td width="30%">Address:</td>
        <td>${homeBean.adminInfoTO.clientTO.address}</td>
      </tr>
      <tr class="altColor">
        <td>Business Phone:</td>
        <td>${homeBean.adminInfoTO.clientTO.contact_phone}</td>
      </tr>
      <tr>
        <td>Admin Contact Name:</td>
        <td>${homeBean.adminInfoTO.clientTO.client_name}</td>
      </tr>
      <tr class="altColor">
        <td>Admin Contact Email:</td>
        <td>${homeBean.adminInfoTO.clientTO.contact_email}</td>
      </tr>
      <tr>
        <td>Admin Contact Phone</td>
        <td>${homeBean.adminInfoTO.clientTO.contact_phone}</td>
      </tr>
      <tr class="altColor">
        <td>Billing Contact Name:</td>
        <td>${homeBean.adminInfoTO.clientTO.client_name}</td>
      </tr>
      <tr>
        <td>Billing Contact Email:</td>
        <td>${homeBean.adminInfoTO.clientTO.contact_email}</td>
      </tr>
      <tr class="altColor">
        <td>Billing Contact Email CC:</td>
        <td>${homeBean.adminInfoTO.clientTO.contact_email}</td>
      </tr>
    </table>

	<%--  Scheduler Info Starts --%>
	<c:set var="tdCount" value="1"/>
	<h2>Scheduler Info</h2>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table homeContent">
	   <c:if test="${homeBean.adminInfoTO.clientTO.forward_url!=null && homeBean.adminInfoTO.clientTO.forward_url!=''}">
			<tr class="${tdCount % 2 == 0 ? 'altColor' : ''}">
				<td width="30%">Admin Site: </td>
				<td>${homeBean.adminInfoTO.clientTO.forward_url}</td>
			</tr>
			<c:set var="tdCount" value="${tdCount+1}"/>
	   </c:if>
	   <c:if test="${homeBean.adminInfoTO.clientTO.appt_link!=null && homeBean.adminInfoTO.clientTO.appt_link!=''}">
			<tr class="${tdCount % 2 == 0 ? 'altColor' : ''}">
				<td>Online Scheduler Link:</td>
				<td><a href="${homeBean.adminInfoTO.clientTO.appt_link}" target="_blank">${homeBean.adminInfoTO.clientTO.appt_link}</a></td>
			</tr>
			<c:set var="tdCount" value="${tdCount+1}"/>
	   </c:if>
	   <c:if test="${homeBean.adminInfoTO.clientTO.phone_scheduler_number!=null && homeBean.adminInfoTO.clientTO.phone_scheduler_number!=''}">
				<td>Phone Scheduler Number:</td>
				<td>
					${homeBean.adminInfoTO.clientTO.phone_scheduler_number}
				</td>
			</tr>
			<c:set var="tdCount" value="${tdCount+1}"/>
	   </c:if>
	   <c:if test="${homeBean.adminInfoTO.clientTO.direct_access_phone!=null && homeBean.adminInfoTO.clientTO.direct_access_phone!=''}">
			<tr class="${tdCount % 2 == 0 ? 'altColor' : ''}">
				<td>Direct Access Phone # :</td>
				<td>${homeBean.adminInfoTO.clientTO.direct_access_phone}</td>
			</tr>
			<c:set var="tdCount" value="${tdCount+1}"/>
		</c:if>
	</table>
	<%--  Scheduler Info Ends --%>

    <h2>Locations</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table homeContent">     
		 <c:forEach items="${locationListResponse.locationList}" var="location" varStatus="loop">
			<tr class="${loop.count%2 == 0 ? 'altColor' : ''}">
				<td>
					<ul>							
						<li>${location.locationName} 
							  ${location.address!='' ? ',' : '' } ${location.address}
							  ${location.city !='' ? ',' : '' } ${location.city}
							  ${location.state!='' ? ',' : '' } ${states[location.state]}
						</li>								
					</ul>
				</td>
			</tr>
		</c:forEach>
	</table>

	 <h2>Events</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table homeContent">     
		 <c:forEach items="${eventListResponse.eventList}" var="event" varStatus="loop">
			<tr class="${loop.count%2 == 0 ? 'altColor' : ''}">
				<td>
					<ul>							
						<li>${event.eventName} - ${event.duration} (Mins)</li>								
					</ul>
				</td>
			</tr>
		</c:forEach>
	</table>

  </div>
  <div class="homeMainContainerGraph">
    <div id="opendSchedulerDiv" class="home_mainbuttons" style="display: none;">
		<input class="homebtn" type="button" value="Close Scheduler Now for Maintenance" onclick="changeSchedulerStatus('close');">
    </div>
    <div id="closedSchedulerDiv" class="home_mainbuttons" style="display: block;">
      <input class="homebtn" type="button" value="Open Scheduler" onclick="changeSchedulerStatus('open');">
    </div>
    	
	<%-- Stacked / Bar Chart Starts --%>
		<div >			
			Select Location
			<select id="stackedChartLocId"  STYLE="width:160px">
				<c:if test="${locationListResponse.responseStatus && !locationListResponse.errorStatus}">
					<c:forEach items="${locationListResponse.locationList}" var="location" varStatus="loop">
						<option value="${location.locationId}">${location.locationName}</option>
					</c:forEach>
				</c:if>	
				<c:if test="${!locationListResponse.responseStatus && locationListResponse.errorStatus}">
					<option value="-1">All Locations</option>
				</c:if>	
			</select>
			&nbsp;
			Select Date <input id="stackedChartDate" value="${date}"/>

			<input type="hidden" id="availableDate" value="${date}"/>
			<input type="hidden" id="jsCalendarMinDate" value="${jsCalDateListResponse.minDate}"/>
			<input type="hidden" id="jsCalendarMaxDate" value="${jsCalDateListResponse.maxDate}"/>
			<div class="datePicker_Class" id="dateHiddenDivId">				
				<c:forEach var="dateData"  items="${jsCalDateListResponse.dateMap}">
					<input type="hidden" id="${dateData.key}" value="${dateData.key}_${dateData.value}"/>
				</c:forEach>
			</div>

		</div>	
		<br/> 
		<div id="stackedcharts" style="min-width: 310px; height: 400px; margin: 0 auto"></div>					
		<br/> 
	<%-- Stacked / Bar Chart Ends --%>
  
  </div>
  <div class="clear-all"></div>
<!-- Body ends -->