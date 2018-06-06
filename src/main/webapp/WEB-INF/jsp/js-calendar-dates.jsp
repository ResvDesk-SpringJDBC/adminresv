<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<input type="hidden" id="jsCalFirstDate" value="${jsCalFirstDate}"/>

<c:if test="${jsCalDateListResponse.responseStatus && !jsCalDateListResponse.errorStatus}">
	<c:forEach var="dateData"  items="${jsCalDateListResponse.dateMap}">
		<input type="hidden" id="${dateData.key}" value="${dateData.key}_${dateData.value}"/>
	</c:forEach>
	<script type="text/javascript">
		var jsCalendarMinDate = '${jsCalDateListResponse.minDate}';
		var jsCalendarMaxDate = '${jsCalDateListResponse.maxDate}';
		$("#jsCalendarMinDate").val(jsCalendarMinDate);
		$("#jsCalendarMaxDate").val(jsCalendarMaxDate);
	</script>
</c:if>