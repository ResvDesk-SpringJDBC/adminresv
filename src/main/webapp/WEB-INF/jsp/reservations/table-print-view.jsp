<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="static/js/validation/reservations/tablePrintViewFormValidation.js?version=1.0"></script>
<script type="text/javascript" src="static/js/validation/JSCalenderHelper.js?version=1.3"></script>

<!--
<script type="text/javascript">
	$(document).ready(function() {
		new JsDatePick({
			useMode : 2,
			target : "jsCalendarDate",
			dateFormat : "%M/%d/%Y"
		});
	});
</script>
-->
<script type="text/javascript">
	$(document).ready(function() {
		prepareDatesMapAfterAvailDatesLoaded();
		initiateJSCalender("jsCalendarDate");
	});
</script>

<title>Reservation &gt; Table View</title>

<div class="error" id="locationIdError"></div>
<div class="error" id="eventIdError"></div>		
<div class="error" id="dateError"></div>

<!-- Body starts -->
  <form:form action="search-table-print-view.html" method="POST" id="calendarForm" modelAttribute="tablePrintViewRequest"> 

    <div class="dailyCal_head">
	<form:hidden  path="changedParameter" id="changedParameter"/>
      <div class="cal_head-new">Reservation &gt; Table View</div>
      <div class="float-left" style="margin-left:10px">
        <input name="btnPrint" type="submit" class="btn printbtn" value="Print" onclick="window.print(); return false;" style="height: 28px; padding: 0 20px;">
      </div>
      <div class="clear-all"></div>
    </div>
    <!--Search bar starts -->
    <div class="search-bar">
      <div class="float-left" style="z-index:9999;"> Locations:
         <form:select path="locationId" id="locationId" class="leftFloat marginLTen paddingAll selectChangeClass">
			<form:options items="${locationListResponse.locationList}" itemLabel="locationName" itemValue="locationId"></form:options>
		 </form:select>
      </div>
      <div class="float-left"> Events: <span>
		<form:checkboxes  items="${eventListResponse.eventList}" path="selectedEventIdsList"  delimiter="&nbsp;"
				itemLabel="eventName" itemValue="eventId"  class="noWidth" />	
      </div>
      <div class="float-right">
        <input name="btnSearch" type="button" class="btn" value="View Reservation" onclick="viewReservationDetails();">
      </div>
      <div class="clear-all"></div>
    </div>
    <!--Search bar starts --> 
    
    <!--Options starts -->
    <div class="options">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
			   <td class="txt-bold" width="200" valign="top">Reservation Details</td>
			   <td style="text-align: center;" class="font16 txt-bold">
					<a href="javascript:void(0)" onclick="previousDateLinkClicked();" style="text-decoration: none;"> 
						<img src="static/images/previous.png" alt="Previous" title="Previous" style="vertical-align: bottom"/> 
						<form:hidden path="previousAvailableDate" id="previousAvailableDate"/>
					</a> 
					${tablePrintViewRequest.selectedDateDisplayFormat}
					<form:hidden path="selectedDate" id="selectedDate"/>
					<a href="javascript:void(0)" onclick="nextDateLinkClicked();" style="text-decoration: none;"> 
						<img src="static/images/next.png" alt="Next" title="Next" style="vertical-align: bottom"/>
						<form:hidden path="nextAvailableDate" id="nextAvailableDate"/>
					</a>
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
	  <c:if test="${tablePrintViewResponse.responseStatus && !tablePrintViewResponse.errorStatus}">
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="main-table">
			  <tbody>
			    <c:set var = "titles" value="${fn:split(tablePrintViewResponse.titles, ',')}"/>
			    <c:set var = "javaRefs" value="${fn:split(tablePrintViewResponse.javaRefs, ',')}"/>
			    <c:set var = "hides" value="${fn:split(tablePrintViewResponse.hides, ',')}"/>
			    
				<c:forEach items="${tablePrintViewResponse.tablePrintView}" var="tablePrintViewObj">
					<c:set var = "tablePrintView" value="${tablePrintViewObj.key}"/>
					<c:set var = "seatViewList" value="${tablePrintViewObj.value}"/>
					<tr>
					  <th style="background: #fff;" colspan="3">Time: ${tablePrintView.time} - ${tablePrintView.totalSeats} Total Seats, ${tablePrintView.bookedSeats} Booked Reservations</th>
					</tr>
					<tr>
					  <th width="8%">Seat #</th>
					  <%--<th width="12%">Notification Status</th> --%>
					  <th width="80%">Reservation Details</th>
					</tr>
					<c:forEach var="seatView" items="${seatViewList}" varStatus="index">	
						<tr class="${index.index %2 ==0 ? 'apptTableView' : 'apptTableViewAltColor'}">
						  <td>${seatView.seatNumber} </td>
						  <%--<td>Pending</td> --%>
						  <td>
							<c:if test="${fn:contains(seatView.booked, 'Y')}">
								<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
									<c:if test="${fn:contains(hides[i], 'N')}">
										<c:if test="${fn:contains(javaRefs[i], 'firstName') || fn:contains(javaRefs[i], 'lastName')}">
											<b> ${titles[i]} : ${seatView.reservationDetails[javaRefs[i]]} </b>
										</c:if>
										<c:if test="${! fn:contains(javaRefs[i], 'firstName') && !fn:contains(javaRefs[i], 'lastName')}">
											${titles[i]} : ${seatView.reservationDetails[javaRefs[i]]} 
										</c:if>
										<c:if test="${fn:length(javaRefs)-1 gt i}">
											;
										</c:if>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${fn:contains(seatView.booked, 'N')}">
								<c:if test="${fn:contains(seatView.reserved, 'Y')}">
									Reserved
								</c:if>
								<c:if test="${fn:contains(seatView.reserved, 'N')}">
									Open
								</c:if>
							</c:if>
						  </td>
						</tr>
					</c:forEach>
				 </c:forEach>
			  </tbody>
			</table>
	  </c:if>	
	  <c:if test="${tablePrintViewResponse.errorStatus && !tablePrintViewResponse.responseStatus }">
		<b>Error while retriving Table print view results!</b>
	  </c:if>
    <!-- Main tables ends -->
<!-- Body ends --> 