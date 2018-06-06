<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>   

<link rel="stylesheet" type="text/css" media="all" href="static/css/jqModal.css" />

<script type="text/javascript">
	$('#resvBookingMsg').html("");
</script>
<c:if test="${resvBookingSuccess=='SUCCESS'}" >
	<div style="color:green;text-align:center;"><b> ${bookingResvResponse}</b></div>
</c:if>
<c:if test="${resvBookingSuccess!='SUCCESS'}" >
	<div style="color:red;text-align:center;"><b> ${bookingResvResponse}</b></div>
</c:if>
<input type="hidden" id="resvBookingSuccess" value="${resvBookingSuccess}"/>

<c:if test="${resvBookingSuccess=='SUCCESS'}" >
 <div class="popup-content-big" id="popup-content-no-height"> 
	 <!--Booked Reservation Response starts -->
    <div class="popup-box">
      <div class="popup-box-head">
        <h2>Booked Reservation Response</h2>
		<div class="clear-all"></div>
      </div>
      <div class="popup-box-content" id="CustomerInfo">
        	<dl class="normal">
				  <c:set var="displayedDataCount" value="0"/>

				  <dt >${confirmResvResponse.confirmationNoLabel}</dt>
				  <dd>${confirmResvResponse.confNumber}</dd>
				  <dt >${confirmResvResponse.nameLabel}</dt>
				  <dd>${confirmResvResponse.firstName} ${confirmResvResponse.lastName}</dd>
				
				  <c:if test="${(fn:contains(confirmResvResponse.displayCompany,'Y') || fn:contains(confirmResvResponse.displayCompany,'y'))}">
					<dt class="${displayedDataCount%2==0 ? 'clear-all' : '' }">${confirmResvResponse.displayCompanyLabel}</dt>
					<dd>${confirmResvResponse.companyName}</dd>
					<c:set var="displayedDataCount" value="${displayedDataCount+1}"/>
				  </c:if>

				  <c:if test="${(fn:contains(confirmResvResponse.displayProcedure,'Y') || fn:contains(confirmResvResponse.displayProcedure,'y'))}">
					<dt class="${displayedDataCount%2==0 ? 'clear-all' : '' }">${confirmResvResponse.displayProcedureLabel}</dt>
					<dd>${confirmResvResponse.procedure}</dd>
					<c:set var="displayedDataCount" value="${displayedDataCount+1}"/>
				  </c:if>

				  <c:if test="${(fn:contains(confirmResvResponse.displayProcedure,'Y') || fn:contains(confirmResvResponse.displayProcedure,'y'))}">
					<dt class="${displayedDataCount%2==0 ? 'clear-all' : '' }">${confirmResvResponse.displayProcedureLabel}</dt>
					<dd>${confirmResvResponse.procedure}</dd>
					<c:set var="displayedDataCount" value="${displayedDataCount+1}"/>
				  </c:if>

				  <c:if test="${(fn:contains(confirmResvResponse.displayLocation,'Y') || fn:contains(confirmResvResponse.displayLocation,'y'))}">
					<dt class="${displayedDataCount%2==0 ? 'clear-all' : '' }">${confirmResvResponse.displayLocationLabel}</dt>
					<dd>${confirmResvResponse.location}</dd>
					<c:set var="displayedDataCount" value="${displayedDataCount+1}"/>
				  </c:if>

				  <c:if test="${(fn:contains(confirmResvResponse.displayEvent,'Y') || fn:contains(confirmResvResponse.displayEvent,'y'))}">
					<dt class="${displayedDataCount%2==0 ? 'clear-all' : '' }">${confirmResvResponse.displayEventLabel}</dt>
					<dd>${confirmResvResponse.event}</dd>
					<c:set var="displayedDataCount" value="${displayedDataCount+1}"/>
				  </c:if>

				  <c:if test="${(fn:contains(confirmResvResponse.displaySeat,'Y') || fn:contains(confirmResvResponse.displaySeat,'y'))}">
					<dt class="${displayedDataCount%2==0 ? 'clear-all' : '' }">${confirmResvResponse.displaySeatLabel}</dt>
					<dd>${confirmResvResponse.seatNumber}</dd>
					<c:set var="displayedDataCount" value="${displayedDataCount+1}"/>
				  </c:if>

				  <dt class="${displayedDataCount%2==0 ? 'clear-all' : '' }"> Resv ${confirmResvResponse.dateLabel} & ${confirmResvResponse.timeLabel}</dt>
				  <dd class="noWidth">${confirmResvResponse.reservationDate} @ ${confirmResvResponse.reservationTime}</dd>
			  </dl>
      </div>
      <div class="clear-all"></div>
    </div>
</div>
  <!--Booked Reservation Response ends --> 
</c:if>