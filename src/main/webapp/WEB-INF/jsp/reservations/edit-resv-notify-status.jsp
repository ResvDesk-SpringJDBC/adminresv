<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Appointment Details</title>

<link href="<%=request.getContextPath()%>/static/css/global.css"	 rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/static/css/jqModal.css"	rel="stylesheet" type="text/css">
<link	href="<%=request.getContextPath()%>/static/css/jsDatePick_ltr.min.css"	rel="stylesheet" type="text/css">

<script type="text/javascript">
	function validateForm() {
		try{
			//var status = document.getElementById('notifyPhoneStatus').value;
			//if ('NO_INPUT' == status) {
			//	alert("Select status other than No Input");
			//	return false;
			//}
			var notes = $("#notes").val();
			//alert(" Notes :::: "+notes);
			if (notes.length>1000) {
				alert("Notes length should be greater than 1000 characters");
				return false;
			}
		}catch(e){
			//alert("Error :::: "+e);
			return false;
		}
		return true;
	}
	function updateNotifyStatus(){
		if(validateForm()){
			var url = "update-resv-notify-status.html";
			var extraData = $("#updateNotificationStatus").serialize();
			url = url+"?"+extraData;
			$.post(url,function(data) {
				if(data=="SUCESSES"){		
					$("#statusChangeError").html("");
					$('#changedParameter').val("JsCalender");
					$('#resvReminders').submit();	
				}else{
					$("#statusChangeError").html("Error while updating notify status!");
				}
			});
		}
	}
</script>

	<form:form action="update-resv-notify-status.html" method="POST" id="updateNotificationStatus" modelAttribute="notifyRequest">
		<h1>&nbsp;&nbsp;Update Notify Status</h1>
		<a href="#" class="jqmClose close" title="Close">Close</a>
		<div class="popup-content">
			<div class="error" id="statusChangeError"></div>
			
			<!--Appointment Details starts -->
			<div class="popup-box">
				<form:hidden path="notifyId"/>
				<form:hidden path="notifyStatus"/>
				<div>
					<dl>
						<dt>First Name : </dt>
						<dd>
							<div class="support-label">${firstName}</div>
						</dd>
						<dt>Last Name : </dt>
						<dd>
							<div class="support-label">${lastName}</div>
						</dd>
						<dt class="clear-all">Status:</dt>
						<dd>
							<form:select path="notifyReminderStatusId" id="notifyReminderStatusId">
								<%--
								<form:options items="${reminderStatusResponse.reminderStatusList}" itemLabel="statusKey" itemValue="notifyPhoneStatus"></form:options>
								--%>
								<c:forEach var="reminderStatus" items="${reminderStatusResponse.reminderStatusList}">
									<form:option value="${reminderStatus.reminderStatusId}" 
									selected="${notifyRequest.notifyPhoneStatus==reminderStatus.notifyPhoneStatus ? 'selected' : ''}">${reminderStatus.statusKey}</form:option>
								</c:forEach>
								 
							</form:select>
						</dd>						 
						<dt>Notess :</dt>
						<dd>
							<form:textarea path="notes" rows="5" cols="20" />	
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
			<input type="button" class="btn" name="btnSave" value="Save" id="btnUpdateNotifyStatus" onclick="updateNotifyStatus();"/>
		</div>
	</form:form>