<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp; Event Date Times:&nbsp;&nbsp;&nbsp;

<c:if test="${fn:contains(dropDownType, 'CheckBox')}">
	 <c:if test="${resvReportCheckboxResponse.responseStatus && !resvReportCheckboxResponse.errorStatus}">
		<c:if test="${fn:length(resvReportCheckboxResponse.reportDateTimeCheckBoxList) gt 0}">
			<c:forEach var="reportDateTimeCheck"  items="${resvReportCheckboxResponse.reportDateTimeCheckBoxList}" varStatus="theCount">
				<input name="${dropDownId}"  ${theCount.count==1 ? 'checked' : ''} 
				class="noWidth" type="checkbox" value="${reportDateTimeCheck.eventDateTimeId}"/>${reportDateTimeCheck.dateTime}
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(resvReportCheckboxResponse.reportDateTimeCheckBoxList) le 0}">
			<b style="color:red;"> There are no event dates configured for the date range you have selected. Please try again.</b>
		</c:if>
	</c:if>
	<c:if test="${resvReportCheckboxResponse.errorStatus && !resvReportCheckboxResponse.responseStatus}">
		<b style="color:red;"> Error while retriving Event date times </b>
	</c:if>
</c:if>