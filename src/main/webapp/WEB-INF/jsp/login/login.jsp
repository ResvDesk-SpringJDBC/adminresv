<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
<title>Login</title>
<meta charset="UTF-8">
</head>

<body>
<!-- Body starts -->
  <form:form name="loginAdminApp" action="log-in-auth.html" id="loginAdminApp" modelAttribute="loginForm" method="POST"  >
    <div class="loginMainContainer">
      <h2>Login</h2>
      <div class="loginContent">
	  <b style="color:red;"> ${changePasswordResponse} </b>
	   <b style="color:red;"> ${resetPasswordResponse} </b>
	    <b style="color:red;"> ${useLoginResponse} </b>
      	<c:if test="${not empty errorMessage}">
			<div class="error">				
				<ul><li>${errorMessage}</li></ul>
			</div>
		</c:if>
        <label for="txtUserName">User Name</label>
        <form:input id="txtUserName" name="txtUserName" path="username"/>       
        <div class="clear-all"></div>
        <label for="txtPassword">Password</label>
        <form:password name="txtPassword" id="txtPassword" path="password"/>    
		<form:hidden id="ipAddress" path="ipAddress"/>
        <div>
          <div class="float-left">
            <form:checkbox path="keepMeLoggedIn"  id="keepMeLoggedIn" class="noWidth"/>
            keep me Logged In</div>
          <div class="float-left">
         <!-- <a href="#" class="btn ex2trigger" id="log-in-auth" >Login</a>-->
           <input type="submit" class="btn noWidth" value="Submit"> 
          </div>
          <div class="clear-all"></div>
        </div>
      </div>
    <div style="font-size: 12px; padding-top: 10px;"> 
		This site is designed with latest standards supported by 
		<a href="javascript:donothing()" onClick="window.open('https://www.google.com/intl/en-US/chrome/browser/');" title="Click to download" style="text-decoration:none">Google Chrome</a>, 
		<a href="javascript:donothing()" onClick="window.open('https://www.mozilla.org/en-US/firefox/new/');" title="Click to download" style="text-decoration:none">Mozila Firefox</a>, 
		<a href="javascript:donothing()" onClick="window.open('http://support.apple.com/kb/dl1531');" title="Click to download" style="text-decoration:none">Apple Safari</a> and 
		<a href="javascript:donothing()" onClick="window.open('http://windows.microsoft.com/en-in/internet-explorer/download-ie');" title="Click to download" style="text-decoration:none">Microsoft Internet Explorer (IE)</a>. 
		If you are using IE 9.0 or below, you may need to upgrade to latest version for best viewing of this site.
	</div>
    </div>
	 </form:form>

	<form name="resetPasswordForm" action="sendResetPasswordRequest.html" id="resetPasswordForm" method="POST">

    <div class="loginMainContainer loginMainContainer-last">
      <h2>Can't Login</h2>
      <div class="loginContent">
		   <p>Simply enter your username in the below box and we will send password reset information to the email address associated with the account</p>
		 <label for="txtEmail">User Name</label>
		 <input type="text" name="usename"   id="usename">
       <div>
         <input type="submit" class="btn noWidth" value="Submit" id="authLogin" style="margin-left:340px" >
        </div>
      </div>
    </div>
    <div class="clear-all"></div>
 </form>

<!-- Body ends --> 

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<!--Pop up section ends -->
</body>
</html>
