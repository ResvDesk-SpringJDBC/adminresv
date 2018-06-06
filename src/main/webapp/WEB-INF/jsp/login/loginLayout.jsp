<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" />
</title>
</head>
<body>
	<div id="branding">
		<tiles:insertAttribute name="header" ignore="true"/>		
	</div>
	<div id="main">
		<tiles:insertAttribute name="body" ignore="true"/>
	</div>	
</body>
</html>