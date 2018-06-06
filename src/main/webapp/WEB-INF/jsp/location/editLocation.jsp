<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<title>Edit Location</title>

<script type="text/javascript" src="static/js/validation/location/locations.js?version=1.0"></script>

	<form:form name="locationForm" id="locationForm" commandName="location" action="update-location.html" method="post">	
	
		<h1>&nbsp; &nbsp;Edit Location</h1>
		<a href="#" class="jqmClose close" title="Close">Close</a>
		<div class="popup-content">

			<!--Location Details starts -->
			<div class="popup-box">
				<div class="">
					<div class="required-indicator">
						<span class="required">*</span>indicates required field
					</div>
					<div class="error" id="errors"></div>
					<div>
						<dl>
							<dt><span class="required">*</span>Location Name</dt>
							<dd>
								<form:hidden path="locationId" />
								<form:input path="locationName" id="locationName" maxlength="100" />

							</dd>
							<dt>Street Address</dt>
							<dd>
								<form:input path="address" id="address" maxlength="60" />
							</dd>
							<dt>City</dt>
							<dd>
								<form:input path="city" id="city" maxlength="30" />
							</dd>
							<dt>State</dt>
							<dd>
								<form:select path="state"  id="state">
									<form:options items="${states}" />
								</form:select>
							</dd>
							<dt>Time Zone</dt>
							<dd>
								<form:select path="timeZone"  id="timeZone">
									<form:option  value="US/Alaska">US/Alaska</form:option>
									<form:option  value="US/Aleutian">US/Aleutian</form:option>
									<form:option  value="US/Arizona">US/Arizona</form:option>
									<form:option  value="US/Central">US/Central</form:option>
									<form:option  value="US/East-Indiana">US/East-Indiana</form:option>
									<form:option  value="US/Eastern">US/Eastern</form:option>
									<form:option  value="US/Hawaii">US/Hawaii</form:option>
									<form:option  value="US/Indiana-Starke">US/Indiana-Starke</form:option>
									<form:option  value="US/Michigan">US/Michigan</form:option>
									<form:option  value="US/Mountain">US/Mountain</form:option>
									<form:option  value="US/Pacific">US/Pacific</form:option>
									<form:option  value="US/Pacific-New">US/Pacific-New</form:option>
									<form:option  value="US/Samoa">US/Samoa</form:option>
									<form:option  value="Canada/Atlantic">Canada/Atlantic</form:option>
									<form:option  value="Canada/Central">Canada/Central</form:option>
									<form:option  value="Canada/East-Saskatchewan">Canada/East-Saskatchewan</form:option>
									<form:option  value="Canada/Eastern">Canada/Eastern</form:option>
									<form:option  value="Canada/Mountain">Canada/Mountain</form:option>
									<form:option  value="Canada/Newfoundland">Canada/Newfoundland</form:option>
									<form:option  value="Canada/Pacific">Canada/Pacific</form:option>
									<form:option  value="Canada/Saskatchewan">Canada/Saskatchewan</form:option>
									<form:option  value="Canada/Yukon">Canada/Yukon</form:option>
								</form:select>
							</dd>
							<dt>ZIP</dt>
							<dd style="width:200px; margin-right:20px;">
								<form:input path="zip1" class="phone1" id="zip1" maxlength="5" /> 
								<form:input path="zip2" class="phone1" id="zip2" maxlength="4" />
							</dd>
							<dt>Work Phone</dt>
							<dd>
								<form:input path="workPhone1" class="phone" id="workPhone1" maxlength="3" /> 
								<form:input path="workPhone2" class="phone" id="workPhone2" maxlength="3" /> 
								<form:input path="workPhone3" class="phone1" id="workPhone3" maxlength="4" />
							</dd>
							<%--
							<form:hidden path="procedureLocationId" id="procedureLocationId"/>
							<c:if test="${fn:length(procedureTOList) >1}">
								<dt>Select ${homeBean.displayNamesTO.procedure_name}</dt>
								<dd>
									<form:select path="procedureId" id="procedureId">
										<c:forEach items="${procedureTOList}" var="procedure" varStatus="loop">
												<form:option value="${procedure.id}">${procedure.procedure_name_online}</form:option >
										</c:forEach>
									</form:select>
								</dd>
							</c:if>
							<c:if test="${fn:length(procedureTOList) ==1}">
								<form:hidden path="procedureId" id="procedureId" value="${procedureTOList[0].id}"/>
							</c:if>
							<c:if test="${fn:length(procedureTOList) ==0}">
								<form:hidden path="procedureId" id="procedureId" value="-1"/>
							</c:if>
							--%>
						</dl>
					</div>
					<div class="clear-all"></div>
				</div>
				<!-- Location Details ends -->
			</div>
			<div class="popup-button">				
				<a href="#" class="btn jqmClose">Cancel</a>
				 &nbsp; 
				<input name="btnSave" type="button" class="btn" value="Save" id="editlocationSaveBtn">
			</div>
			<!-- pop up ends -->
		</div>
	</form:form>