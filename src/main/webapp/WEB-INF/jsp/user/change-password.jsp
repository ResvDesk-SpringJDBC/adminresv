<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="static/js/validation/user/changepasswordvalidation.js"></script>

<form:form  action="update-change-password.html" id="updatePasswordForm" method="POST" modelAttribute="changePasswordTO"		                                         commandName="changePasswordTO" onsubmit="return validateChangePasswordForm();">
		<h1>Change Password</h1>
		<br/><div >Password can contain <b class="error"> @#$%-_ </b> special characters only!</div>
		<div class="required-indicator">
				<span class="required">*</span>indicates required field
				<input type=hidden id="passwordValidationResponse"/>
				<input type=hidden id="oldPasswordValidationResponse"/>
				<b style="color:red;"> ${changePasswordError} </b>
		</div>
		<div>			
			<table style="border-spacing:5px;border-collapse:separate;">
				<tr>
					<th><label for="userName">User Name</label> </th>
					<td> ${homeBean.adminInfoTO.username}<form:hidden id="userName" path="userName" /> </td>
					<td> <div class="error" id="userNameerror"></div></td>
				</tr>
				<tr>
					<th><span class="required">*</span><label for="oldpassword">Old Password</label> </th>
					<td> <form:password id="oldpassword" path="oldpassword" onchange="validateOldPassword();"/> </td>
					<td> <div class="error" id="oldpassworderror"></div> </td>
				</tr>
				<tr>
					<th><span class="required">*</span> <label for="newpassword">New Password</label> </th>
					<td> <form:password id="newpassword" path="newpassword" onchange="validatePassword();"/> </td>
					<td> <div class="error" id="newpassworderror"></div> </td>
				</tr>
				<tr>
					<th><span class="required">*</span> <label for="conformpassword">Confirm New Password</label> </th>
					<td> <form:password id="conformpassword" path="conformpassword" /> </td>
					<td> <div class="error" id="conformpassworderror"></div> </td>
				</tr>
			</table>
		</div>
		<div class="clear-all"></div>
		<input type="submit" class="btn noWidth" value=" Save " id="btnChangePassword"/>
	</form:form>