<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript" src="static/js/validation/user/userdetailsValidation.js"></script>
<title>Change Password</title>

<form:form action="update-user-password.html" modelAttribute="resetPasswordTO" method="POST" name="addUser" id="addUser" commandName="resetPasswordTO">
		<h1>&nbsp;&nbsp;Change Password</h1>
		<a href="#" class="jqmClose close" title="Close">Close</a>
		<div class="popup-content">

			<!--User Details starts -->
			<div class="popup-box">
				<div class="error">Password should contain :
					${homeBean.adminInfoTO.passwordResetAlgorithm}</div>
				<br />
				<div>
					Password can contain <b class="error"> @#$%-_ </b> special
					characters only!
				</div>
				<div class="error" id="error"></div>			
				<div class="required-indicator">
					<span class="required">*</span>indicates required field
				</div>
				<div id="hdnElements">
					<form:hidden path="userId" />
				</div>
				<div>
					<dl>						
						<dt>
							<span class="required">*</span>Password
						</dt>
						<dd>
							<form:password path="newpassword" name="newpassword"
								id="txtPassword" onchange="validatePassword();" />
								<input type="hidden" name="passwordValidationResponse"
							id="passwordValidationResponse" />
							<span class="errorTxtPassword"></span>
						</dd>
						<dt>
							<span class="required">*</span>Re-enter Password
						</dt>
						<dd>
							<input type="password" name="txtConfirmPassword"
								id="txtConfirmPassword" />
						</dd>					
					</dl>
				</div>
				<div class="clear-all"></div>
			</div>
			<!-- User Details ends -->
		</div>
		<div class="popup-button">
				<a href="#" class="btn jqmClose">Cancel</a>
				&nbsp;      
				<input type="submit" class="btn noWidth" value="Update Password"  id="btnUpdatePassword" />
		</div>
	</form:form>