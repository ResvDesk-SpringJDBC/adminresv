<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script type="text/javascript" src="static/js/validation/user/userdetailsValidation.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#resourceIds").hide();
		$("#locationIds").hide();
	});
</script>

<form:form action="save-user.html" method="POST" name="addUser" id="addUser" modelAttribute="adminInfoTOs">
	<h1>&nbsp;&nbsp;Add User</h1>
	<a href="#" class="jqmClose close" title="Close">Close</a>
	<div class="popup-content-big">
		<!--User Details starts -->
		<div class="popup-box">
			<div class="error">Password should contain :
				${homeBean.adminInfoTO.passwordResetAlgorithm}</div>
			<br />
			<div>
				Password can contain <b class="error"> @#$%-_ </b> special characters only!
			</div>
			<div class="error" id="error"></div>
			<input type="hidden" id="userNameError"/>
			<div class="required-indicator">
				<span class="required">*</span>indicates required field
			</div>
			<div>
				<dl>
					<dt>
						<span class="required">*</span>First Name
					</dt>
					<dd>
						<form:input path="firstName" name="txtFirstName" id="txtFirstName" />
						<span id="errorTxtFirstName" class="errorTxtFirstName"></span>
					</dd>
					<dt>
						<span class="required">*</span>Last Name
					</dt>
					<dd>
						<form:input path="lastName" name="txtLastName" id="txtLastName" />
						<div class="errorTxtLastName"></div>
					</dd>
					<dt>
						<span class="required">*</span>User Name
					</dt>
					<dd>
						<form:input path="userName" name="txtUserName" id="txtUserName"  onchange="validateUserName('0');" />
						<span class="errorTxtUserName"></span>
					</dd>
					<dt>
						<span class="required">*</span>Password
					</dt>
					<dd>
						<form:password path="password" name="txtPassword" id="txtPassword" onchange="validatePassword();" />
						<span class="errorTxtPassword"></span>
					</dd>
					<dt>
						<span class="required">*</span>Re-enter Password
					</dt>
					<dd>
						<input type="password" name="txtConfirmPassword" id="txtConfirmPassword" />
					</dd>
					<dt>
						<span class="required">*</span>Contact Email
					</dt>
					<dd>
						<form:input path="contactEmail" id="contactEmail" />
					</dd>
					<dt class="clear-all">
						<span class="required">*</span>Administrative Privilege 
					</dt>
					<input type="hidden" name="locationPrivilage" id="locationPrivilage" value="Location" />
					<input type="hidden" name="resourcePrivilage" id="resourcePrivilage" value="Provider" />
					<input type="hidden" name="passwordValidationResponse" id="passwordValidationResponse" />

					<dd class="noWidth">
						<c:forEach items="${privilageResponse.privilageList}" var="accessPrivilege">
							<c:if test="${accessPrivilege.privilageName!='Location' && accessPrivilege.privilageName!='Provider'}">
								<form:radiobutton path="accessLevel" value="${accessPrivilege.privilageName}"
									class="noWidth" onchange="hideLocationAndResourceDropDowns()" />&nbsp; ${accessPrivilege.privilageName}<br/>
							</c:if>
							<c:if  test="${accessPrivilege.privilageName=='Location'}">
								<form:radiobutton path="accessLevel" value="${accessPrivilege.privilageName}"
									class="noWidth" onchange="enableLocationDropDown()" />&nbsp; ${accessPrivilege.privilageName}
								<br/>
								<form:select path="locationIds" class="noWidth" id="locationIds"  multiple="multiple" style="height:100%;"
									size="${fn:length(locationListResponse.locationList) gt 8 ? (fn:length(locationListResponse.locationList)/2) : 5}">
									<c:forEach items="${locationListResponse.locationList}" var="location" varStatus="loop">
										<form:option value="${location.locationId}">${location.locationName}</form:option>
									</c:forEach>
								</form:select>
								<br/>
							</c:if>
							<c:if test="${accessPrivilege.privilageName=='Provider'}">
								<form:radiobutton path="accessLevel" value="${accessPrivilege.privilageName}" class="noWidth" 
										onchange="enableResourceDropDown()" />&nbsp; ${accessPrivilege.privilageName}
								<br />
								<form:select path="resourceIds" class="noWidth" id="resourceIds" multiple="multiple" style='height: 100%;' 
									size="${fn:length(eventListResponse.eventList) gt 8 ? (fn:length(eventListResponse.eventList)/2) : 5}">
									<c:forEach items="${eventListResponse.eventList}" var="event" varStatus="loop">
										<form:option value="${event.eventId}">${event.eventName}</form:option>
									</c:forEach>
								</form:select>
								<br/>
							</c:if>
						</c:forEach>
					</dd>
				</dl>
			</div>
			<div class="clear-all"></div>
			<span ><b style="color:red;">Note : </b><i>Multi Select Combo Box: Press & Hold CTRL key while clicking items</i></span>
		</div>
		<!-- User Details ends -->
	</div>
	<div class="popup-button">			
		<a href="#" class="btn jqmClose">Cancel</a> &nbsp;     
		<input type="submit" class="btn noWidth" value="Add User" 	id="btnAddUser" /> 
	</div>
</form:form>