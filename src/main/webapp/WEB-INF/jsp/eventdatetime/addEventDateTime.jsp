<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="static/js/validation/eventdatetime/eventDateTimeFormValidation.js?version=1.0"></script>
<script type="text/javascript">	
	$(document).ready(function() {
		new JsDatePick({
			useMode:2,
			target:"date",
			dateFormat : "%M/%d/%Y"
		});
	});	
</script>

<h1>&nbsp; &nbsp;Add Event Date Time</h1>
<a href="javascript:" class="jqmClose close" title="Close">Close</a>
<div class="popup-content">

	<div class="error" id="locationError"></div>
	<div class="error" id="eventError"></div>
	<div class="error" id="dateError"></div>
	<div class="error" id="noOfSeatsError"></div>

<!--EventDateTime Details starts -->
<form:form action="saveEventDateTime.html" method="POST" id="addEventDateTimeForm" modelAttribute="eventDateTime" onsubmit="return validateAddEventDateTimeForm()">   
<div class="popup-box">
  <div class="">
    <div class="required-indicator"> <span class="required">*</span>indicates required field </div>
    <div class="error" id="errors"></div>
    <div>
		  <dl>
			<dt> <span class="required">*</span>Location </dt>
			<dd>
				<form:select path="locationId" id="locationId" class="leftFloat marginLTen paddingAll selectChangeClass" onchange="loadEvents();">
					<form:option value="-1" label="Select  Location"/> 
					<form:options items="${locationListResponse.locationList}" itemLabel="locationName" itemValue="locationId"></form:options>
				</form:select>
			</dd>
			<dt> <span class="required">*</span>Event </dt>
			<dd class="noHeight" style="height:auto" id="eventIdDD">
				<form:select path="eventId" id="eventId" class="leftFloat marginLTen paddingAll selectChangeClass">
					<form:option value="-1" label="Select  Event"/> 
				</form:select>
			</dd>
			<dt><span class="required">*</span> Date </dt>
			<dd>
			  <form:input path="date" id="date"/> <%-- <a href="#">Add More</a> --%>
			</dd>
			<dt> <span class="required">*</span>Time </dt>
			<dd>
				<form:select class="noWidth" path="timeHr">
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
				&nbsp;
				<form:select class="noWidth" path="timeMin">
					<form:option selected="selected" value="00">00</form:option>
					<form:option value="15">15</form:option>
					<form:option value="30">30</form:option>
					<form:option value="45">45</form:option>
				</form:select>
				&nbsp;
				<form:select class="noWidth" path="timeAmPm">
					<form:option selected="selected" value="AM">AM</form:option>
					<form:option value="PM">PM</form:option>
				</form:select>
			</dd>
			<dt> <span class="required">*</span>No. of Seats </dt>
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
	<input name="btnSave" type="submit" class="btn" value="Save" id="btnAddEventDateTime"/>
</div>
<!-- pop up ends -->
 </form:form>