<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Event Date Time</title>

<script type="text/javascript">
	$(document).ready(function() {
		  $('#ex2').jqm({ajax : 'addEventDateTime.html', modal: true,trigger : 'a.ex2trigger' });
		 
		  $('.editEventDateTime').click(function() {	
			  var id = $(this).attr('id');
			  var url = 'editEventDateTime.html?eventDateTimeId='+id;
			  $('#ex3').jqm({ajax: url,modal: true,cache: false,trigger: 'a.ex3trigger'});
		  });
		  $('.editEventSeats').click(function() {
			  var id = $(this).attr('id');
			  var url = 'editEventSeats.html?eventDateTimeId='+id;
			  $('#ex4').jqm({ajax: url,modal: true,cache: false,trigger: 'a.ex4trigger'});
		  });
	});
</script>

<!-- Body starts -->
    <h1>Event Date Time > Add/Edit/View Event Date Time</h1>
    <!--Options starts -->
    <div class="options">
      <div class="float-left txt-bold">Scheduled Event Date Time</div>
      <div class="float-right"><a href="javascript:doNothing()" class="btn ex2trigger">Add Event Date Time</a></div>
      <div class="clear-all"></div>
    </div>
	
    <!--Options ends --> 
   <!-- Main tables starts -->
    <display:table id="eventDateTime" name="eventDateTimeResponse.eventDateTimeList" export="true" requestURI="" pagesize="100" class="main-table" >
			
			<display:column property="locationName"  title="Location" sortable="true" />
			<display:column property="eventName"  title="Event" sortable="true" />

			<display:column title="Date Time" sortable="true" media="html">
				${eventDateTime.date} - ${eventDateTime.time} &nbsp;&nbsp;
				<c:if test="${eventDateTime.showEditIcon}">
					<a href="javascript:doNothing()" class="ex3trigger" id="${eventDateTime.eventDateTimeId}">
						<img src="static/images/edit.png" width="16" height="16" alt="Edit Event Date Time" title="Edit Event Date Time" id="${eventDateTime.eventDateTimeId}&noOfNotifiedReservations=${eventDateTime.noOfNotifiedReservations}" class="editEventDateTime"/>
					</a>
				</c:if>
			</display:column>
			<display:column title="Date Time" sortable="true" media="excel pdf csv xml rtf">
				${eventDateTime.date} - ${eventDateTime.time}
			</display:column>

			<display:column title="No.of Seats" sortable="true" media="html">
				${eventDateTime.noOfSeats}&nbsp;&nbsp;
				<c:if test="${eventDateTime.showEditIcon}">
					<a href="javascript:doNothing()" class="ex4trigger" id="${eventDateTime.eventDateTimeId}">
						<img src="static/images/edit.png" width="16" height="16" alt="Edit Event Seats" title="Edit Event Seats" id="${eventDateTime.eventDateTimeId}" class="editEventSeats"/>
					</a>
				</c:if>
			</display:column>
			<display:column title="No.of Seats" sortable="true" media="excel pdf csv xml rtf">
				${eventDateTime.noOfSeats}
			</display:column>

			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'allow_disallow_event_scheduling')}"> --%>
				<display:column title="Allow Scheduling <br/>by Customer" media="html">
					<c:if test="${eventDateTime.showEditIcon}">
						<c:choose>
							<c:when test="${fn:contains(eventDateTime.enable, 'Y')}">
								<a href="openCloseEventDateTime.html?eventDateTimeId=${eventDateTime.eventDateTimeId}&enable=N" onclick="return confirm('Do you want to Disallow Scheduling by Customer');"  alt="Allow" title="Allow">
									<img src="static/images/enable-icon.png" width="16" height="16" alt="Enable">
								</a>
							</c:when>
							<c:when test="${fn:contains(eventDateTime.enable, 'N')}">
								<a href="openCloseEventDateTime.html?eventDateTimeId=${eventDateTime.eventDateTimeId}&enable=Y" onclick="return confirm('Do you want to Allow Scheduling by Customer');"  alt="Disallow" title="Disallow">
									<img src="static/images/disable-icon.png" width="16" height="16" alt="Disable">
								</a>
							</c:when>							
						</c:choose>
					</c:if>
				</display:column>
				<display:column title="Allow Scheduling by Customer" media="excel pdf csv xml rtf" value="${fn:contains(eventDateTime.enable, 'Y')==true ? 'Allow' : 'Disallow'}"/>
			<%--  </c:if>--%>
			 
			<display:setProperty name="export.excel.filename" value="Event Date Time.xls" />
			<display:setProperty name="export.csv.filename" value="Event Date Time.csv" />
			<display:setProperty name="export.pdf.filename" value="Event Date Time.pdf" />
			<display:setProperty name="export.xml.filename" value="Event Date Time.xml" />
			<display:setProperty name="export.rtf.filename" value="Event Date Time.rtf" />

		</display:table>
    <!-- Main tables ends -->
<!-- Body ends --> 

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<div class="jqmWindow" id="ex4"> Please wait... </div>
<!--Pop up section ends -->