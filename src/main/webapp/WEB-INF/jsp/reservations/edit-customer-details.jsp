<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<title>Edit Customer</title>

<script type="text/javascript">
	$().ready(function() {
		$('#doneBtn').hide();

		$('#btnSaveId').show();
		$('#cancelBtn').show();
		$('#btnCommentsSaveId').hide();

		//Auto Tab
		$("input[class='phone']").bind('keyup', function() {
			var limit = parseInt($(this).attr('maxlength'));  
			var text = $(this).val();  			
			var chars = text.length; 
			//check if there are more characters then allowed
			if(chars >=limit){  	
				$("#"+($(this).next().attr("id"))).focus();
			}
		});	
	});
</script>

	<form:form name="editCustomerForm" id="editCustomerForm" commandName="resvBookingForm" method="post">
		<h1>&nbsp;&nbsp;Edit Customer</h1>

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
					    <input type="hidden" id="editCustomerId" value="${resvBookingForm.customer.customerId}"/>

						<form:hidden path="customer.customerId" id="customerId"/>
						<c:forEach var="loginFieldDetails" items="${loginInfoResponse.loginFieldList}" varStatus="count">   
							<td>${loginFieldDetails.displayTitle}</td>									 
							<td>
								<c:if test="${loginFieldDetails.displayType == 'textbox'}">
									<form:input type="${loginFieldDetails.displayType}" 
										path="customer.${loginFieldDetails.javaRef}"
										maxlength="${loginFieldDetails.maxLength}"/>
								</c:if>
								<c:if test="${fn:contains(loginFieldDetails.displayType, 'textbox-')}">
										<c:set var="textsplit" value="${fn:split(loginFieldDetails.displayType, '-')}" />
										<c:set var="len" value="${fn:length(textsplit)}" />
										<c:forEach var="i" begin="1" end="${fn:length(textsplit)-1}">
											<form:input type="${textsplit[0]}" path="customer.${loginFieldDetails.javaRef}${i}" 
											    id="phone${loginFieldDetails.javaRef}${i}"
												class="${i == 3 ? 'phone1' : 'phone' }" maxlength="${textsplit[i]}"/>
										</c:forEach>
								</c:if>

								<c:if test="${loginFieldDetails.displayType == 'select'}">
									<c:set var="labelsplit"  value="${fn:split(loginFieldDetails.listLabels, ',')}" />
									<c:set var="valuesplit" 	value="${fn:split(loginFieldDetails.listValues, ',')}" />
									<c:set var="selectVal" 	value="$loginFieldDetails.listInitialValues }" />
									 <form:select path="customer.${loginFieldDetails.javaRef}">
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
												path="customer.${loginFieldDetails.javaRef}" id="${loginFieldDetails.javaRef}" 
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
	</form:form>