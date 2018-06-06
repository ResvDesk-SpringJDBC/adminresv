<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<title>Close ${homeBean.displayNamesTO.location_name}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<script type="text/javascript" src="static/js/locations.js"></script>
<script type="text/javascript" src="static/js/validation/fileSizeValidation.js"></script>
<script type="text/javascript">
	//var contextPath="<%=request.getContextPath() %>";
	window.onload = function(){
		
	};
</script>

</head>

<body>
	<form:form id="closeLocation" commandName="locationClosedForm" action="updateLocationClosedDetails.html" method="post" onsubmit="return validateLocationClosedDetailsForm();"  enctype="multipart/form-data">
		<h1>&nbsp; &nbsp;Close ${homeBean.displayNamesTO.location_name}</h1>
		<a href="#" class="jqmClose close" title="Close">Close</a>
		<div class="popup-content">
				<div class="error" id='closedMessage_error'></div>
				<div class="error" id="closedAudio_error"></div>
				<div class="error" id="closedTTS_error"></div>
			<!--Location Details starts -->
			<div class="popup-box">
				<div class="popup-box-head">
					<div class="required-indicator">
						<span class="required">*</span>indicates required field
					</div>
					<div class="error" id="errors"></div>
					<div>
						<dl>
							<dt>
								<span class="required">*</span>${homeBean.displayNamesTO.location_name} Name
							</dt>
							<dd>
								<form:hidden path="locationId" />
								${locationClosedForm.locationName}
							</dd>
							<dt class="clear-all">
								Closed Message
							</dt>
							<dd>
								<form:textarea class="box" path="closedMessage" id="closedMessage" rows="5" cols="30" />
							</dd>
							<dt class="clear-all">
								Existing Audio file
							</dt>
							<dd>
								${locationClosedForm.closedAudioFileName}
								<form:hidden  path="existingAudioFilePath"  id="existingAudioFilePath" />   
							</dd>
							<dt class="clear-all">
								Closed Audio
							</dt>
							<dd>
								<form:input type="file" path="closedAudio"  id="closedAudio" />   
							</dd>
							<dt class="clear-all">
								Closed TTS
							</dt>
							<dd>
								<form:input path="closedTTS" id="closedTTS" maxlength="100" />
							</dd>
						</dl>
					</div>
					<div class="clear-all"></div>
				</div>
				<!-- Location Details ends -->
			</div>
			<div class="popup-button">				
				<a href="#" class="btn jqmClose">Cancel</a>
				 &nbsp; 
				<input name="btnSave" type="submit" class="btn" value="Save">
			</div>

			<!-- pop up ends -->
		</div>
	</form:form>
</body>
</html>
