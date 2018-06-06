<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="static/js/validation/reservations/reservationRemaindersFormValidation.js?version=1.0"></script>

<script type="text/javascript" src="static/js/validation/JSCalenderHelper.js?version=1.3"></script>

<script type="text/javascript">

	$(document).ready(function() {
		prepareDatesMapAfterAvailDatesLoaded();
		initiateJSCalender("jsCalendarDate");
	
		/*
		new JsDatePick({
			useMode : 2,
			target : "jsCalendarDate",
			dateFormat : "%M/%d/%Y"
		});
		*/

		$('.editStstus').click(function() {	
		  var id = $(this).attr('id');		  
		  var url = 'edit-resv-notify-status.html?'+id;
		  url = url.replace(/ /g, "%20");
		  //alert("url ::::::::: "+url);
		  $('#ex2').jqm({ajax: url,modal: true,cache: false,trigger: 'a.ex2trigger'}).jqmShow();
	    });
	});
</script>
<title>Notification > Daily View</title>
		
<div class="error" id="dateError"></div>

<!-- Body starts -->
  <form:form action="resv-reminders.html" method="POST" id="resvReminders" modelAttribute="notificationRequest"> 

    <div class="dailyCal_head">
	<form:hidden  path="changedParameter" id="changedParameter"/>
      <div class="cal_head-new">Notification > Daily View</div>
      <div class="clear-all"></div>
    </div>
    <!--Search bar starts -->
    <div class="search-bar">
      <div class="float-left" style="z-index:9999;"> Locations:
         <form:select path="locationId" id="locationId" class="leftFloat marginLTen paddingAll selectChangeClass">
			<%-- <form:option value="-1" label="All Locations"/>  --%>
			<form:options items="${locationListResponse.locationList}" itemLabel="locationName" itemValue="locationId"></form:options>
		 </form:select>
      </div>
      <div class="float-left"> Campaigns: <span>
		 <form:select path="campaignId" id="campaignId" class="leftFloat marginLTen paddingAll selectChangeClass">
			<form:option value="-1" label="All Campaigns"/> 
			<form:options items="${campaignResponse.campaignList}" itemLabel="campaignName" itemValue="campaignId"></form:options>
		 </form:select>
      </div>
      <div class="clear-all"></div>
    </div>
    <!--Search bar starts --> 
    
	 <div class="legend_container">
		<span class="yellow"></span>Pending &nbsp;&nbsp; 
		<span class="blue"></span>In Progress &nbsp;&nbsp; 
		<span class="green"></span>Completed &nbsp;&nbsp; 
		<span class="red"></span>Suspended &nbsp;&nbsp;
	</div>

    <!--Options starts -->
    <div class="options">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
			   <td class="txt-bold" width="200" valign="top">Daily View</td>
			   <td style="text-align: center;" class="font16 txt-bold">
					<a href="javascript:void(0)" onclick="previousDateLinkClicked();" style="text-decoration: none;"> 
						<img src="static/images/previous.png" alt="Previous" title="Previous" style="vertical-align: bottom"/> 
					</a> 
					<form:hidden path="previousAvailableDate" id="previousAvailableDate"/>
					${notificationRequest.selectedDateDisplayFormat}
					<form:hidden path="selectedDate" id="selectedDate"/>
					<a href="javascript:void(0)" onclick="nextDateLinkClicked();" style="text-decoration: none;"> 
						<img src="static/images/next.png" alt="Next" title="Next" style="vertical-align: bottom"/>
					</a>
					<form:hidden path="nextAvailableDate" id="nextAvailableDate"/>
				</td>
				<td align="right" width="200" style="text-align: right;z-index:9999;">
				  <span class="float-righ1t"> 
					<span style="position: relative;">
					  <form:input path="jsCalendarDate" id="jsCalendarDate"/>
					</span>
					<%-- <input name="btnSearch" type="button" class="btn" value="Go" onclick="jsCalenderDateSelected();"> --%>
					<input type="hidden" id="jsCalendarMinDate" value="${jsCalDateListResponse.minDate}"/>
					<input type="hidden" id="jsCalendarMaxDate" value="${jsCalDateListResponse.maxDate}"/>
					<div class="datePicker_Class" id="dateHiddenDivId">				
						<c:forEach var="dateData"  items="${jsCalDateListResponse.dateMap}">
							<input type="hidden" id="${dateData.key}" value="${dateData.key}_${dateData.value}"/>
						</c:forEach>
					</div>
				  </span>
				</td>
          </tr>
        </tbody>
      </table>
    </div>
    <!--Options ends --> 
</form:form>
	
	
	<!-- Main tables starts -->
<div class="dailyCal">
	<c:if test="${resvReminder.responseStatus && !resvReminder.errorStatus}">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
		<tr>
			<c:forEach items="${resvReminder.resvReminders}" var="resvReminder">
				<c:set var="dailyReminderView" value="${resvReminder.key}" />
				<th width="25%">
					<%-- <a  href="viewCampaign.html?locationId=${tableViewSearchTO.locationId}&resourceId=${calendarViewKey.resourceId}
					&notificationDate=${tableViewSearchTO.notificationDate}">${calendarViewKey.resourceName}</a>
					--%>
					<a href="javascript:void(0)">${dailyReminderView.eventName}</a>					 
					<br>
					<span class="cal-info">
						${dailyReminderView.totalNotifications} Notifications<br />
						${dailyReminderView.totalConfirmed} Confirmed<br />
						<%-- 
						${dailyReminderView.conflictedNotifications} Conflict<br />
						${dailyReminderView.noInputNotifications} No input<br />
						${dailyReminderView.otherNotifications} Other
						--%>
				   </span>
					<div class="clear-all"></div></th>
			</c:forEach>
		</tr>
		<tr>			
			<c:forEach items="${resvReminder.resvReminders}" var="resvReminderMap">
				<c:set var="notificationList" value="${resvReminderMap.value}" />
				<td width="25%" style="padding: 0; vertical-align: top">
					<c:forEach var="notification" items="${notificationList}" varStatus="loop">
						<c:choose>
							<c:when test="${notification.notifyStatus == 1}">
								<c:set var="alarmStyle" value="yellow" />
							</c:when>
							<c:when test="${notification.notifyStatus == 2}">
								<c:set var="alarmStyle" value="blue" />
							</c:when>
							<c:when test="${notification.notifyStatus == 3}">
								<c:set var="alarmStyle" value="green" />
							</c:when>
							<c:when test="${notification.notifyStatus == 7}">
								<c:set var="alarmStyle" value="grey" />
							</c:when>
							<c:otherwise>
								<c:set var="alarmStyle" value="red" />
							</c:otherwise>
						</c:choose>
						<div class="${alarmStyle}">
							 
							<c:if test="${resvReminder.showTime}">
								<span class="attemptText">${notification.time}</span>
							</c:if>
														 
							<div class="txt-bold">${notification.campaignName}</div>
							<div class="txt-bold">
								<a href="javascript:void(0)" class="editStstus ex2trigger" id="notifyId=${notification.notifyId}&notifyPhoneStatus=${notification.notifyPhoneStatus}
								&notifyStatus=${notification.notifyStatus}&firstName=${notification.firstName}&lastName=${notification.lastName}"> 
									${notification.firstName} ${notification.lastName}
								</a>
							</div>
							<div class="clear-all"></div>
							<c:if test="${notification.notifyByPhone =='Y'}">
								<c:choose>
									<c:when test="${notification.notifyPhoneStatus == 1 || notification.notifyPhoneStatus == 0}">
										<img src="static/images/phone-orange.png" width="22"
											height="22" style="float: left;" alt="Phone Notification" title="Phone Notification" />
										<img src="static/images/pendingIcon.png" width="22"
											height="22" style="float: left;" alt="Pending" title="Pending" />
									</c:when>
									<c:when test="${notification.notifyPhoneStatus == 2}">
										<img src="static/images/phone-orange.png" width="22"
											height="22" style="float: left;" alt="Phone Notification" title="Phone Notification" />
										<img src="static/images/pendingIcon.png" width="22" 
											height="22" style="float: left;" alt="In Progress" title="In Progress" />
									</c:when>
									<c:when test="${notification.notifyPhoneStatus == 3}">
										<img src="static/images/phone-black.png" width="22"
											height="22" style="float: left;" alt="Phone Notification" title="Phone Notification" />
										<img src="static/images/human-grey.png" width="22"
											height="22" style="float: left;" alt="No Input"	title="No Input" />
									</c:when>
									<c:when test="${notification.notifyPhoneStatus == 4}">
										<img src="static/images/phone-black.png" width="22"
											height="22" style="float: left;" alt="Phone Notification" title="Phone Notification" />
										<img src="static/images/speaker-black.png" width="22"
											height="22" style="float: left;" alt="Ans Machine" title="Ans Machine" />
									</c:when>
									<c:when test="${notification.notifyPhoneStatus == 5}">
										<img src="static/images/phone-green.png" width="22"
											height="22" style="float: left;" alt="Phone Notification" title="Phone Notification" />
										<img src="static/images/human-green.png" width="22"
											height="22" style="float: left;" alt="Confirm" title="Confirm" />
									</c:when>
									<c:when test="${notification.notifyPhoneStatus == 6}">
										<img src="static/images/phone-red.png" width="22"
											height="22" style="float: left;" alt="Phone Notification" title="Phone Notification" />
										<img src="static/images/human-red.png" width="22"
											height="22" style="float: left;" alt="Conflict" title="Conflict" />
									</c:when>
									<c:when test="${notification.notifyPhoneStatus == 7}">
										<img src="static/images/phone-red.png" width="22"
											height="22" style="float: left;" alt="Phone Notification" title="Phone Notification" />
										<img src="static/images/x-red.png" width="22" height="22"
											style="float: left;" alt="Do Not Call" title="Do Not Call" />
									</c:when>
									<c:when test="${notification.notifyPhoneStatus == 8}">
										<img src="static/images/phone-red.png" width="22" height="22" style="float: left;" alt="Phone Notification"
											title="Phone Notification" />
										<img src="static/images/x-red.png" width="22" height="22" style="float: left;" alt="Terminated" title="Terminated" />
									</c:when>
									<c:otherwise>
										<img src="static/images/phone-red.png" width="22"
											height="22" style="float: left;" alt="Phone Notification" title="Phone Notification" />
										<img src="static/images/x-red.png" width="22" height="22"
											style="float: left;" alt="Suspended" title="Suspended" />
									</c:otherwise>
								</c:choose>
								<%-- <span class="attemptCount">${notification.noOfAttempts}</span> --%>
							</c:if>
							<c:if test="${notification.notifyBySMS == 'Y'}">
								<c:choose>
									<c:when
										test="${notification.notifySMSStatus == 1 || notification.notifySMSStatus == 0}">
										<img src="static/images/sms-orange.png" width="22"
											height="22" style="float: left;" alt="Text Pending" title="Text Pending" />
									</c:when>
									<c:when test="${notification.notifySMSStatus == 2}">
										<img src="static/images/sms-orange.png" width="22"
											height="22" style="float: left;" alt="Text In Progress" title="Text In Progress" />
									</c:when>
									<c:when test="${notification.notifySMSStatus == 3}">
										<img src="static/images/sms-black.png" width="22"
											height="22" style="float: left;" alt="Text Sent" title="Text Sent" />
									</c:when>
									<c:when test="${notification.notifySMSStatus == 4}">
										<img src="static/images/sms-green.png" width="22"
											height="22" style="float: left;" alt="Text Confirmed" title="Text Confirmed" />
									</c:when>
									<c:when test="${notification.notifySMSStatus == 5}">
										<img src="static/images/sms-red.png" width="22" height="22"
											style="float: left;" alt="Text Conflict" title="Text Conflict" />
									</c:when>
									<c:when test="${notification.notifySMSStatus == 6}">
										<img src="static/images/sms-red.png" width="22" height="22"
											style="float: left;" alt="Do Not Text" title="Do Not Text" />
									</c:when>
									<c:when test="${notification.notifySMSStatus == 7}">
										<img src="static/images/sms-red.png" width="22" height="22"
											style="float: left;" alt="Text Terminated" title="Text Terminated" />
									</c:when>
									<c:otherwise>
										<img src="static/images/sms-red.png" width="22" height="22"
											style="float: left;" alt="Text Suspended" title="Text Suspended" />
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${notification.notifyByEmail == 'Y'}">
								<c:choose>
									<c:when
										test="${notification.notifyEmailStatus == 1 || notification.notifyEmailStatus == 0}">
										<img src="static/images/email-orange.png" width="22"
											height="22" style="float: left;" alt="Email Pending" title="Email Pending" />
									</c:when>
									<c:when test="${notification.notifyEmailStatus == 2}">
										<img src="static/images/email-orange.png" width="22"
											height="22" style="float: left;" alt="Email In Progress" title="Email In Progress" />
									</c:when>
									<c:when test="${notification.notifyEmailStatus == 3}">
										<img src="static/images/email-black.png" width="22"
											height="22" style="float: left;" alt="Email Sent" title="Email Sent" />
									</c:when>
									<c:when test="${notification.notifyEmailStatus == 4}">
										<img src="static/images/email-green.png" width="22"
											height="22" style="float: left;" alt="Email Confirmed" title="Email Confirmed" />
									</c:when>
									<c:when test="${notification.notifyEmailStatus == 5}">
										<img src="static/images/email-red.png" width="22"
											height="22" style="float: left;" alt="Email Conflict" title="Email Conflict" />
									</c:when>
									<c:when test="${notification.notifyEmailStatus == 6}">
										<img src="static/images/email-red.png" width="22"
											height="22" style="float: left;" alt="Do Not Email" title="Do Not Email" />
									</c:when>
									<c:when test="${notification.notifyEmailStatus == 5}">
										<img src="static/images/email-red.png" width="22"
											height="22" style="float: left;" alt="Email Terminated" title="Email Terminated" />
									</c:when>
									<c:otherwise>
										<img src="static/images/email-red.png" width="22"
											height="22" style="float: left;" alt="Email Suspended" title="Email Suspended" />
									</c:otherwise>
								</c:choose>
							</c:if>
							 
							<c:set value="${fn:contains(resvReminder.remindStatusNeedAppt,notification.notifyPhoneStatus)}" var="viewCalLink" />
							<c:if test="${viewCalLink}"> 
								<a href="daily-view-calendar-for-setlect-cust.html?customerId=${notification.customerId}
								&customerName=${notification.firstName}${notification.lastName!='' ? ' ' : ''}${notification.lastName}">View Calendar</a>
							 </c:if>							 
							<div class="clear-all"></div>
						</div>
					</c:forEach>							
				</td>
			</c:forEach>
		</tr>
	</table>
	</c:if>	
	<c:if test="${resvReminder.errorStatus && !resvReminder.responseStatus }">
		<b>Error while retriving Reservation Remaiders response</b>
	</c:if>	
	<img src="static/images/notification_iconset3.gif" alt="Notification Icons" title="Notification Icons" /> 
</div>
<!-- Main tables ends -->
<!-- Body ends --> 

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<!--Pop up section ends -->