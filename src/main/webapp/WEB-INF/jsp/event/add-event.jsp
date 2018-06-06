<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<title>Add Event</title>

<script type="text/javascript" src="static/js/validation/event/events.js?version=1.4"></script>

	<form:form name="eventForm" id="eventForm" commandName="event" action="save-event.html" method="post" >
		<h1>&nbsp; &nbsp;Add Event</h1>
		<a href="#" class="jqmClose close" title="Close">Close</a>
		<div class="popup-content">

			<!--Event Details starts -->
			<div class="popup-box">
				<div class="">
					<div class="required-indicator">
						<span class="required">*</span>indicates required field
					</div>
					<div class="error" id="errors"></div>
					<div>
						<dl>
							<dt> <span class="required">*</span>Locations </dt>
							<dd>
								<%--
								<form:select path="locationId" id="locationId" class="leftFloat marginLTen paddingAll selectChangeClass">
									<form:options items="${locationListResponse.locationList}" itemLabel="locationName" itemValue="locationId"></form:options>
								</form:select>
								--%>
								<form:checkboxes  items="${locationListResponse.locationList}" path="selectedLocIdsList"  delimiter="&nbsp;"
									itemLabel="locationName" itemValue="locationId"  class="noWidth" />	
							</dd>
						</dl>
						<dl class="clear-all">
							<dt><span class="required">*</span>Event Name</dt>
							<dd>
								<form:input path="eventName" id="eventName" maxlength="100" />
							</dd>
						</dl>
						<dl>
							<dt><span class="required">*</span>Duration(in Mins)</dt>
							<dd>
								<form:input path="duration" id="duration" maxlength="200" />
							</dd>
						</dl>
					</div>
					<div class="clear-all"></div>
				</div>
				<!-- Event Details ends -->
			</div>
			<div class="popup-button">				
				<a href="#" class="btn jqmClose">Cancel</a>
				&nbsp; 
				<input name="btnSave" type="button" class="btn" value="Save" id="addEventSaveBtn">
			</div>
			<!-- pop up ends -->
		</div>
	</form:form>