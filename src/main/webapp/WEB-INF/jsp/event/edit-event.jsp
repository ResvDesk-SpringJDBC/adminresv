<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<title>Edit Event</title>

<script type="text/javascript" src="static/js/validation/event/events.js?version=1.4"></script>

	<form:form name="eventForm" id="eventForm" commandName="event" action="update-event.html" method="post">	
	
		<h1>&nbsp; &nbsp;Edit Event</h1>
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
							<dt> <span class="required">*</span>Locations</dt>
							<dd>
								<%--
								<form:checkboxes  items="${locationListResponse.locationList}" path="selectedLocIdsList"  delimiter="&nbsp;"
									itemLabel="locationName" itemValue="locationId"  class="noWidth" 
									disabled="${fn:contains(event.locationIds, locationId)? 'true':''}" />	
								--%>
								<c:forEach items="${locationListResponse.locationList}" var="location" varStatus="loopStatus">
								  <c:if test="${fn:contains(event.locationIds,location.locationId)}">
									  <form:checkbox path="selectedLocIdsList" value="${location.locationId}" class="noWidth"
										disabled="true"/>
										<c:out value="${location.locationName}"/>&nbsp;
								  </c:if> 
								</c:forEach>
								<c:forEach items="${locationListResponse.locationList}" var="location" varStatus="loopStatus">
								  <c:if test="${!fn:contains(event.locationIds,location.locationId)}">
									  <form:checkbox path="updateSelectedLocIdsList" value="${location.locationId}" class="noWidth"/>
										<c:out value="${location.locationName}"/>&nbsp;
								  </c:if> 
								</c:forEach>
							</dd>
						</dl>
						<div class="clear-all"></div>
						<dl>
							<dt><span class="required">*</span>Event Name</dt>
							<dd>
								<form:hidden path="eventId"/>
								<form:hidden path="locationId"/>
								<form:input path="eventName" id="eventName" maxlength="200" />
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
				<input name="btnSave" type="button" class="btn" value="Update" id="editEventSaveBtn">
			</div>
			<!-- pop up ends -->
		</div>
	</form:form>