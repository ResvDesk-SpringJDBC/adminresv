<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<script type="text/javascript">	
	var contextPath="<%=request.getContextPath() %>";
</script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link href="static/css/global.css" rel="stylesheet" type="text/css">
<!--
<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,400italic,600,600italic,700,700italic">
-->
<link rel="stylesheet" type="text/css" media="all" 	href="static/css/jqModal.css" />
<link rel="stylesheet" type="text/css" media="all"  href="static/css/jsDatePick_ltr.min.css" />

<script type='text/javascript' src="static/js/default.js"></script>

<title><tiles:insertAttribute name="title" ignore="true" /></title>

</head>
<body>
	<tiles:insertAttribute name="body" ignore="true" />
</body>
</html>
