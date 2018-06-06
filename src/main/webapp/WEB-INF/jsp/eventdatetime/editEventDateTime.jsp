<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="static/js/validation/eventdatetime/eventDateTimeFormValidation.js?version=1.0"></script>
<script type="text/javascript">	
	$(document).ready(function() {	
		var timeoutID;

		g_calendarObject = new JsDatePick({
			useMode:2,
			target:"date",
			dateFormat : "%M/%d/%Y"
		});	
		
		timeoutID = setTimeout(function(){ 
				g_calendarObject.closeCalendar(); 
				clearTimer();
		},1);		
		
		g_calendarObject.setOnSelectedDelegate(function(){
			g_calendarObject.closeCalendar();
			var obj = g_calendarObject.getSelectedDay();
			var day   = obj.day;
			var month   = obj.month;
			if(day<10){
				day = "0"+day;
			}
			if(month<10){
				month = "0"+month;
			}
			$("#date").val(month + "/" +day+ "/" + obj.year);			
		});

		function clearTimer() {
			window.clearTimeout(timeoutID);
		}
	});
</script>

<h1>&nbsp; &nbsp;Edit Event Date Time</h1>
<a href="javascript:" class="jqmClose close" title="Close">Close</a>
<div class="popup-content">
	<div class="error" id="dateError"></div>
	<div class="error" id="noOfSeatsError"></div>
<!--EventDateTime Details starts -->
<form:form action="updateEventDateTime.html" method="POST"  id="updateEventDateTimeForm" modelAttribute="eventDateTime" onsubmit="return validateEditEventDateTimeForm()">   
<div class="popup-box">
  <div class="">
    <div class="required-indicator"> <span class="required">*</span>indicates required field </div>
	<div class="error" id="noOfNotifiedResvMsg"> 
		<c:if test="${noOfNotifiedReservations gt 0}">
			Already ${noOfNotifiedReservations} notifications went out for this ${eventDateTime.date} ${eventDateTime.time}. 
			Therefore, if you change the date, you will have to contact them manually for their appointment reminders.
		</c:if>
	</div>
    <div class="error" id="errors"></div>
    <div class="popup-box-content">
		  <dl  class="normal">
			<form:hidden path="eventDateTimeId" id="eventDateTimeId"/> 
			<form:hidden path="eventDateTimeId" id="eventDateTimeId"/> 
			<dt> Location </dt>
			<dd>
				<form:hidden path="locationId" id="locationId"/> 
				${eventDateTime.locationName}
			</dd>
			<dt> Event </dt>
			<dd>
				<form:hidden path="eventId" id="eventId"/> 
				${eventDateTime.eventName}
			</dd>
			<dt> <span class="required">*</span> Date </dt>
			<dd>
			  <form:input path="date" id="date"/> <%-- <a href="#">Add More</a> --%>
			</dd>
			<dt> <span class="required">*</span>Time </dt>
			<dd>
				<form:select class="noWidth" path="timeHr" id="timeHr">
					<form:option selected="selected" value="07">7</form:option>
					<form:option value="08">8</form:option>
					<form:option value="09">9</form:option>
					<form:option value="10">10</form:option>
					<form:option value="11">11</form:option>
					<form:option value="12">12</form:option>
					<form:option value="01">1</form:option>
					<form:option value="02">2</form:option>
					<form:option value="03">3</form:option>
					<form:option value="04">4</form:option>
					<form:option value="05">5</form:option>
					<form:option value="06">6</form:option>
				</form:select>
				 
				<form:select class="noWidth" path="timeMin">
					<form:option selected="selected" value="00">00</form:option>
					<form:option value="15">15</form:option>
					<form:option value="30">30</form:option>
					<form:option value="45">45</form:option>
				</form:select>
				 
				<form:select class="noWidth" path="timeAmPm">
					<form:option selected="selected" value="AM">AM</form:option>
					<form:option value="PM">PM</form:option>
				</form:select>
			</dd>
			<dt>  <span class="required">*</span>No. of Seats </dt>
			<dd>
			   <form:hidden path="noOfSeats" id="noOfSeats"/> 
				${eventDateTime.noOfSeats}
			</dd>			
		  </dl>		 
    </div>
    <div class="clear-all"></div>
  </div>
  <!-- EventDateTime Details ends --> 
</div>
<div class="popup-button">
	<a href="javascript:" class="btn jqmClose">Cancel</a>
	<input name="btnSave" type="submit" class="btn" value="Save" id="btnAddEventDateTime"/>
</div>
<!-- pop up ends -->
 </form:form>