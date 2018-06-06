<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Customer > Customer Details</title>

<script type="text/javascript">
	$().ready(function() {
	  $('#ex2').jqm({ajax:'add-customer.html', modal: true,trigger: 'a.ex2trigger'});  
	  $('.edit').click(function() {	
		  var id = $(this).attr('id');
		  //alert("id :::"+id);
		  $('#ex3').jqm({ajax:'edit-customer.html?customerId='+id, modal: true,trigger: 'a.ex3trigger'});
	  });
	});

	function validateSearchForm(){
		$("#searchError").html(" ");
		var i =0;
		var result = true;
		$( "input.validateInput" ).each(function( index ) {
			if($(this).val() !="" ) {
			   i++;
		    }
		}); 
	    if(i==0){
		   $("#searchError").html("<b>Please provide atleast one search Parameter</b>");
		   result = false;
	    }
	    return result;
	}
</script>


<!-- Body starts -->
	<h1>Customer > Customer Details</h1>
    <!--Search bar starts -->
	<form:form action="customer-details.html" method="POST" id="custDetailsSearchRequestForm" modelAttribute="custDetailsSearchRequest"> 
		<div class="search-bar">
		  <p>Please enter one or more of the fields and click Search button. You may enter a full value or first few characters.</p>
		  <input type="hidden" id="isSearchReq" name="isSearchReq" value="yes"/>
		  <c:if test="${searchFieldsResponse.responseStatus && !searchFieldsResponse.errorStatus}">
			  <c:set var = "titles" value="${fn:split(searchFieldsResponse.titles, ',')}"/>
			  <c:set var = "javaRefs" value="${fn:split(searchFieldsResponse.javaRef, ',')}"/>
			  <c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
				  <div class="float-left"> ${titles[i]}:
					<form:input path="${javaRefs[i]}" id="${javaRefs[i]}"/>
				  </div>
			  </c:forEach>
		  </c:if>	
		  <c:if test="${searchFieldsResponse.errorStatus && !searchFieldsResponse.responseStatus }">
			<b>Error while retriving customer search fields!</b>
		  </c:if>	

		  <div class="float-left">
			<input name="btnSearch" type="submit" class="btn" value="Search">
		  </div>
		  <div class="clear-all"></div>
		</div>
	</form:form>
    <!--Search bar Ends --> 
    
	<!--Options starts -->
    <div class="options">
      <div class="txt-bold float-left">Customer Details</div>
      <div class="float-right"><a href="javascript:doNothing()" class="btn ex2trigger">Add Customer</a></div>
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 
    
   <!-- Main tables starts -->
	<c:if test="${clientDetailsResponse.responseStatus && !clientDetailsResponse.errorStatus}">
		 
		<c:set var = "titles" value="${fn:split(clientDetailsResponse.titles, ',')}"/>
		<c:set var = "javaRefs" value="${fn:split(clientDetailsResponse.javaRefs, ',')}"/>
		<c:set var = "hides" value="${fn:split(clientDetailsResponse.hides, ',')}"/>
		
			<display:table id="customer" name="clientDetailsResponse.customerList" export="true" requestURI="" 
				pagesize="100" class="main-table" >
				 
				<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
					<c:if test="${fn:contains(hides[i], 'N')}">
						<display:column property="${javaRefs[i]}"  title="${titles[i]}" sortable="true" />
					</c:if>
				</c:forEach>
				
				<%--
				<display:column title="Blocked" sortable="true" >
					<c:choose>
						<c:when test="${fn:contains(customer.blocked_flag, 'Y')}">Yes</c:when>
						<c:when test="${fn:contains(customer.blocked_flag, 'N')}">No</c:when>
					</c:choose>
				</display:column>
				<display:column title="Deleted" sortable="true" >
					<c:choose>
						<c:when test="${fn:contains(customer.delete_flag, 'Y')}">Yes</c:when>
						<c:when test="${fn:contains(customer.delete_flag, 'N')}">No</c:when>
					</c:choose>
				</display:column>
				--%>

				<display:column title="Reservations" media="html"> 
					<a href="daily-view-calendar-for-setlect-cust.html?customerId=${customer.customerId}&customerName=${customer.firstName}  ${customer.lastName }  - ${customer.accountNumber}">Reserve</a>
				</display:column>

				<display:column title="Edit" media="html"> 
					<a href="javascript:doNothing()" class="ex3trigger" id="${customer.customerId}">
						<img src="static/images/edit.png" width="16" height="16" alt="Edit" title="Edit" id="${customer.customerId}" class="edit">
					</a>
				</display:column>
				
				<display:setProperty name="export.excel.filename" value="Customer Details.xls" />
				<display:setProperty name="export.csv.filename" value="Customer Details.csv" />
				<display:setProperty name="export.pdf.filename" value="Customer Details.pdf" />
				<display:setProperty name="export.xml.filename" value="Customer Details.xml" />
				<display:setProperty name="export.rtf.filename" value="Customer Details.rtf" />

			</display:table>
	 </c:if>	
	  <c:if test="${clientDetailsResponse.errorStatus && !clientDetailsResponse.responseStatus }">
		<b>Error while retriving customer details!</b>
	  </c:if>
    <!-- Main tables ends -->

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<!--Pop up section ends -->

<!-- Body ends -->