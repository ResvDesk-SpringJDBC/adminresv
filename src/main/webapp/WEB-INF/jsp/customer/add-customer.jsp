<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
<title>Add Customer</title>

<script type="text/javascript" src="static/js/validation/customer/customerFormValidation.js"></script>

<!-- pop up starts -->
	<form:form name="customerForm" id="customerForm" commandName="customer" method="post" action="save-customer.html"> 
		<h1>&nbsp;&nbsp;Add Customer</h1>
		<a href="#" class="jqmClose close" title="Close"  id="popupClose">Close</a> 
		
		<div class="popup-content-big">
		<!--Patient Details starts -->
			<div class="popup-box">
			    <div class="error" id="validationResponse"> </div>
				<div class="required-indicator">
					<span class="required">*</span>indicates required field
				</div>
				<div>	
				<table width="100%"  style="border-collapse:separate; border-spacing:1em;">
					<tr>	
					    <c:forEach var="loginFieldDetails" items="${loginInfoResponse.loginFieldList}" varStatus="count">   
							<td>${loginFieldDetails.displayTitle}</td>									 
							<td>
								<c:if test="${loginFieldDetails.displayType == 'textbox'}">
									<form:input type="${loginFieldDetails.displayType}" 
										path="${loginFieldDetails.javaRef}"
										maxlength="${loginFieldDetails.maxLength}"/>
								</c:if>
								<c:if test="${fn:contains(loginFieldDetails.displayType, 'textbox-')}">
										<c:set var="textsplit" value="${fn:split(loginFieldDetails.displayType, '-')}" />
										<c:set var="len" value="${fn:length(textsplit)}" />
										<c:forEach var="i" begin="1" end="${fn:length(textsplit)-1}">
											<form:input type="${textsplit[0]}" path="${loginFieldDetails.javaRef}${i}" 
											    id="phone${loginFieldDetails.javaRef}${i}"
												class="${i == 3 ? 'phone1' : 'phone' }" maxlength="${textsplit[i]}"/>
										</c:forEach>
								</c:if>

								<c:if test="${loginFieldDetails.displayType == 'select'}">
									<c:set var="labelsplit"  value="${fn:split(loginFieldDetails.listLabels, ',')}" />
									<c:set var="valuesplit" 	value="${fn:split(loginFieldDetails.listValues, ',')}" />
									<c:set var="selectVal" 	value="$loginFieldDetails.listInitialValues }" />
									 <form:select path="${loginFieldDetails.javaRef}">
										<form:option value="-1">Select</form:option>
										<c:forEach var="i" begin="0" end="${fn:length(labelsplit)-1}">
											<form:option value="${valuesplit[i]}" selected="${selectVal==valuesplit[i]  ? 'selected' : ''}">${labelsplit[i]}</form:option>
										</c:forEach>
									 </form:select>
								  </c:if>

								  <c:if test="${loginFieldDetails.displayType == 'radio'}">
									  <c:set var="labelsplit"   value="${fn:split(loginFieldDetails.listLabels, ',')}" />
									  <c:set var="valuesplit"  value="${fn:split(loginFieldDetails.listValues, ',')}" />
									  <c:forEach var="i" begin="0" end="${fn:length(labelsplit)-1}">														
											<form:radiobutton  style="margin-right: 20px;width: 40px;" class="noWidth"
												path="${loginFieldDetails.javaRef}" id="${loginFieldDetails.javaRef}" 
												value="${valuesplit[i]}" checked="${valuesplit[i]== loginFieldDetails.listInitialValues ? 'checked' : '' }"/>
												${labelsplit[i]}	
									  </c:forEach>
								  </c:if>
							  </td>
							  <c:if test="${count.index % 2==1}">
								  </tr><tr>
							  </c:if>
							</c:forEach>
						</tr>
					</table>
				</div>
				<div class="clear-all"></div>
			</div>
			<!-- Patient Details ends -->
		</div>
		 
		   <div class="popup-button" id="resvSave">		
				<a href="#" class="btn jqmClose"  id="cancelBtn">Cancel</a>
				&nbsp; 
				<input name="btnSave" type="button" class="btn" value=" Add " id="btnSave" />
		  </div>
		  <div id="addCustomerMsg"  style="text-align:center;"> </div>
	</form:form>
 <!-- pop up ends -->