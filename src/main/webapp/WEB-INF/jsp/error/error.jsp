<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
<link href="static/css/global.css" rel="stylesheet" type="text/css">
<!--
<link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:300,300italic,400,400italic,600,600italic,700,700italic">
-->
</head>

<body>
<!-- Header starts -->
<header id="branding">
  <div class="top-line"></div>
  <div id="logo"><a href="javascript:donothing();"><img  src="/logo/logo.png" height="60"></a></div>
  <div id="navbar">
    <div class="adminloginText">Error</div>
  </div>
</header>
<!-- Header ends --> 
<!-- Body starts -->
<div id="main" >
  <div class="generalContainer" >
    <h2>Error Occurred - Re-login</h2>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
        <td width="25%" valign="top"><img src="static/images/error.png"></td>
        <td width="75%" style="vertical-align:top">
		Appointment Scheduling is unexpectedly interrupted. <br/>
		Please contact ITFrontDesk Support <a href="mailto:support@itfrontdesk.com?Subject=Error%20with%20Apptadmin" target="_top">Send Mail</a> with your name, contact phone and/or email address and how you got this problem.  <br/><br/>
		<c:if test="${errorBean!=null}">
			Also please include the ClientCode,ErrorCode and TransID which you received. <br/>
			ClientCode : ${errorBean.clientCode}<br/>
			ErrorCode: ${errorBean.errorCode}<br/>
			<!-- Error Description: ${errorBean.errorDescription}<br/> -->
			TransID:${errorBean.transId}<br/>					
		<!--
		AppCode : ${errorBean.appCode}<br/>
		ErrorCode : ${errorBean.errorCode}<br/>
		Display Text: ${errorBean.displayText}
		-->
		</c:if>
          <div class="center-align">
            <input type="button" value="Log In" class="btn" style="margin-top:40px;" onClick="window.location='login.html'">
          </div></td>
      </tr>
    </table>
  </div>
</div>
<!-- Body ends -->
</body>
</html>
