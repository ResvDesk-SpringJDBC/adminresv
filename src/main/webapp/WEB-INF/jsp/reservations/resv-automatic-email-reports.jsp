<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Reservations > Automatic Email Reports</title>

<script type="text/javascript">
	$(document).ready(function() {
		$('#ex2').jqm({ajax : 'add-automatic-email-reports.html', modal: true,trigger : 'a.ex2trigger'});

	    $('.deleteConfig').click(function() {
		  var isConfirmed = confirm('Do you want to delete the selected record');
		  if(isConfirmed){
			  var id = $(this).attr('id');
			  window.location="delete-automatic-email-reports.html?id="+id;
		  }
	   });
	});
</script>

<!-- Body starts -->
    <h1>Reservations > Automatic Email Reports</h1>
    <!--Options starts -->
    <div class="options">
      <div class="float-left txt-bold">Automatic Email Reports</div>
      <div class="float-right"><a href="javascript:doNothing()" class="btn ex2trigger">Configure Automatic Report</a></div>
	  <br/><br/>
	  <div class="clear-all"></div>
	  <span class="required">*</span>Configured reports will be emailed to above address(es) on every morning at 4 AM Central Time. Please check your email spam filter if you did not receive it.
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 
    
    <!-- Main tables starts -->
    <display:table id="resvReportConfig" name="reseReportConfigResponse.resvReportConfigList" export="true" requestURI="" pagesize="100" class="main-table" >
			
			<display:column  property="userName" title="User Name" sortable="true" />
			<display:column  property="timestamp" title="Timestamp " sortable="true" />
			<display:column  property="reportName" title="Report Name " sortable="true" />
			<display:column  property="email1" title="Email 1" sortable="true" />
			<display:column  property="email2" title="Email 2" sortable="true" />
			<display:column  property="email3" title="Email 3" sortable="true" />
			<display:column title="Delete" media="html">
				<a href="javascript:donothing();"  id="${resvReportConfig.resvReportConfigId}" title="Delete">
					<img src="static/images/delete.png" width="16" height="16" alt="Delete" title="Delete" class="deleteConfig" id="${resvReportConfig.resvReportConfigId}"/>
				</a>
			</display:column>

			<display:setProperty name="export.excel.filename" value="Automatic Report Configs.xls" />
			<display:setProperty name="export.csv.filename" value="Automatic Report Configs.csv" />
			<display:setProperty name="export.pdf.filename" value="Automatic Report Configs.pdf" />
			<display:setProperty name="export.xml.filename" value="Automatic Report Configs.xml" />
			<display:setProperty name="export.rtf.filename" value="Automatic Report Configs.rtf" />

		</display:table>
    <!-- Main tables ends -->
<!-- Body ends --> 

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<!--Pop up section ends -->