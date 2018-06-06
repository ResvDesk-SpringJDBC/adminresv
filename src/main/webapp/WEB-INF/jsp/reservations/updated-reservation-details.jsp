<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript">
	var contextPath =  "${pageContext.request.contextPath}";
	$(document).ready(function() {	
		var operationResponseStatus = $("#operationResponseStatus").val();
		var operationResponse = $("#operationResponse").val();
		var responseDisplyDivId = $("#responseDisplyDivId").val();

		if(operationResponseStatus=="SUCCESS"){
			$("#"+responseDisplyDivId).html(operationResponse).css("color","green").css("text-align","center");
		}else{
			$("#"+responseDisplyDivId).html(operationResponse).css("color","red").css("text-align","center");
		}	
		
		$('#resvMsg').html("");
		$('#doneBtn').show();
		$('.cancleUpcomingResv').click(function() {	
		  $('#doneBtn').hide();
		  var isConfirmed = confirm("Are you sure, do you want to cancel the Reservation");
		  if(isConfirmed){
				$('#resvMsg').html("<div style='color:red;text-align:center;'><b>Please wait Reservation is canceling!</b></div>");
				var sceduleId = $(this).attr('id');
				var url = contextPath+"/cancle-resv-and-load-updated-resvs.html";
				var customerId = $("#editCustomerId").val();
				var data =  "customerId="+customerId+"&sceduleId="+sceduleId;
				//alert("DATA  ----> "+data);
				data = data.replace( ' ', '%20' );
				$("#upcomingResvDivId").load(url,data,function(response,status,xhr){
				});
		  }
		});

	});
</script>
	
  <c:set value="${fn:contains(homeBean.adminInfoTO.accessLevel,'Read Only')}"  var="result" />
   <input type="hidden" id="operationResponseStatus" value="${operationResponseStatus}"/>
   <input type="hidden" id="operationResponse" value="${operationResponse}"/>
   <input type="hidden" id="responseDisplyDivId" value="${responseDisplyDivId}"/>

   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
	  <tr>
		<th width="5%">Conf No.</th>
		<th width="20%">Resv Date</th>
		<th width="15%">Location</th>
		<th width="25%">Event</th>
		<th width="20%">Seat</th>
		<c:if test="${!result}">
			<th width="15%">${linkName}</th>
		</c:if>
	  </tr>		
	    <c:if test="${resvDetailsResponseStatus=='SUCCESS'}" >
		 <c:forEach var="resvDetails" items="${resvDetailsResv}">
		   <tr>
				<td>${resvDetails.confNumber}</td>
				<td>${resvDetails.eventDateTime}</td>
				<td>${resvDetails.locationName}</td>
				<td>${resvDetails.eventName}</td>
				<td>${resvDetails.displaySeatNumber}</td>
				<c:if test="${!result}">
					<td>
						<a href="javascript:void(0)" class="${linkCSSClassName}"  id="${resvDetails.scheduleId}">${linkName}</a>
					</td>
				</c:if>
			  </tr>			 
			</c:forEach>
		</c:if>
		<c:if test="${resvDetailsResponseStatus!='SUCCESS'}" >
			<tr><td colspan="6">${resvDetailsResponse}</td></tr>
		</c:if>
	</table>		