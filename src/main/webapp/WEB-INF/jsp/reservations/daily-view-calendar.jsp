<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<title>Reservations > Reservation Calendar</title>

<script type="text/javascript" src="static/js/validation/reservations/dailyViewCalendarFormValidation.js?version=1.2"></script>
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
		initiateJSCalender("jsCalendarDate");
	});
</script>

<!-- Body starts -->
	
	<div class="error" id="customerNameError"></div>
	<div class="error" id="locationIdError"></div>
	<div class="error" id="eventIdError"></div>		
	<div class="error" id="dateError"></div>

    <div class="dailyCal_head">
      <div class="cal_head-new">Reservation &gt; Calendar   &gt;</div>
      <div class="cal_button"> 
		<a href="javascript:donothing();" onclick="searchSeatViewCalender('dailyview');">Seat View</a> 
		<a href="javascript:donothing();" onclick="searchDailyViewCalender('dailyview');" class="active">Daily View</a> 
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
<form:form action="search-daily-view-calendar.html" method="POST" id="calendarForm" modelAttribute="dailyCalendar"> 
    <div class="search-bar">

	  <form:hidden  path="changedParameter" id="changedParameter"/>
      <div class="float-left">Clients:
		<form:input path="customerName" id="customerName" style="margin-right:0; padding-right:25px" value="" class="ui-autocomplete-input" />
		<form:hidden  path="customerId" id="customerId"/>
        <a href="javascript:donothing();" title="Clear Client" style="text-decoration: none; margin-right:10px; margin-left:-25px;" onclick="clearCustomerDetails();"> 
			<img src="static/images/clear_field.png" alt="Clear" style="vertical-align:middle"> 
		</a> 
	  </div>
      <div class="float-left">Locations:
        <form:select path="locationId" id="locationId" class="leftFloat marginLTen paddingAll selectChangeClass">
			<form:option value="-1" label="Select  Location"/> 
			<form:options items="${locationListResponse.locationList}" itemLabel="locationName" itemValue="locationId"></form:options>
		</form:select>
      </div>
      <div class="float-left">Events:
        <form:select path="eventId" id="eventId" class="leftFloat marginLTen paddingAll selectChangeClass">
			<form:option value="-1" label="Select  Event"/> 
			<form:options items="${eventListResponse.eventList}" itemLabel="eventName" itemValue="eventId"></form:options>
		</form:select>
      </div>	   
	  
      <div class="float-left">
        <form:checkbox path="showRemainderIcons" id="showRemainderIcons" value="true" class="noWidth"/> Show Reminder Icons 
	  </div>
	  
      <div class="clear-all"></div>
    </div>
    <!--Search bar starts --> 
    
    <!--Options starts -->
    <div class="options" style="max-width: 1244px;">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
            <td class="txt-bold" width="200" valign="top">
				<%--
				<a title="First Available Date" href="javascript:void(0);" onclick="onGoToFirstAvailableDateClickd();"> 
					Go to First Available Date				
				</a>
				--%>
				<form:hidden path="firstAvailableDate" id="firstAvailableDate"/>
			</td>
            <td style="text-align: center;" class="font16 txt-bold">
				<a href="javascript:void(0)" onclick="previousDateLinkClicked();" style="text-decoration: none;"> 
					<img src="static/images/previous.png" alt="Previous" title="Previous" style="vertical-align: bottom"/> 
					<form:hidden path="previousAvailableDate" id="previousAvailableDate"/>
				</a> 
				${dailyCalendar.selectedDateDisplayFormat}
				<form:hidden path="selectedDate" id="selectedDate"/>
				<a href="javascript:void(0)" onclick="nextDateLinkClicked();" style="text-decoration: none;"> 
					<img src="static/images/next.png" alt="Next" title="Next" style="vertical-align: bottom"/>
					<form:hidden path="nextAvailableDate" id="nextAvailableDate"/>
				</a>
			</td>
            <td align="right" width="200" style="text-align: right;z-index:9999;">
				<span class="float-righ1t">
					<form:input path="jsCalendarDate" id="jsCalendarDate"/>
					<form:hidden path="date" id="date"/>
				</span>				
			    <input type="hidden" id="availableDate" value="${dailyCalendar.jsCalendarDate}"/>

				<input type="hidden" id="jsCalendarMinDate" value="${jsCalDateListResponse.minDate}"/>
				<input type="hidden" id="jsCalendarMaxDate" value="${jsCalDateListResponse.maxDate}"/>
			    <div class="datePicker_Class" id="dateHiddenDivId">				
					<c:forEach var="dateData"  items="${jsCalDateListResponse.dateMap}">
						<input type="hidden" id="${dateData.key}" value="${dateData.key}_${dateData.value}"/>
					</c:forEach>
			    </div>
			</td>
          </tr>
        </tbody>
      </table>
    </div>
    <!--Options ends --> 

	<!-- Main tables starts -->
    <div class="dailyCal">
	  <c:if test="${dailyCalendarView.responseStatus && !dailyCalendarView.errorStatus}">
		  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
			<tbody>
				  <tr>
					<th width="10%" class="center-align time">Seat/Time</th>
					<c:forEach var="caleandarViewColumn" items="${dailyCalendarView.caleandarView[0]}">	
						<th class="selectedDoctor" >
							${caleandarViewColumn.time}&nbsp;
						</th>
					</c:forEach>
				  </tr>
			  <c:set var = "titles" value="${fn:split(dailyCalendarView.title, ',')}"/>
			  <c:set var = "javaRefs" value="${fn:split(dailyCalendarView.javaRef, ',')}"/>
			  <c:set var = "hides" value="${fn:split(dailyCalendarView.hides, ',')}"/>

			  <c:forEach var="caleandarView" items="${dailyCalendarView.caleandarView}" varStatus="rowIndex">				
				<tr> 
					<%-- Seat No Display 
					<c:set var = "isSeatNoDisplayed" value="false"/>
					<c:forEach var="caleandarViewColumn" items="${caleandarView}" varStatus="columnIndex">	
						<c:if test="${!isSeatNoDisplayed}">
							<c:if test="${not empty caleandarViewColumn}">
								<td>
									${caleandarViewColumn.seatNumber}
								</td>
								<c:set var = "isSeatNoDisplayed" value="true"/>
							</c:if>	
						</c:if>	
					</c:forEach>
					--%>
					<c:forEach var="caleandarViewColumn" items="${caleandarView}" varStatus="columnIndex">	
						<c:if test="${columnIndex.index eq 0}">
							<td>							
								${rowIndex.index + 1}
							</td>
						</c:if>
						<c:if test="${not empty caleandarViewColumn}">
							<c:if test="${fn:contains(caleandarViewColumn.booked, 'Y')}">
								  <td class="dotdiv rline2 avail">
									  <fmt:parseNumber var="customerId" type="number" value="${caleandarViewColumn.toolTipDetails.customerId}"/>
									  <fmt:parseNumber var="scheduleId" type="number" value="${caleandarViewColumn.toolTipDetails.scheduleId}"/>
									  <a class="showCustWithBookedResvsWindow" style="color:#003499;float:left" href="javascript:void(0);"
									   id="customerId=${customerId}&scheduleId=${scheduleId}">
										${caleandarViewColumn.toolTipDetails.firstName} ${caleandarViewColumn.toolTipDetails.lastName}
										<span class="tooltip">
											<span class="top"></span>
											<span class="middle">
												<b>Reservation Details:</b><br><br>

												<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
													<c:if test="${fn:contains(hides[i], 'N')}">
														<b>${titles[i]} : </b>${caleandarViewColumn.toolTipDetails[javaRefs[i]]}<br>
													</c:if>
												</c:forEach>
											  </span>
											  <span class="bottom"></span>
										 </span>
									  </a>
									   &nbsp;&nbsp;
									  <c:if test="${!fn:contains(caleandarViewColumn.toolTipDetails.fileFullPath, '#')}">
										  <div id="special-demo-left" class="sm2-inline-list">
											<div style="float:left;" class="ui360">
												<a href="${caleandarViewColumn.toolTipDetails.fileFullPath}"  
												class="norewrite exclude button-exclude inline-exclude" alt="Speaker" title="Speaker"></a>
											</div>
										  </div>
										  &nbsp;&nbsp;
										</c:if>
										<div class="float-left">
											<img src="${fn:contains(caleandarViewColumn.toolTipDetails.screened,'Y') ? 'static/images/screened.ico' : 'static/images/not_screened.ico'}"											
											width="22" height="22" 
											alt="${fn:contains(caleandarViewColumn.toolTipDetails.screened,'Y') ? 'Screened' : 'Not Screened'}" style="float:left;cursor:pointer;margin-right:3px" title="${fn:contains(caleandarViewColumn.toolTipDetails.screened,'Y') ? 'Screened' : 'Not Screened'}" 
											params="customerId=${customerId}&scheduleId=${scheduleId}&screened=${caleandarViewColumn.toolTipDetails.screened}"
											class="updateScreenedStatus"/>
										</div>
										 
										  <c:if test="${dailyCalendar.showRemainderIcons}">
											 <c:if test="${fn:contains(caleandarViewColumn.toolTipDetails.notifyByPhone, 'Y')}">
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus==0 ||		caleandarViewColumn.toolTipDetails.notifyPhoneStatus==1}">
													<img src="static/images/phone-orange.png" width="22" height="22" style="float: left;" 
													alt="Phone Notification" title="Phone Notification" /> 
													<img src="static/images/pendingIcon.png" width="22" height="22" 
													style="float: left;" alt="Pending" title="Pending" /> 
												</c:if>		
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus==2}">
													<img src="static/images/phone-orange.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/pendingIcon.png" width="22" height="22"
													style="float: left;" alt="In Progress" title="In Progress" />
												</c:if>	
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus==3}">
													<img src="static/images/phone-black.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/human-grey.png" width="22" height="22"
													style="float: left;" alt="No Input" title="No Input" />
												</c:if>	
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus==4}">
													<img src="static/images/phone-black.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/speaker-black.png" width="22" height="22"
													style="float: left;" alt="Ans Machine" title="Ans Machine" />
												</c:if>	
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus==5}">
													<img src="static/images/phone-green.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/human-green.png" width="22" height="22"
													style="float: left;" alt="Confirm" title="Confirm" />
												</c:if>	
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus==6}">
													<img src="static/images/phone-red.png" width="22" height="22" 
													style="float: left;" alt="Phone Notification" title="Phone Notification" /> 
													<img src="static/images/human-red.png" width="22" height="22" 
													style="float: left;" alt="Conflict" title="Conflict" /> 
												</c:if>	
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus==7}">
													<img src="static/images/phone-red.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/x-red.png" width="22" height="22"
													style="float: left;" alt="Do Not Call" title="Do Not Call" />
												</c:if>	
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus==8}">
													<img src="static/images/phone-red.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/x-red.png" width="22" height="22"
													style="float: left;" alt="Terminated" title="Terminated" />
												</c:if>	
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyPhoneStatus>8}">
													<img src="static/images/phone-red.png" width="22" height="22"
													style="float: left;" alt="Phone Notification" title="Phone Notification" />
													<img src="static/images/x-red.png" width="22" height="22"
													style="float: left;" alt="Suspended" title="Suspended" />
												</c:if>	
												<fmt:parseNumber var="i" integerOnly="true"  type="number" value="${caleandarViewColumn.toolTipDetails.attemptCount}" />
												<span class="attemptCount"> ${i}</span>
											 </c:if>

											 <c:if test="${fn:contains(caleandarViewColumn.toolTipDetails.notifyBySMS, 'Y')}">
												<c:if test="${caleandarViewColumn.toolTipDetails.notifySMSStatus==0 ||		caleandarViewColumn.toolTipDetails.notifySMSStatus==1}">
													<img src="static/images/sms-orange.png" width="22" height="22"
													style="float: left;" alt="Text Pending" title="Text Pending"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifySMSStatus==2}">
													<img src="static/images/sms-orange.png" width="22" height="22"
													style="float: left;" alt="Text In Progress" title="Text In Progress"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifySMSStatus==3}">
													<img src="static/images/sms-black.png" width="22" height="22"
													style="float: left;" alt="Text Sent" title="Text Sent"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifySMSStatus==4}">
													<img src="static/images/sms-green.png" width="22" height="22"
													style="float: left;" alt="Text Confirmed" title="Text Confirmed"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifySMSStatus==5}">
													<img src="static/images/sms-red.png" width="22" height="22"
													style="float: left;" alt="Text Conflict" title="Text Conflict"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifySMSStatus==6}">
													<img src="static/images/sms-red.png" width="22" height="22"
													style="float: left;" alt="Do Not Text" title="Do Not Text"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifySMSStatus==7}">
													<img src="static/images/sms-red.png" width="22" height="22"
													style="float: left;" alt="Text Terminated" title="Text Terminated"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifySMSStatus>7}">
													<img src="static/images/sms-red.png" width="22" height="22"
													style="float: left;" alt="Text Suspended" title="Text Suspended"/>
												</c:if>
											 </c:if>

											  <c:if test="${fn:contains(caleandarViewColumn.toolTipDetails.notifyByEmail, 'Y')}">
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyEmailStatus==0 ||		caleandarViewColumn.toolTipDetails.notifyEmailStatus==1}">
													<img src="static/images/email-orange.png" width="22" height="22"
													style="float: left;" alt="Email Pending" title="Email Pending"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyEmailStatus==2}">
													<img src="static/images/email-orange.png" width="22" height="22"
													style="float: left;" alt="Email In Progress" title="Email In Progress"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyEmailStatus==3}">
													<img src="static/images/email-black.png" width="22" height="22"
													style="float: left;" alt="Email Sent" title="Email Sent"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyEmailStatus==4}">
													<img src="static/images/email-green.png" width="22" height="22"
													style="float: left;" alt="Email Confirmed" title="Email Confirmed"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyEmailStatus==5}">
													<img src="static/images/email-red.png" width="22" height="22"
													style="float: left;" alt="Email Conflict" title="Email Conflict"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyEmailStatus==6}">
													<img src="static/images/email-red.png" width="22" height="22"
													style="float: left;" alt="Do Not Email" title="Do Not Email"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyEmailStatus==7}">
													<img src="static/images/email-red.png" width="22" height="22"
													style="float: left;" alt="Email Terminated" title="Email Terminated"/>
												</c:if>
												<c:if test="${caleandarViewColumn.toolTipDetails.notifyEmailStatus>7}">
													<img src="static/images/email-red.png" width="22" height="22"
													style="float: left;" alt="Email Suspended" title="Email Suspended"/>
												</c:if>
											  </c:if>
										  </c:if>											 
									  </td>
								 </c:if>	
								 <c:if test="${fn:contains(caleandarViewColumn.booked, 'N')}">
									<%--
									 <c:if test="${fn:contains(caleandarViewColumn.reserved, 'N')}">
										 <td class="appt">
											<a href="javascript:void(0)" class="showReservationBookingWindow" 
											 id="eventDateTimeId=${caleandarViewColumn.eventDateTimeId}&seatId=${caleandarViewColumn.seatId}&time=${caleandarViewColumn.time}&seatNumber=${caleandarViewColumn.seatNumber}"><b> Open <b></a> 
											<a href="javascript:void(0)" class="blockReservation txt-bold"> Block </a>
										</td>
									 </c:if>
									 <c:if test="${fn:contains(caleandarViewColumn.reserved, 'Y')}">
										<td class="solid rline2  reserved blocked">
											<b> Reserved <b>
										</td>
									 </c:if>
									 --%>
									 <td id="TD_${caleandarViewColumn.seatId}_${caleandarViewColumn.eventDateTimeId}"
										class="${fn:contains(caleandarViewColumn.reserved, 'N') ? 'appt' : 'solid rline2  reserved blocked'}">
										<span class="seat-info" id="Book_${caleandarViewColumn.seatId}_${caleandarViewColumn.eventDateTimeId}" 
										style="${fn:contains(caleandarViewColumn.reserved, 'N') ? 'display:block;' : 'display:none;'}">
											<a href="javascript:void(0)" class="showReservationBookingWindow" 
												id="eventDateTimeId=${caleandarViewColumn.eventDateTimeId}&seatId=${caleandarViewColumn.seatId}&time=${seatViewCalendar.time}&seatNumber=${caleandarViewColumn.seatNumber}">
												<b> Book <b>
											</a>
										</span>

										<span class="seat-info" id="Reserve_${caleandarViewColumn.seatId}_${caleandarViewColumn.eventDateTimeId}"			style="${fn:contains(caleandarViewColumn.reserved, 'N') ? 'display:block;' : 'display:none;'}">	
											<a href="javascript:void(0)" class="reserveUnReservedSeat" 
											params="seatId=${caleandarViewColumn.seatId}&reserved=Y&eventDateTimeId=${caleandarViewColumn.eventDateTimeId}">
												<b> Reserve <b>
											</a>
										</span>

										<span class="seat-info" id="Reserved_${caleandarViewColumn.seatId}_${caleandarViewColumn.eventDateTimeId}" style="${fn:contains(caleandarViewColumn.reserved, 'Y') ? 'display:block;' : 'display:none;'}">
											<b> Reserved <b>
											<a href="javascript:void(0)" class="unReserveReservedSeat" 
											params="seatId=${caleandarViewColumn.seatId}&reserved=N&eventDateTimeId=${caleandarViewColumn.eventDateTimeId}">
												<img src="static/images/cal-cancelled.png" style="float:left;cursor:pointer;">
											</a>
										</span>
									</td>
								</c:if>
						 </c:if>
						 <c:if test="${empty caleandarViewColumn}">	
							<td> &nbsp; </td>
						 </c:if>
					</c:forEach>
				</tr>
			</c:forEach>			
			 <tr>
				<th width="10%" class="center-align time">Seat/Time</th>
				<c:forEach var="caleandarViewColumn" items="${dailyCalendarView.caleandarView[0]}">	
					<th class="selectedDoctor" >
						${caleandarViewColumn.time}&nbsp;
					</th>
				</c:forEach>
			  </tr>
			</tbody>
		  </table>
	  </c:if>	
	  <c:if test="${dailyCalendarView.errorStatus && !dailyCalendarView.responseStatus }">
		<b>No records found</b>
	  </c:if>
	 </div>
	 <br/>
     <img src="static/images/notification_iconset2.gif" alt="Notification Icons" title="Notification Icons"> 
	
    <!-- Main tables ends -->
 </form:form>

<div class="jqmWindow" id="ex2">Please wait...</div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<!-- Body ends -->