<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<title>Reservation Details</title>

<script type="text/javascript" src="static/js/validation/reservations/custResvDetailsWindowFormHelper.js?version=1.0"></script>

<script type="text/javascript">
	var contextPath =  "${pageContext.request.contextPath}";

	$(document).ready(function() {		
		$('.editCustDetails').click(function(){		
			$('#doneBtn').hide();
			var customerId = $("#editCustomerId").val();
			var url = 'edit-customer-details.html?customerId='+customerId;
			$("#dataId").load(url,function(response,status,xhr){
			});
		});
	});

</script>

<!-- pop up starts -->
  <h1>&nbsp;&nbsp;Reservation Details</h1>
  <a href="#" class="jqmClose close" title="Close"  id="popupClose">Close</a>
	
	<c:set value="${fn:contains(homeBean.adminInfoTO.accessLevel,'Read Only')}"  var="result" />

   <div class="popup-content-big" > 

    <div id="dataId" > 
   <!--Customer Info starts -->
    <div class="popup-box">
	   <div class="popup-box-head">
		  <h2>Customer Info</h2>
		  <img src="static/images/minus.png" onClick="switchMainCustomerInfo()" ondrag="return false" id="expnClsp2"><div class="clear-all"></div>
	    </div>
		<div  style="text-align:center;"  id="customerUpdateResponseId"/>
        <div class="" id="customerInfo">
             <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
				<c:if test="${loginInfoResponse.responseStatus && !loginInfoResponse.errorStatus}">
					<tr>
						<c:forEach var="loginFieldDetails" items="${loginInfoResponse.loginFieldList}">
							<th>${loginFieldDetails.displayTitle}</th>
						</c:forEach>
						<c:if test="${!result}">
							<th >Edit</th>
						</c:if>
					 </tr>	  
					 <tr>
						<input type="hidden" id="editCustomerId" value="${customer.customerId}"/>
						
						<c:forEach var="loginFieldDetails" items="${loginInfoResponse.loginFieldList}">
							<td>
								<c:choose>						
									<c:when test="${not empty customer[loginFieldDetails.javaRef]}" >
											${customer[loginFieldDetails.javaRef]}
									</c:when>
									<c:when test="${empty customer[loginFieldDetails.javaRef]}" >
											-
									</c:when>							
								</c:choose>
							</td>
						</c:forEach>
						
						<c:if test="${!result}">
							<td class="center-align action-icons">
								<a href="javascript:void(0)"  class="ex7trigger" id="${customer.customerId}">
									<div id="${customer.customerId}" class="editCustDetails">Edit</div>
								</a>
							</td>
						</c:if>
					</tr>
				</c:if>	
			    <c:if test="${loginInfoResponse.errorStatus && !loginInfoResponse.responseStatus }">
					<tr><td>Error while getting customer details!</td></tr>
				</c:if>
		
	  </table>
      </div>
      <div class="clear-all"></div>
    </div>
    <!-- Customer Info ends -->
    
   <!--Upcoming Reservation starts -->
    <div class="popup-box">
      <div class="popup-box-head">
        <h2>Upcoming Reservations (Top 5)</h2>
        <img src="static/images/plus.png" onClick="switchMainUpcomintReservation()" ondrag="return false" id="expnClsp1"><div class="clear-all"></div>
      </div>
	  <div  style="text-align:center;"  id="upcomingResvResponseDisplyDivId"/>
      <div class="popup-box-content" id="UpcomintReservation" style="display:none">
		   <div id = "upcomingResvDivId">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
				  <tr>
				    <th width="5%">Conf No.</th>
					<th width="20%">Resv Date</th>
					<th width="15%">Location</th>
					<th width="25%">Event</th>
					<th width="20%">Seat</th>
					<c:if test="${!result}">
						<th width="15%">Cancel</th>
					</c:if>
					<%--
					 <c:if test="${fn:contains(homeBean.resvSysConfig.displayComments,'Y') ||	
						fn:contains(homeBean.resvSysConfig.displayComments,'y')}">
						<th width="15%">Comments</th>
					</c:if>
					--%>
				  </tr>		
				  <c:if test="${futureResvResponse.responseStatus && !futureResvResponse.errorStatus}">
					 <c:forEach var="resvDetails" items="${futureResvResponse.resvDetails}">
					   <tr>
							<td>${resvDetails.confNumber}</td>
							<td>${resvDetails.eventDateTime}</td>
							<td>${resvDetails.locationName}</td>
							<td>${resvDetails.eventName}</td>
							<td>${resvDetails.displaySeatNumber}</td>
							<c:if test="${!result}">
								<td>
									<a href="javascript:void(0)" class="cancleUpcomingResv"  id="${resvDetails.scheduleId}">Cancel</a>
								</td>
							</c:if>
							<%--
							 <c:if test="${fn:contains(homeBean.resvSysConfig.displayComments,'Y') ||	fn:contains(homeBean.resvSysConfig.displayComments,'y')}">
								<td>
									<a href="javascript:void(0)" class="editApptComment"  id="${resvDetails.scheduleId}">Edit</a>
								</td>
							</c:if>
							--%>
						  </tr>			 
						</c:forEach>
					</c:if>
					<c:if test="${futureResvResponse.errorStatus && !futureResvResponse.responseStatus }">
						<tr><td colspan="6">Error while getting Upcoming Reservations!</td></tr>
					</c:if>
				</table>			
			 </div>
      </div>
    </div>
    <!--Upcoming Reservation ends --> 

	<input type="hidden" id="upcomintReservationSwitched" value="No"/>
	<c:if test="${futureResvResponse.responseStatus && !futureResvResponse.errorStatus}">		
		<c:if test="${fn:length(futureResvResponse.resvDetails) gt 0}">
		   <script type="text/javascript">
				switchMainUpcomintReservation();
				$("#upcomintReservationSwitched").val("Yes");
			</script>
		</c:if>
	</c:if>	

	 <!--Past Reservations  starts -->
    <div class="popup-box">
      <div class="popup-box-head">
        <h2>Past Reservations (Top 5)</h2>
        <img src="static/images/plus.png" onClick="switchMainPastReservation()" ondrag="return false" id="expnClsp3"><div class="clear-all"></div>
      </div>
      <div class="popup-box-content" id="PastReservation" style="display:none">
			<div id = "pastApptDivId">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
				  <tr>
					<th width="5%">Conf No.</th>
					<th width="20%">Resv Date</th>
					<th width="15%">Location</th>
					<th width="25%">Event</th>
					<th width="20%">Seat</th>
					<c:if test="${fn:contains(homeBean.resvSysConfig.displayComments,'Y') ||	
						fn:contains(homeBean.resvSysConfig.displayComments,'y')}">
						<th width="15%">Comments</th>
					</c:if>
				  </tr>
					  <c:if test="${pastReservResponse.responseStatus && !pastReservResponse.errorStatus}">
						 <c:forEach var="resvDetails" items="${pastReservResponse.resvDetails}">
						   <tr>
							<td>${resvDetails.confNumber}</td>
							<td>${resvDetails.eventDateTime}</td>
							<td>${resvDetails.locationName}</td>
							<td>${resvDetails.eventName}</td>
							<td>${resvDetails.displaySeatNumber}</td>
							 <c:if test="${fn:contains(homeBean.resvSysConfig.displayComments,'Y') ||	fn:contains(homeBean.resvSysConfig.displayComments,'y')}">
								<td>
									<a href="javascript:void(0)" class="editApptComment"  id="${resvDetails.scheduleId}">Edit</a>
								</td>
							</c:if>
						  </tr>			 
						</c:forEach>
					</c:if>
					<c:if test="${pastReservResponse.errorStatus && !pastReservResponse.responseStatus }">
						<tr><td colspan="6">Error while getting Past Reservations!</td></tr>
					</c:if>
				</table>
			 </div>
      </div>
    </div>

    <!--Past Reservations ends --> 
	<c:if test="${pastReservResponse.responseStatus && !pastReservResponse.errorStatus}">		
		<c:if test="${fn:length(pastReservResponse.resvDetails) gt 0}">
		   <script type="text/javascript">
				var upcomintReservationSwitched = $("#upcomintReservationSwitched").val();
				if(upcomintReservationSwitched=="No"){
					switchMainPastReservation();
				}
			</script>
		</c:if>
	</c:if>	

  </div>
  </div>
    <div id="resvMsg"> </div>
  <div class="popup-button">
		<input name="btnSave" type="button" class="btn" value="Cancel" id="cancelBtn" />
		&nbsp;      
		<input name="btnSave" type="button" class="btn" value="Save" id="btnSaveId" />
		<input name="btnSave" type="button" class="btn" value="Save" id="btnCommentsSaveId" />
		&nbsp;      		
		<a href="#" class="btn jqmClose"  id="doneBtn">Done</a>
  </div>

<div class="jqmWindow" id="ex7">Please wait...</div>
<!-- pop up ends -->