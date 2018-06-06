<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Locations</title>

<script type="text/javascript">
	$(document).ready(function() {
		$('#ex2').jqm({ajax : 'add-location.html', modal: true,trigger : 'a.ex2trigger'});

		$('.edit').click(function() {	
		  var id = $(this).attr('id');
		  var url = 'edit-location.html?id='+id;
		  $('#ex3').jqm({ajax: url,modal: true,cache: false,trigger: 'a.ex3trigger'});
	    });

	    $('.locclose').click(function() {	
		  var id = $(this).attr('id');
		  var url = 'locations-close.html?id='+id;
		  $('#ex4').jqm({ajax: url,modal: true,cache: false,trigger: 'a.ex4trigger'});
	    });
	});
</script>

<!-- Body starts -->
    <h1>Locations</h1>
    <!--Options starts -->
    <div class="options">
      <div class="float-left txt-bold">Locations</div>
      <div class="float-right"><a href="javascript:doNothing()" class="btn ex2trigger">Add Location</a></div>
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 
    
    <!-- Main tables starts -->
    <display:table id="location" name="locationListResponse.locationList" export="true" requestURI="" pagesize="100" class="main-table" >
			
			<display:column property="locationName"  title="Location Name" sortable="true" />
			<display:column property="address"  title="Address" sortable="true" />
			<display:column property="city"  title="City" sortable="true" />
			<display:column title="State" sortable="true">
				${states[location.state]}
			</display:column>
			<display:column property="zip"  title="ZIP" sortable="true" />

			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'edit_location')}">  --%>
				<display:column title="Edit" media="html">
					<a href="javascript:doNothing()" class="ex3trigger" id="${location.locationId}">
						<img src="static/images/edit.png" width="16" height="16" alt="Edit" title="Edit" id="${location.locationId}" class="edit"/>
					</a>
				</display:column>
			<%-- </c:if>  --%>
			
			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'allow_disallow_location_scheduling')}">  --%>
				<display:column title="Allow Scheduling <br/>by Customer" media="html">
					<c:choose>
						 <c:when test="${fn:contains(location.enable, 'Y')}">
							<a href="open-close-location.html?locationId=${location.locationId}&enable=N" onclick="return confirm('Do you want to Disallow Scheduling by Customer');"  alt="Allow" title="Allow">
								<img src="static/images/enable-icon.png" width="16" height="16" alt="Enable">
							</a>
						</c:when>
						<c:when test="${fn:contains(location.enable, 'N')}">
							<a href="open-close-location.html?locationId=${location.locationId}&enable=Y" onclick="return confirm('Do you want to Allow Scheduling by Customer');"  alt="Disallow" title="Disallow">
								<img src="static/images/disable-icon.png" width="16" height="16" alt="Disable">
							</a>
						</c:when>							
					</c:choose>
				</display:column>
				<display:column title="Allow Scheduling by Customer" media="excel pdf csv xml rtf" value="${fn:contains(location.enable, 'Y')==true ? 'Allow' : 'Disallow'}"/>
			<%-- </c:if>--%>

			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'open_close_location')}">
				<display:column title="Open/Close Status" media="html">
					<c:choose>
						<c:when test="${fn:contains(location.closed, 'Y')}">
							<a href="openLocation.html?id=${location.locationId}" title="Closed" onclick="return confirm('Do you want to Open the ${homeBean.displayNamesTO.location_name}');">
								<img src="static/images/close_icon.png" alt="Closed" title="Closed"  width="24" height="24"/>
							</a>
						</c:when>
						<c:when test="${fn:contains(location.closed, 'N')}">
							<a href="closeLocation.html?id=${location.locationId}" title="Opened" onclick="return confirm('Do you want to Close the	 ${homeBean.displayNamesTO.location_name}');">
								<img src="static/images/open_icon.png" alt="Opened" title="Opened"  width="24" height="24"/>
							</a>
						</c:when>							
					</c:choose>
				</display:column>
				<display:column title="Closed" media="excel pdf csv xml rtf" value="${fn:contains(location.closed, 'Y')==true ? 'Yes' : 'No'}"/>
			</c:if>--%>

			<display:setProperty name="export.excel.filename" value="Locations.xls" />
			<display:setProperty name="export.csv.filename" value="Locations.csv" />
			<display:setProperty name="export.pdf.filename" value="Locations.pdf" />
			<display:setProperty name="export.xml.filename" value="Locations.xml" />
			<display:setProperty name="export.rtf.filename" value="Locations.rtf" />

		</display:table>
    <!-- Main tables ends -->
<!-- Body ends --> 

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<!--Pop up section ends -->