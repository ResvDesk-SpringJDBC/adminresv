<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Events</title>

<script type="text/javascript">
$(document).ready(function() {
	$('#ex2').jqm({ajax : 'add-event.html', modal: true,trigger : 'a.ex2trigger'});

	$('.edit').click(function() {	
	  var id = $(this).attr('id');
	  var url = 'edit-event.html?id='+id;
	  $('#ex3').jqm({ajax: url,modal: true,cache: false,trigger: 'a.ex3trigger'});
	});
});
</script>

<!-- Body starts -->
    <h1>Events > Add/Edit/View Events</h1>
    <!--Options starts -->
    <div class="options">
      <div class="float-left txt-bold">Events</div>
      <div class="float-right"><a href="javascript:doNothing()" class="btn ex2trigger">Add Event</a></div>
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 

   <!-- Main tables starts -->
    <display:table id="event" name="eventListResponse.eventList" export="true" requestURI="" pagesize="100" class="main-table" >
			
			<display:column property="locationNames"  title="Location(s)" sortable="true" />
			<display:column property="eventName"  title="Event" sortable="true" />
			<display:column property="duration"  title="Duration(in Mins)" sortable="true" />

			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'edit_event')}">  --%>
				<display:column title="Edit" media="html">
					<a href="javascript:doNothing()" class="ex3trigger" id="${event.eventId}">
						<img src="static/images/edit.png" width="16" height="16" alt="Edit" title="Edit" id="${event.eventId}" class="edit"/>
					</a>
				</display:column>
			<%-- </c:if>  --%>

			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'allow_disallow_event_scheduling')}"> --%>
				<display:column title="Allow Scheduling <br/>by Customer" media="html">
					<c:choose>
						<c:when test="${fn:contains(event.enable, 'Y')}">
							<a href="open-close-event.html?eventId=${event.eventId}&enable=N" onclick="return confirm('Do you want to Disallow Scheduling by Customer');"  alt="Allow" title="Allow">
								<img src="static/images/enable-icon.png" width="16" height="16" alt="Enable">
							</a>
						</c:when>
						<c:when test="${fn:contains(event.enable, 'N')}">
							<a href="open-close-event.html?eventId=${event.eventId}&enable=Y" onclick="return confirm('Do you want to Allow Scheduling by Customer');"  alt="Disallow" title="Disallow">
								<img src="static/images/disable-icon.png" width="16" height="16" alt="Disable">
							</a>
						</c:when>							
					</c:choose>
				</display:column>
				<display:column title="Allow Scheduling by Customer" media="excel pdf csv xml rtf" value="${fn:contains(event.enable, 'Y')==true ? 'Allow' : 'Disallow'}"/>
			<%--  </c:if>--%>

			<display:setProperty name="export.excel.filename" value="Events.xls" />
			<display:setProperty name="export.csv.filename" value="Events.csv" />
			<display:setProperty name="export.pdf.filename" value="Events.pdf" />
			<display:setProperty name="export.xml.filename" value="Events.xml" />
			<display:setProperty name="export.rtf.filename" value="Events.rtf" />

		</display:table>
    <!-- Main tables ends -->
<!-- Body ends --> 

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<!--Pop up section ends -->