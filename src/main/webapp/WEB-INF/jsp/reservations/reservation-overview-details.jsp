<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h1>&nbsp; &nbsp; Reservation Overview Details</h1>
<a href="javascript:" class="jqmClose close" title="Close">Close</a>
<div class="popup-content-big">  
	<b>Location :</b> ${locationName}    &nbsp;&nbsp;&nbsp; <b>Event :</b> ${eventName} &nbsp;&nbsp;&nbsp; <b>Date Time :</b> ${eventDateTime} <br/> 
	<%-- <b>Total Seats :</b> ${totalSeats} &nbsp; <b>Booked Seats :</b> ${bookedSeats}  <br/>  --%>
  <div class="popup-box">		
    <div class="popup-box-content">
		<c:if test="${overviewDetailsResponse.responseStatus && !overviewDetailsResponse.errorStatus}">
		  <table id="appontment" class="main-table">
			  <thead>
				<tr>
				  <th> SeatNumber</th>
				  <th> Status</th>
				  <th> Customer Name</th>
				</tr>
			  </thead>
			  <tbody>
			   <c:forEach var="overviewDetails" items="${overviewDetailsResponse.calendarOverviewDetailsList}" varStatus="count">
					<tr class="${count.index%2==1 ? 'altColor' : ''}">
					  <td align="center">${overviewDetails.displaySeatNumber}</td>
					  <td align="center">${overviewDetails.availableMsg}</td>
					  <td align="center">
						  <c:if test="${fn:contains(overviewDetails.booked, 'Y')}">
							<b>FirstName : ${overviewDetails.firstName} </b>; <b>LastName : ${overviewDetails.lastName}</b>;
							Account Number : ${overviewDetails.accountNumber};
							Contact Phone : ${overviewDetails.contactPhone}							
						  </c:if>
						  <c:if test="${fn:contains(overviewDetails.booked, 'N')}">
							<div style="text-align:center"> -  </div>
						  </c:if>
					   </td>
					</tr>
				</c:forEach>
			  </tbody>
			</table> 
		 </c:if>
		 <c:if test="${!overviewDetailsResponse.responseStatus && overviewDetailsResponse.errorStatus}">
			<div style="color:red"><b>Error while retriving data</b></div>
		 </c:if>
    </div>
    <div class="clear-all"></div>
  </div>
</div>
<div class="popup-button">
	<a href="javascript:" class="btn jqmClose"> &nbsp;&nbsp; Ok &nbsp;&nbsp; </a>
</div>
<!-- pop up ends -->