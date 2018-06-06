<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="static/js/validation/eventdatetime/eventDateTimeFormValidation.js?version=1.0"></script>

<h1>&nbsp; &nbsp;Edit Event Seats</h1>
<a href="javascript:" class="jqmClose close" title="Close">Close</a>
<div class="popup-content">
	<div class="error" id="noOfSeatsError"></div>
<!--EventDateTime Details starts -->
<form:form action="updateEventSeats.html" method="POST"  id="updateEventSeatForm" modelAttribute="eventDateTime" 
	onsubmit="return validateEditEventSeatsForm()">   
<div class="popup-box">
  <div class="">
    <div class="required-indicator"> <span class="required">*</span>indicates required field </div>
    <div class="error" id="errors"></div>
    <div class="popup-box-content">
		  <dl  class="normal">
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
			<dt class="clear-all"> Date </dt>
			<dd>
			  ${eventDateTime.date}
			  <form:hidden path="date" id="date"/> <%-- <a href="#">Add More</a> --%>
			</dd>
			<dt> Time </dt>
			<dd>
				${eventDateTime.time}
			    <form:hidden path="time" id="time"/> 
			</dd>
			<dt class="clear-all"> <span class="required">*</span>No. of Seats </dt>
			<dd>
			   <form:input type="text" path="noOfSeats" id="noOfSeats"/> 
			</dd>			
		  </dl>		 
    </div>
    <div class="clear-all"></div>
  </div>
  <!-- EventDateTime Details ends --> 
</div>
<div class="popup-button">
	<a href="javascript:" class="btn jqmClose">Cancel</a>
	<input name="btnSave" type="submit" class="btn" value="Save" id="btnupdateEventSeats"/>
</div>
<!-- pop up ends -->
 </form:form>