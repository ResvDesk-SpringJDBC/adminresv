<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
<title>Reservation Details</title>

<script type="text/javascript" src="static/js/common.js"></script>
<script type="text/javascript" src="static/js/validation/reservations/bookReservationValidation.js"></script>
<script type="text/javascript">
	var contextPath =  "${pageContext.request.contextPath}";

	$().ready(function() {
		//Auto Tab
		$("input[class='phone']").bind('keyup', function() {
			var limit = parseInt($(this).attr('maxlength'));  
			var text = $(this).val();  			
			var chars = text.length; 
			//check if there are more characters then allowed
			if(chars >=limit){ 			
				$("#"+$(this).next().attr("id")).focus();
			}
		});	
	});
</script>

</head>

<body>
<!-- pop up starts -->
<form:form  id="bookResvform" commandName="resvBookingForm"  modelAttribute="resvBookingForm"  method="get">

  <h1>&nbsp;&nbsp;Reservation Details</h1>
  <a href="#" class="jqmClose close" title="Close"  id="popupClose">Close</a> 

<div id ="bookingResvDivId" > 

 <div class="popup-content-big" > 

    <!--Upcomint Reservation starts -->
    <div class="popup-box">
      <div class="popup-box-head">
        <h2>Upcoming Reservations (Top 5)</h2>
        <img src="static/images/plus.png" onClick="switchMainUpcomintReservation()" ondrag="return false" id="expnClsp1">
		<div class="clear-all"></div>
      </div>
      <div class="popup-box-content" id="UpcomintReservation" >
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
          <tr>
            <th width="30%">Resv Date</th>
            <th width="20%">Location</th>
            <th width="23%">Event</th>
            <th width="27%">Seat Number</th>
          </tr>
		  <c:forEach var="resvDetail" items="${futureResvResponse.resvDetails}">
			   <tr>
				<td>${resvDetail.eventDateTime}</td>
				<td>${resvDetail.locationName}</td>
				<td>${resvDetail.eventName}</td>
				<td>${resvDetail.displaySeatNumber}</td>
			  </tr>
		  </c:forEach>
        </table>
      </div>
    </div>
    <!--Upcomint Reservation ends --> 

    <!--Customer Info starts -->
    <div class="popup-box">
      <div class="popup-box-head">	  
        <h2>Customer Info</h2>
        <img src="static/images/minus.png" onClick="switchMainCustomerInfo()" ondrag="return false" id="expnClsp2">
		<div class="clear-all"></div>
      </div>
      <div class="popup-box-content" id="CustomerInfo">
	  <div class="error" id="validationResponse"> </div>
      <table width="100%"  style="border-collapse:separate; border-spacing:1em;">
		  <tr>	
			<form:hidden path="customer.customerId"/>
			<c:forEach var="loginFieldDetails" items="${loginInfoResponse.loginFieldList}" varStatus="count">   
				<td>${loginFieldDetails.displayTitle}</td>									 
				<td>
					<c:if test="${loginFieldDetails.displayType == 'textbox'}">
						<form:input type="${loginFieldDetails.displayType}" 
							path="customer.${loginFieldDetails.javaRef}"
							maxlength="${loginFieldDetails.maxLength}"/>
					</c:if>
					<c:if test="${fn:contains(loginFieldDetails.displayType, 'textbox-')}">
							<c:set var="textsplit" value="${fn:split(loginFieldDetails.displayType, '-')}" />
							<c:set var="len" value="${fn:length(textsplit)}" />
							<c:forEach var="i" begin="1" end="${fn:length(textsplit)-1}">
								<form:input type="${textsplit[0]}" path="customer.${loginFieldDetails.javaRef}${i}"
								    id="phone${loginFieldDetails.javaRef}${i}"
									class="${i == 3 ? 'phone1' : 'phone' }" maxlength="${textsplit[i]}"/>
							</c:forEach>
					</c:if>

					<c:if test="${loginFieldDetails.displayType == 'select'}">
						<c:set var="labelsplit"  value="${fn:split(loginFieldDetails.listLabels, ',')}" />
						<c:set var="valuesplit" 	value="${fn:split(loginFieldDetails.listValues, ',')}" />
						<c:set var="selectVal" 	value="$loginFieldDetails.listInitialValues }" />
						 <form:select path="customer.${loginFieldDetails.javaRef}">
							<form:option value="-1">Select</form:option>
							<c:forEach var="i" begin="0" end="${fn:length(labelsplit)-1}">
								<form:option value="${valuesplit[i]}" selected="${selectVal==valuesplit[i]  ? 'selected' : ''}">${labelsplit[i]}</form:option>
							</c:forEach>
						 </form:select>
					  </c:if>

					  <c:if test="${loginFieldDetails.displayType == 'radio'}">
						  <c:set var="labelsplit"   value="${fn:split(loginFieldDetails.listLabels, ',')}" />
						  <c:set var="valuesplit"  value="${fn:split(loginFieldDetails.listValues, ',')}" />
						  <c:forEach var="i" begin="0" end="${fn:length(labelsplit)-1}">														
								<form:radiobutton  style="margin-right: 20px;width: 40px;" class="noWidth"
									path="customer.${loginFieldDetails.javaRef}" id="${loginFieldDetails.javaRef}" 
									value="${valuesplit[i]}" checked="${valuesplit[i]== loginFieldDetails.listInitialValues ? 'checked' : '' }"/>
									${labelsplit[i]}	
						  </c:forEach>
					  </c:if>
				  </td>
				  <c:if test="${count.index % 2==1}">
					  </tr><tr>
				  </c:if>
				</c:forEach>
			 </tr>
		   </table>
		  </div>
		  <div class="clear-all"></div>
		</div>
		<!-- Customer Info ends -->
    
    <!--New Reservation Details starts -->
    <div class="popup-box" style="display1:none">
      <div class="popup-box-head">
        <h2>New Reservation Details</h2>
        <img src="static/images/minus.png" onClick="switchMainNewReservation()" ondrag="return false" id="expnClsp3">
		<div class="clear-all"></div>
      </div>
      <div class="popup-box-content" id="NewReservation">
      <dl class="normal">
          <dt>Location</dt>
          <dd>
			  ${locationName}
			  <form:hidden path="companyId" id="companyId"/>
			  <form:hidden path="procedureId" id="procedureId"/>
			  <form:hidden path="locationId" id="locationId"/>
		  </dd>
          <dt>Event</dt>
          <dd>
				${eventName}
				<form:hidden path="departmentId" id="departmentId" />	
				<form:hidden path="eventId" id="eventId" />	
			</dd>
          
          <dt class="clear-all">Seat Number</dt>
          <dd>
				${seatNumber}
				<form:hidden path="seatId" id="seatId" />	
			</dd>
           <dt > Resv Date & Time</dt>
          <dd>
				${date} @ ${time}
				<form:hidden path="eventDateTimeId" id="eventDateTimeId"/>
		 </dd>
		  <c:if test="${fn:contains(homeBean.resvSysConfig.displayComments,'Y') ||	fn:contains(homeBean.resvSysConfig.displayComments,'y')}">
			  <dt class="clear-all">Comments</dt>
			  <dd>
				<form:textarea path="comments" rows="${homeBean.resvSysConfig.commentsNoOfRows}" cols="${homeBean.resvSysConfig.commentsNoOfCols}" />
			  </dd>  
		  </c:if >		
      </dl>
      </div>
      <div class="clear-all"></div>
    </div>
    <!-- New Reservation Details  ends -->     
  </div>
 </div>
  <div class="popup-button" id="resvSave">		
		<a href="#" class="btn jqmClose"  id="cancelBtn">Cancel</a>
		&nbsp; 
		<input name="btnSave" type="button" class="btn" value="Book" id="btnSave" />
  </div>
  <div id="resvBookingMsg"  style="text-align:center;"> </div>
</form:form>

  <div class="popup-button"  id="OKDIV">
		<a href="#" class="btn jqmClose"  id="okBtn">Done</a>
  </div>

 <div class="jqmWindow" id="ex2">Please wait...</div>
 <!-- pop up ends -->