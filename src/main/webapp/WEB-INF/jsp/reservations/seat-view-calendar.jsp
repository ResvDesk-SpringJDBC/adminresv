<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>Reservations > Reservation Calendar</title>

<script type="text/javascript" src="static/js/validation/reservations/seatViewCalendarFormValidation.js?version=1.3"></script>
<script type="text/javascript" src="static/js/validation/reservations/reservationsHelper.js?version=1.6"></script>
<script type="text/javascript" src="static/js/validation/JSCalenderHelper.js?version=1.3"></script>

<!-- inline demo CSS (combined + minified for performance; see comments for raw source URLs) -->
<link rel="stylesheet" type="text/css" href="static/audio_play/demo/index-rollup.css"/>
<!-- the SoundManager 2 API -->
<script type="text/javascript" src="static/audio_play/script/soundmanager2.js"></script>
<!-- 360 UI demo, canvas magic for IE -->
<!--[if lt IE 9]><script type="text/javascript" src="static/audio_play/demo/360-player/script/excanvas.js"></script><![endif]-->
<!-- SM2 demo/homepage-specific stuff -->
<script type="text/javascript">var is_home = true;</script>
<!-- inline demo JS (combined + minified; see comments for raw source URLs) -->
<script type="text/javascript" src="static/audio_play/demo/index-rollup.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
			prepareDatesMapAfterAvailDatesLoaded();
			initiateJSCalender("date");
	});
</script>

<!-- Body starts -->
    
	<div class="error" id="customerNameError"></div>
	<div class="error" id="locationIdError"></div>
	<div class="error" id="eventIdError"></div>		
	<div class="error" id="dateError"></div>
	<div class="error" id="timeError"></div>

    <div class="dailyCal_head">
      <div class="cal_head-new">Reservation &gt; Calendar   &gt;</div>
      <div class="cal_button"> 
			<a href="javascript:donothing();" onclick="searchSeatViewCalender('seatview');" class="active">Seat View</a> 
			<a href="javascript:donothing();" onclick="searchDailyViewCalender('seatview');">Daily View</a> 
			<%-- <a href="monthly-view-calendar.html">Monthly View</a>  --%>
	  </div>
      <div id="special-demo-left" class="sm2-inline-list" style="float:right">
        <div class="dailyCal_refresh" style="float:right"> 
			<a title="Refresh" href="javascript:void(0);" onclick="onRefreshClick()"></a> 
		</div>
      </div>
      <div style="clear:both"></div>
    </div>

    <!--Search bar starts -->
	<form:form action="search-seat-view-calendar.html" method="POST" id="calendarForm" modelAttribute="seatViewCalendar">  
    <div class="search-bar">

	  <form:hidden  path="changedParameter" id="changedParameter"/>
      <dl style="width:auto">
        <dt>Clients:</dt>
        <dd style="width:auto">
			 <form:input path="customerName" id="customerName" style="margin-right:0; padding-right:0px"  value="" class="ui-autocomplete-input" />
			 <form:hidden  path="customerId" id="customerId"/>
			  <a href="javascript:void(0)" title="Clear Client" style="text-decoration: none; margin-right:10px; margin-left:-25px;" onclick="clearCustomerDetails();"> 
					<img src="static/images/clear_field.png" alt="Clear" style="vertical-align:middle"> 
			  </a> 
		  </dd>
      </dl>
      <dl>
        <dt>Locations:</dt>
        <dd>
			<form:select path="locationId" id="locationId" class="leftFloat marginLTen paddingAll selectChangeClass">
				<form:option value="-1" label="Select  Location"/> 
				<form:options items="${locationListResponse.locationList}" itemLabel="locationName" itemValue="locationId"></form:options>
			</form:select>
        </dd>
      </dl>
      <dl>
        <dt>Events:</dt>
        <dd>
          <form:select path="eventId" id="eventId" class="leftFloat marginLTen paddingAll selectChangeClass">
				<form:option value="-1" label="Select  Event"/> 
				<form:options items="${eventListResponse.eventList}" itemLabel="eventName" itemValue="eventId"></form:options>
			</form:select>
        </dd>
      </dl>
      <dl>
        <dt>Date:</dt>
        <dd>
          <form:input type="text" path="date" id="date"/>
		  <input type="hidden" id="availableDate" value="${seatViewCalendar.date}"/>
		  <form:hidden path="jsCalendarDate" id="jsCalendarDate"/>

		  <input type="hidden" id="jsCalendarMinDate" value="${jsCalDateListResponse.minDate}"/>
		  <input type="hidden" id="jsCalendarMaxDate" value="${jsCalDateListResponse.maxDate}"/>
		  <div class="datePicker_Class" id="dateHiddenDivId">				
				<c:forEach var="dateData"  items="${jsCalDateListResponse.dateMap}">
					<input type="hidden" id="${dateData.key}" value="${dateData.key}_${dateData.value}"/>
				</c:forEach>
		  </div>
        </dd>
      </dl>
      <dl>
        <dt>Time: </dt>
        <dd>
			<form:select path="time" id="time" class="leftFloat marginLTen paddingAll selectChangeClass">
				<%-- <form:option value="-1" label="Select  Time"/> --%>
				<form:options items="${timeListResponse.timeList}"></form:options>
			</form:select>
        </dd>
      </dl>
	   
	  <dl>
        <dt><form:checkbox path="showRemainderIcons" value="true" class="noWidth"/></dt>
        <dd>Show Reminder Icons</dd>
      </dl>
	  
      <div class="float-right" style="margin-top:10px">
        <input name="btnSearch"  id="seatViewSearchBtn" type="button" class="btn" value="Search">
      </div>
      <div class="clear-all"></div>
    </div>
	 </form:form>
    <!--Search bar starts --> 

	<!--Options starts -->
    <div class="options" style="max-width: 1244px;">
      <div class="txt-bold">Seat View</div>
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 
    
    <!-- Main tables starts -->
    <div class="dailyCal">
		<c:if test="${seatsCalendarView.responseStatus && !seatsCalendarView.errorStatus}">
			<table id="appontment" class="main-table seat-view">
			  <tbody>
					<c:set var = "titles" value="${fn:split(seatsCalendarView.title, ',')}"/>
					<c:set var = "javaRefs" value="${fn:split(seatsCalendarView.javaRef, ',')}"/>
					<c:set var = "hides" value="${fn:split(seatsCalendarView.hides, ',')}"/>

					<c:forEach var="seatViewRow" items="${seatsCalendarView.seatView}">
						<tr>
							<c:forEach var="seatViewColumn" items="${seatViewRow}" >
								 <c:if test="${fn:contains(seatViewColumn.booked, 'Y')}">
									  <td class="dotdiv rline2 avail">
										  <fmt:parseNumber var="customerId" type="number" value="${seatViewColumn.reservationDetails.customerId}"/>
										  <fmt:parseNumber var="scheduleId" type="number" value="${seatViewColumn.reservationDetails.scheduleId}"/>
										  <a class="showCustWithBookedResvsWindow" style="color:#003499;float:left" href="javascript:void(0);"
										  id="customerId=${customerId}&scheduleId=${scheduleId}">
											${seatViewColumn.reservationDetails.firstName} ${seatViewColumn.reservationDetails.lastName}
											<span class="tooltip">
												<span class="top"></span>
												<span class="middle">
													<b>Reservation Details:</b><br><br>

													<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
														<c:if test="${fn:contains(hides[i], 'N')}">
															<b>${titles[i]} : </b>${seatViewColumn.reservationDetails[javaRefs[i]]}<br>
														</c:if>
													</c:forEach>
												  </span>
												  <span class="bottom"></span>
											 </span>												
										   </a>
										   &nbsp;&nbsp;
										   <c:if test="${!fn:contains(seatViewColumn.reservationDetails.fileFullPath, '#')}">
											   <div id="special-demo-left" class="sm2-inline-list">
													<div style="float:left;" class="ui360">
														<a href="${seatViewColumn.reservationDetails.fileFullPath}"  
														class="norewrite exclude button-exclude inline-exclude" alt="Speaker" title="Speaker">
														</a>
													</div>
												</div>											
												&nbsp;
										   </c:if>
										  <div class="float-left">
											<img src="${fn:contains(seatViewColumn.reservationDetails.screened,'Y') ? 'static/images/screened.ico' : 'static/images/not_screened.ico'}"											
											width="22" height="22" 
											alt="${fn:contains(seatViewColumn.reservationDetails.screened,'Y') ? 'Screened' : 'Not Screened'}" style="float:left;cursor:pointer;margin-right:3px" title="${fn:contains(seatViewColumn.reservationDetails.screened,'Y') ? 'Screened' : 'Not Screened'}" 
											params="customerId=${customerId}&scheduleId=${scheduleId}&screened=${seatViewColumn.reservationDetails.screened}"
											class="updateScreenedStatus"/>
										  </div>
										   
										  <c:if test="${seatViewCalendar.showRemainderIcons}">
											 <c:if test="${fn:contains(seatViewColumn.reservationDetails.notifyByPhone, 'Y')}">
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus==0 ||		seatViewColumn.reservationDetails.notifyPhoneStatus==1}">
													<img src="static/images/phone-orange.png" width="22" height="22" style="float: left;" 
													alt="Phone Notification" title="Phone Notification" /> 
													<img src="static/images/pendingIcon.png" width="22" height="22" 
													style="float: left;" alt="Pending" title="Pending" /> 
												</c:if>		
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus==2}">
													<img src="static/images/phone-orange.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/pendingIcon.png" width="22" height="22"
													style="float: left;" alt="In Progress" title="In Progress" />
												</c:if>	
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus==3}">
													<img src="static/images/phone-black.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/human-grey.png" width="22" height="22"
													style="float: left;" alt="No Input" title="No Input" />
												</c:if>	
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus==4}">
													<img src="static/images/phone-black.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/speaker-black.png" width="22" height="22"
													style="float: left;" alt="Ans Machine" title="Ans Machine" />
												</c:if>	
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus==5}">
													<img src="static/images/phone-green.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/human-green.png" width="22" height="22"
													style="float: left;" alt="Confirm" title="Confirm" />
												</c:if>	
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus==6}">
													<img src="static/images/phone-red.png" width="22" height="22" 
													style="float: left;" alt="Phone Notification" title="Phone Notification" /> 
													<img src="static/images/human-red.png" width="22" height="22" 
													style="float: left;" alt="Conflict" title="Conflict" /> 
												</c:if>	
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus==7}">
													<img src="static/images/phone-red.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/x-red.png" width="22" height="22"
													style="float: left;" alt="Do Not Call" title="Do Not Call" />
												</c:if>	
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus==8}">
													<img src="static/images/phone-red.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/x-red.png" width="22" height="22"
													style="float: left;" alt="Terminated" title="Terminated" />
												</c:if>	
												<c:if test="${seatViewColumn.reservationDetails.notifyPhoneStatus>8}">
													<img src="static/images/phone-red.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/x-red.png" width="22" height="22"
													style="float: left;" alt="Suspended" title="Suspended" />
												</c:if>	
												<fmt:parseNumber var="i" integerOnly="true"  type="number" value="${seatViewColumn.reservationDetails.attemptCount}" />
												<span class="attemptCount"> ${i}</span>
											 </c:if>

											 <c:if test="${fn:contains(seatViewColumn.reservationDetails.notifyBySMS, 'Y')}">
												<c:if test="${seatViewColumn.reservationDetails.notifySMSStatus==0 ||		seatViewColumn.reservationDetails.notifySMSStatus==1}">
													<img src="static/images/sms-orange.png" width="22" height="22"
													style="float: left;" alt="Text Pending" title="Text Pending"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifySMSStatus==2}">
													<img src="static/images/sms-orange.png" width="22" height="22"
													style="float: left;" alt="Text In Progress" title="Text In Progress"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifySMSStatus==3}">
													<img src="static/images/sms-black.png" width="22" height="22"
													style="float: left;" alt="Text Sent" title="Text Sent"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifySMSStatus==4}">
													<img src="static/images/sms-green.png" width="22" height="22"
													style="float: left;" alt="Text Confirmed" title="Text Confirmed"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifySMSStatus==5}">
													<img src="static/images/sms-red.png" width="22" height="22"
													style="float: left;" alt="Text Conflict" title="Text Conflict"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifySMSStatus==6}">
													<img src="static/images/sms-red.png" width="22" height="22"
													style="float: left;" alt="Do Not Text" title="Do Not Text"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifySMSStatus==7}">
													<img src="static/images/sms-red.png" width="22" height="22"
													style="float: left;" alt="Text Terminated" title="Text Terminated"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifySMSStatus>7}">
													<img src="static/images/sms-red.png" width="22" height="22"
													style="float: left;" alt="Text Suspended" title="Text Suspended"/>
												</c:if>
											 </c:if>

											  <c:if test="${fn:contains(seatViewColumn.reservationDetails.notifyByEmail, 'Y')}">
												<c:if test="${seatViewColumn.reservationDetails.notifyEmailStatus==0 ||		seatViewColumn.reservationDetails.notifyEmailStatus==1}">
													<img src="static/images/email-orange.png" width="22" height="22"
													style="float: left;" alt="Email Pending" title="Email Pending"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifyEmailStatus==2}">
													<img src="static/images/email-orange.png" width="22" height="22"
													style="float: left;" alt="Email In Progress" title="Email In Progress"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifyEmailStatus==3}">
													<img src="static/images/email-black.png" width="22" height="22"
													style="float: left;" alt="Email Sent" title="Email Sent"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifyEmailStatus==4}">
													<img src="static/images/email-green.png" width="22" height="22"
													style="float: left;" alt="Email Confirmed" title="Email Confirmed"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifyEmailStatus==5}">
													<img src="static/images/email-red.png" width="22" height="22"
													style="float: left;" alt="Email Conflict" title="Email Conflict"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifyEmailStatus==6}">
													<img src="static/images/email-red.png" width="22" height="22"
													style="float: left;" alt="Do Not Email" title="Do Not Email"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifyEmailStatus==7}">
													<img src="static/images/email-red.png" width="22" height="22"
													style="float: left;" alt="Email Terminated" title="Email Terminated"/>
												</c:if>
												<c:if test="${seatViewColumn.reservationDetails.notifyEmailStatus>7}">
													<img src="static/images/email-red.png" width="22" height="22"
													style="float: left;" alt="Email Suspended" title="Email Suspended"/>
												</c:if>
											  </c:if>
										  </c:if>											  
									  </td>
								 </c:if>	
								  <c:if test="${fn:contains(seatViewColumn.booked, 'N')}">
									<td id="TD_${seatViewColumn.seatId}_${seatsCalendarView.eventDateTimeId}"
										class="${fn:contains(seatViewColumn.reserved, 'N') ? 'appt' : 'solid rline2  reserved blocked'}">
										<span class="seat-info" id="Book_${seatViewColumn.seatId}_${seatsCalendarView.eventDateTimeId}" 
										style="${fn:contains(seatViewColumn.reserved, 'N') ? 'display:block;' : 'display:none;'}">
											<a href="javascript:void(0)" class="showReservationBookingWindow" 
												id="eventDateTimeId=${seatsCalendarView.eventDateTimeId}&seatId=${seatViewColumn.seatId}&time=${seatViewCalendar.time}&seatNumber=${seatViewColumn.seatNumber}">
												<b> Book <b>
											</a>
										</span>

										<span class="seat-info" id="Reserve_${seatViewColumn.seatId}_${seatsCalendarView.eventDateTimeId}"			style="${fn:contains(seatViewColumn.reserved, 'N') ? 'display:block;' : 'display:none;'}">	
											<a href="javascript:void(0)" class="reserveUnReservedSeat" 
												params="seatId=${seatViewColumn.seatId}&reserved=Y&eventDateTimeId=${seatsCalendarView.eventDateTimeId}">
												<b> Reserve <b>
											</a>
										</span>

										<span class="seat-info" id="Reserved_${seatViewColumn.seatId}_${seatsCalendarView.eventDateTimeId}" style="${fn:contains(seatViewColumn.reserved, 'Y') ? 'display:block;' : 'display:none;'}">
											<b> Reserved <b>
											<a href="javascript:void(0)" class="unReserveReservedSeat" 
												params="seatId=${seatViewColumn.seatId}&reserved=N&eventDateTimeId=${seatsCalendarView.eventDateTimeId}">
												<img src="static/images/cal-cancelled.png" style="float:left;cursor:pointer;">
											</a>
										</span>

										<span class="seat-info">${seatViewColumn.seatNumber}</span>
									</td>
								 </c:if>								  	
							</c:forEach>
						</tr>
					</c:forEach>					
			  </tbody>
			</table>
		</c:if>	
		<c:if test="${seatsCalendarView.errorStatus && !seatsCalendarView.responseStatus }">
			<b>No records found</b>
		</c:if>	
    </div>
  <!-- Main tables ends --> 

<div class="jqmWindow" id="ex2">Please wait...</div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<!-- Body ends -->