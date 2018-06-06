<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Call Report</title>

<script type="text/javascript" src="static/js/validation/tools/callReportsFormValidation.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		new JsDatePick({
			useMode : 2,
			target : "inBoundPeriodFrom",
			dateFormat : "%M/%d/%Y"
		});
		new JsDatePick({
			useMode : 2,
			target : "inBoundPeriodTo",
			dateFormat : "%M/%d/%Y"
		});

		new JsDatePick({
			useMode : 2,
			target : "outBoundPeriodFrom",
			dateFormat : "%M/%d/%Y"
		});
		new JsDatePick({
			useMode : 2,
			target : "outBoundPeriodTo",
			dateFormat : "%M/%d/%Y"
		});
	
	// tab
	//Default Action
	$(".new_tab_content").hide(); //Hide all content

	var reportType=$("#reportType").val();	
	if(reportType=="inbound"){
		$("ul.new_tabs li:first").addClass("active").show(); //Activate first tab
		$(".new_tab_content:first").show();
	}else{
		$("ul.new_tabs li:last").addClass("active").show(); //Activate first tab
		$(".new_tab_content:last").show();
	}
	
	//On Click Event
	$("ul.new_tabs li").click(function() {
		$("ul.new_tabs li").removeClass("active"); //Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".new_tab_content").hide(); //Hide all tab content
		//var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
		var reportType = $(this).find("a").attr("reportType");
		//$(activeTab).fadeIn(); //Fade in the active content
		$("#reportType").val(reportType);
		//$('#callReportForm').attr('action', 'call-report.html?reportType='+reportType);
		$('#callReportForm').submit();
		return false;
	});

	$("#inBoundGo").click(function() {		
		if(validateCallReport()){
			$('#callReportForm').submit();
		}		
	});
	$("#outBoundGo").click(function() {		
		if(validateCallReport()){
			$('#callReportForm').submit();
		}	
	});

	$('.transStates').click(function() {	
		try{
			  var id = $(this).attr('id');
			  var url = 'call-report-trans-states.html?transId='+id;
			  //alert("URL :::::::::: "+url);
			  $('#ex3').jqm({ajax: url,modal: true,trigger: 'a.ex3trigger'}).show();
		}catch(e){
			alert("Error :::::::::: "+e);
		}
	});
});

</script>

	<form:form  id="callReportForm" commandName="callReportRequestTO" action="call-report.html" method="post">
	<form:hidden  path="reportType"  id="reportType" />

    <h1>Call Report </h1>

	<div class="error" id="fromDateError"></div>	
	<div class="error" id="toDateError"></div>	

	<!-- <div class="refresh-icon"><a title="Refresh" href="#"></a></div> -->
    <div class="new_ultab">
      <ul class="new_tabs">
        <li><a href="javascript:donothing()" id="InBound"  reportType="inbound" >InBound Calls</a></li>
        <li><a href="javascript:donothing()" id="OutBound" reportType="outbound">OutBound Calls</a></li>
      </ul>
    </div>

    <div class="new_tab_container"> 
      
      <!-- Tab 1 starts -->
      <div id="tab1" class="new_tab_content"> 
        <!--Options starts -->
        <div class="options txt-bold"> Periods From : &nbsp;
          <form:input  path="inBoundPeriodFrom" id="inBoundPeriodFrom"/>
          To : &nbsp;
          <form:input  path="inBoundPeriodTo" id="inBoundPeriodTo"/>&nbsp;&nbsp;
          <!-- <input  id="inBoundGo" type="button" class="btn" value="Go"><br/><br/> -->
		  Caller ID  : &nbsp;<form:input  path="inBoundCallerId" id="inBoundCallerId"/>
          <input  id="inBoundGo" type="button" class="btn" value="Search">		
		  <div align="right" >
				<b>InBound Calls  total no.of minutes : <%-- ${inBoundCallsResponse.totalMinutes} --%> </b> 
		  </div>
		   <div align="left" >
				<b>Click TransID link to view detailed foot print.</b>
		  </div>		  
        </div>
        <!--Options ends -->         
       	<!--  class="${(inBoundCallLog.conf_num!=null) ? 'green' : 'red'}"   -->
		<!-- Main tables starts -->		
		<c:if test="${!inBoundCallsResponse.responseStatus && inBoundCallsResponse.errorStatus}">
			Nothing found to display.
		</c:if>
		<c:if test="${inBoundCallsResponse.responseStatus && !inBoundCallsResponse.errorStatus}">
			<c:set var = "titles" value="${fn:split(inBoundCallsResponse.title, ',')}"/>
			<c:set var = "javaRefs" value="${fn:split(inBoundCallsResponse.javaRef, ',')}"/>
			<c:set var = "hides" value="${fn:split(inBoundCallsResponse.hides, ',')}"/>
			
			<display:table id="ivrcall" name="inBoundCallsResponse.ivrcallList" export="true" requestURI="" pagesize="100" class="new_main-table">
				
				<display:column title="Trans ID" media="html" >
					<a href="javascript:doNothing()" class="ex3trigger" id="${ivrcall.transId}">
							<span class="transStates"  id="${ivrcall.transId}">${ivrcall.transId}</span>
					</a>
				</display:column>

				<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
					<c:if test="${fn:contains(hides[i], 'N')}">
						<c:if test="${! fn:contains(javaRefs[i], 'transId')}">
							<display:column title="${titles[i]}" property="${javaRefs[i]}" sortable="true"/>
						</c:if>
					</c:if>
				</c:forEach>

				<%--
				<display:column  property="conf_num" title="Confirm no" sortable="true" />  
				<display:column  property="start_time" title="Call Timestamp" sortable="true" />
				<display:column  property="customerFirstName" title="First Name" sortable="true" />
				<display:column  property="customerLastName" title="Last Name" sortable="true" />
				<display:column  property="callerId" title="Caller ID" sortable="true" />
				<display:column  property="homePhone" title="Home Phone" sortable="true" />
				<display:column  property="apptType" title="Appt Type" sortable="true" />
				<display:column  property="location" title="location" sortable="true"/>
				<display:column  property="resource" title="resource" sortable="true" />
				<display:column  property="seconds" title="Call Duration(Mins)" sortable="true" />
				--%>
				<display:setProperty name="export.excel.filename" value="InBound Calls.xls" />
				<display:setProperty name="export.csv.filename" value="InBound Calls.csv" />
				<display:setProperty name="export.pdf.filename" value="InBound Calls.pdf" />
				<display:setProperty name="export.xml.filename" value="InBound Calls.xml" />
				<display:setProperty name="export.xml.filename" value="InBound Calls.xml" />
				<display:setProperty name="export.rtf.filename" value="InBound Calls.rtf" />
			</display:table>
		</c:if>
		<!-- Main tables ends -->

      </div>
      <!-- Tab 1 ends --> 

      <!-- Tab 2 starts -->
      <div id="tab2" class="new_tab_content"> 
        <!--Options starts -->
        <div class="options txt-bold"> Periods From : &nbsp;
          <form:input  path="outBoundPeriodFrom" id="outBoundPeriodFrom"/>
          To : &nbsp;
          <form:input  path="outBoundPeriodTo" id="outBoundPeriodTo"/>  &nbsp; &nbsp;
         <!-- <input id="outBoundGo" type="button" class="btn" value="Go">	 -->
		  <!-- Caller ID  : &nbsp;<form:input  path="outBoundCallerId" id="outBoundCallerId"/> -->
          <input  id="outBoundGo" type="button" class="btn" value="Search">		
		  <div align="right" >
				<b>OutBound Calls  total no.of  minutes : <%-- ${outBoundCallsResponse.totalMinutes} --%></b>
		  </div>
        </div>
        <!--Options ends --> 
        
		<!-- Main tables starts -->		
		<c:if test="${!outBoundCallsResponse.responseStatus && outBoundCallsResponse.errorStatus}">
			Nothing found to display.
		</c:if>
		<c:if test="${outBoundCallsResponse.responseStatus && !outBoundCallsResponse.errorStatus}">
			<c:set var = "titles" value="${fn:split(outBoundCallsResponse.title, ',')}"/>
			<c:set var = "javaRefs" value="${fn:split(outBoundCallsResponse.javaRef, ',')}"/>
			<c:set var = "hides" value="${fn:split(outBoundCallsResponse.hides, ',')}"/>

			<display:table id="ivrcall" name="outBoundCallsResponse.ivrcallList" export="true" requestURI="" pagesize="100" class="main-table">
					
				<c:forEach var="i" begin="0" end="${fn:length(javaRefs)-1}">
					<c:if test="${fn:contains(hides[i], 'N')}">
						<display:column title="${titles[i]}" property="${javaRefs[i]}" sortable="true"/>
					</c:if>
				</c:forEach>
				<%--
				<display:column  property="trans_id" title="Trans ID" sortable="true"  />
				<display:column  property="customerFirstName" title="First Name" sortable="true" />
				<display:column  property="customerLastName" title="Last Name" sortable="true" />
				<display:column  property="apptDateTime" title="Appt Date Time" sortable="true" />
				<display:column  property="callTime" title="Call Time" sortable="true" />
				<display:column  property="appempt" title="Attempt" sortable="true" />
				<display:column  property="dailedPhone" title="Dailed Phone" sortable="true" />
				<display:column  property="status" title="Status" sortable="true" />
				<display:column  property="location" title="location" sortable="true" />
				<display:column  property="resource" title="resource" sortable="true" />
				<display:column  property="seconds" title="Minutes" sortable="true" />
				--%>
				<display:setProperty name="export.excel.filename" value="OutBound Calls.xls" />
				<display:setProperty name="export.csv.filename" value="OutBound Calls.csv" />
				<display:setProperty name="export.pdf.filename" value="OutBound Calls.pdf" />
				<display:setProperty name="export.xml.filename" value="OutBound Calls.xml" />
				<display:setProperty name="export.xml.filename" value="OutBound Calls.xml" />
				<display:setProperty name="export.rtf.filename" value="OutBound Calls.rtf" />
			</display:table>
		</c:if>
		<!-- Main tables ends -->      
      </div>
      <!-- Tab 2 ends --> 

    </div>
    <div class="clear-all"></div>
 
  </form:form> 

<div class="jqmWindow" id="ex3">Please wait...</div>