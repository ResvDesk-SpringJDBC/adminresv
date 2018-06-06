<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>

<html>
<head>

<link href="static/css/global.css" rel="stylesheet" type="text/css">
 <link href="static/css/global-print.css" rel="stylesheet" type="text/css" media="print">
<link rel="stylesheet" type="text/css" media="all" href="static/css/jqModal.css" />
<link rel="stylesheet" type="text/css" media="all" href="static/css/jsDatePick_ltr.min.css" />
<link rel="stylesheet" type="text/css" media="all" href="static/css/jquery-ui.css?version=1.1" />
<link rel="stylesheet" type="text/css" media="all" href="static/css/jquery.ui.datepicker.css?version=1.0" />

<script type="text/javascript" src="static/js/common.js"></script>
<script type="text/javascript" src="static/js/default.js"></script>
<script type="text/javascript" src="static/js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="static/js/jquery/jsDatePick.jquery.min.1.3.js?version=1.0"></script>
<script type="text/javascript" src="static/js/jquery/jqModal.js"></script>
<script type="text/javascript" src="static/js/jquery/jquery-ui.js"></script> 

<script type="text/javascript">	
	var contextPath="<%=request.getContextPath() %>";
</script>

<!-- <title><tiles:insertAttribute name="title" ignore="true" /></title> -->
<input type="hidden" id="locationDisplayName" value="Location"/>
<input type="hidden" id="eventDisplayName" value="Event"/>

</head>
	<body>		
		<header id="branding">
			<tiles:insertAttribute name="header"/>
			<div id="navbar">
				<tiles:insertAttribute name="menu"/>
			</div>
			<div id="iconbar">
				<tiles:insertAttribute name="iconbar" />
			</div>
		</header>
		<div id="main">
			<div style="color:blue" id="sucessesMsgDivId"><b>${sucessesMessage}</b></div>
			<div class="required"  id="errorMsgDivId"><b>${errorMessage}</b></div>
			<tiles:insertAttribute name="body" />
		</div>		
	</body>
</html>