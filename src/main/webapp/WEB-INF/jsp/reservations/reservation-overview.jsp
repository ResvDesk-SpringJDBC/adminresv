<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Reservations > Reservation Overview</title>

<script type="text/javascript" src="static/js/validation/reservations/reservationOverviewFormValidation.js?version=1.0"></script>

<script type="text/javascript">
	$(document).ready(function() {
		new JsDatePick({
			useMode : 2,
			target : "startDate",
			dateFormat : "%M/%d/%Y"
		});
		new JsDatePick({
			useMode : 2,
			target : "endDate",
			dateFormat : "%M/%d/%Y"
		});

		$('.details').click(function() {
			  var ids = $(this).attr('id');
			  var url = 'reservation-overview-details.html?'+ids;
			  url = url.replace(/ /g,"%20");
			  $('#ex2').jqm({ajax: url,modal: true,cache: false,trigger: 'a.ex2trigger'});
		 });

	});
</script>

<!-- Body starts -->
    <h1>Reservations > Reservation Overview</h1>

	<div class="error" id="startDateError"></div>
	<div class="error" id="endDateError"></div>	

    <!--Search bar starts -->
	<form id="reservationOverviewForm" name="reservationOverviewForm" method="post" action="search-reservation-overview.html"		
				onsubmit="return validateReservationOverviewForm()">
		<div class="search-bar">
		  <div class="float-left"> Start Date:
			<input id="startDate" name="startDate" type="text" value="${startDate}">
		  </div>
		  <div class="float-left"> End Date:
			<input id="endDate" name="endDate"  type="text" value="${endDate}">
		  </div>		  
		  <div class="float-left">
			<input name="btnSearch" type="submit" class="btn" value="Search">
		  </div>
		  <div class="clear-all"></div>
		</div>
	<form>
    <!--Search bar starts --> 
    
    <!--Options starts -->
    <div class="options">
      <div class="txt-bold">Search Results</div>
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 
    
   <!-- Main tables starts -->
    <display:table id="calendarOverView" name="calOverviewResponse.calendarOverViewList" export="true" requestURI="" pagesize="100" class="main-table" >
			
		<display:column property="locationName"  title="Location" sortable="true" />
		<display:column property="eventName"  title="Event" sortable="true" />
		<display:column title="Date Time" sortable="true">
				${calendarOverView.date} ${calendarOverView.time}
		</display:column>
		<display:column property="totalSeats"  title="Total Seats" sortable="true" />
		<display:column property="bookedSeats"  title="Booked Seats" sortable="true" />
		<display:column property="reservedSeats"  title="Reserved Seats" sortable="true" />
		<display:column property="openSeats"  title="Opened Seats" sortable="true" />

		<display:column title="Details" media="html">
			 <a href="javascript:doNothing()" class="ex2trigger">
				<img id="eventDateTimeId=${calendarOverView.eventDateTimeId}&locationName=${calendarOverView.locationName}&eventName=${calendarOverView.eventName}&eventDateTime=${calendarOverView.date} ${calendarOverView.time}&totalSeats=${calendarOverView.totalSeats}&bookedSeats=${calendarOverView.bookedSeats}" src="static/images/search-appt.png" width="16" height="16"
				alt="Details" title="Details" class="details"/>
			 </a> 
		</display:column>
	
		<display:setProperty name="export.excel.filename" value="Reservation Overview.xls" />
		<display:setProperty name="export.csv.filename" value="Reservation Overview.csv" />
		<display:setProperty name="export.pdf.filename" value="Reservation Overview.pdf" />
		<display:setProperty name="export.xml.filename" value="Reservation Overview.xml" />
		<display:setProperty name="export.rtf.filename" value="Reservation Overview.rtf" />

	</display:table>
    <!-- Main tables ends -->
<!-- Body ends --> 

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<!--Pop up section ends -->