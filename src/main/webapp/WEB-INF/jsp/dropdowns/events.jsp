<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${fn:contains(eventDropDownType, 'Select')}">
	<select id="${dropDownId}" name="${dropDownId}">
		<c:if test="${fn:length(eventListResponse.eventList) gt 1}">
			<option value="-1">Select Event</option>
		</c:if>
		<c:forEach var="event"  items="${eventListResponse.eventList}">
			<option value="${event.eventId}">${event.eventName}</option>
		</c:forEach>
	</select>
</c:if>

<c:if test="${fn:contains(eventDropDownType, 'All')}">
	<c:if test="${!fn:contains(page, 'REPORT')}">
		<select id="${dropDownId}" name="${dropDownId}">	
			<option value="-1">All Events</option>
			<c:forEach var="event"  items="${eventListResponse.eventList}">
				<option value="${event.eventId}">${event.eventName}</option>
			</c:forEach>
		</select>
	</c:if>
	<c:if test="${fn:contains(page, 'REPORT')}">
		<select id="${dropDownId}" name="${dropDownId}" onchange="loadEventDateTimes(false);">		
			<option value="-2">Select Event</option>		
			<option value="-1">All Events</option>
			<c:forEach var="event"  items="${eventListResponse.eventList}">
				<option value="${event.eventId}">${event.eventName}</option>
			</c:forEach>
		</select>
	</c:if>
</c:if>