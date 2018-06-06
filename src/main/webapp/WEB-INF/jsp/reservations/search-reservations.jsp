<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Reservations &gt; Search Reservations</title>


<!-- Body starts -->
	<h1>Reservations > Search Reservations</h1>
    <!--Search bar starts -->
	<form:form action="search-reservations.html" method="POST" id="resvSearchRequestForm" modelAttribute="resvSearchRequest"> 
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
			<b>Error while retriving dynamic search fields!</b>
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
      <div class="txt-bold">Search Results</div>
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 
    
   <!-- Main tables starts -->
   <c:if test="${resvSearchResponse.responseStatus && !resvSearchResponse.errorStatus}">
		<c:set var = "titles" value="${fn:split(resvSearchResponse.title, ',')}"/>
		<c:set var = "javaRefs" value="${fn:split(resvSearchResponse.javaRef, ',')}"/>
		<c:set var = "hides" value="${fn:split(resvSearchResponse.hides, ',')}"/>

			<display:table id="resvDetail" name="resvSearchResponse.reservationDetails" export="true" requestURI="" 
				pagesize="100" class="main-table" >
				
				<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
					<c:if test="${fn:contains(hides[i], 'N')}">
						<display:column property="${javaRefs[i]}"  title="${titles[i]}" sortable="true" />
					</c:if>
				</c:forEach>
				
				<display:setProperty name="export.excel.filename" value="Reservation Search Results.xls" />
				<display:setProperty name="export.csv.filename" value="Reservation Search Results.csv" />
				<display:setProperty name="export.pdf.filename" value="Reservation Search Results.pdf" />
				<display:setProperty name="export.xml.filename" value="Reservation Search Results.xml" />
				<display:setProperty name="export.rtf.filename" value="Reservation Search Results.rtf" />

			</display:table>
	 </c:if>	
	  <c:if test="${resvSearchResponse.errorStatus && !resvSearchResponse.responseStatus }">
		<b>Error while retriving search results!</b>
	  </c:if>
    <!-- Main tables ends -->

<!-- Body ends -->