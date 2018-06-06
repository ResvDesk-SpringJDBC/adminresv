<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@taglib uri="http://displaytag.sf.net" prefix="display"%>

<title>Users</title>

<script type="text/javascript">
$().ready(function() {	
	  $('#ex2').jqm({ajax: 'add-user.html',modal: true, trigger: 'a.ex2trigger'}); 
	  $('.edit').click(function() {	
		  var id = $(this).attr('id');
		  //alert("ID --------> "+id);
		  $('#ex3').jqm({ajax: 'edit-user.html?id='+id,modal: true, trigger: 'a.ex3trigger'}); 
	  });
	   $('.changePassword').click(function() {	
		  var id = $(this).attr('id');
		  //alert("ID --------> "+id);
		  $('#ex4').jqm({ajax: 'change-user-password.html?id='+id,modal: true, trigger: 'a.ex4trigger'}); 
	  });
});
</script>
<!-- Body starts -->

	<h1>Users</h1>
    
    <!--Options starts -->
    <div class="options">
      <div class="float-left txt-bold">User Details</div>
	  <%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'add_user')}"> --%>
			<div class="float-right"> 
				<a href="javascript:doNothing()" class="btn ex2trigger">Add User</a> 
			</div>
	 <%-- </c:if> --%>
      <div class="clear-all"></div>
    </div>
    <!--Options ends --> 
   
    <!-- Main tables starts -->	
	<c:set var="superUserPrivilages" value="false"/>
	<c:if test="${homeBean.adminInfoTO.accessLevel == 'Super User' || homeBean.adminInfoTO.accessLevel == 'Administrator'}">
		<c:set var="superUserPrivilages" value="true"/>
	</c:if>

	<display:table id="adminInfoTO" name="adminInfoTOs" export="true" requestURI="" pagesize="25" class="main-table">
		
			<display:column property="firstName" title="First Name" sortable="true"> </display:column>
			<display:column property="lastName" title="Last Name" sortable="true"> </display:column>
			<display:column property="userName" title="User Name" sortable="true"> </display:column>
			<display:column property="accessLevel" title="Access level" sortable="true"> </display:column>
			<display:column property="privalageDetails" title="" sortable="true"> </display:column>
			
			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'edit_user')}"> --%>
				<display:column title="Edit" media="html"> 
					<c:if test="${superUserPrivilages || homeBean.adminInfoTO.username == adminInfoTO.userName }">
					   <a href="javascript:doNothing()" class="ex3trigger" id="${adminInfoTO.id}">
							<img src="static/images/edit.png" width="16" height="16" alt="Edit" title="Edit" id="${adminInfoTO.id}" class="edit">
					   </a>
					</c:if>
				</display:column>
			<%-- </c:if> --%>

			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'changepassword')}"> --%>
				<display:column title="Change Password" media="html"> 
					<c:if test="${superUserPrivilages || homeBean.adminInfoTO.username == adminInfoTO.userName }">
					   <a href="javascript:doNothing()" class="ex4trigger" id="${adminInfoTO.id}">
							<img src="static/images/padlock40.png"  alt="Change Password" title="Change Password" id="${adminInfoTO.id}" 
							class="changePassword">
					   </a>
					</c:if>
				</display:column>
			<%--</c:if> --%>

			<%-- <c:if test="${fn:contains(homeBean.privilegedPageNames,'delete_user')}"> --%>
				<display:column title="Delete" media="html"> 
					<c:if test="${( homeBean.adminInfoTO.accessLevel == 'Super User' )	&& ( adminInfoTO.accessLevel != 'Super User')}">
						<a href="<c:url value="delete-user.html?id=${adminInfoTO.id}" />" id="deleteUser">
								<img src="static/images/delete.png" width="16" height="16" alt="Delete" title="Delete" >
						 </a>
					</c:if>
					<c:if test="${( homeBean.adminInfoTO.accessLevel == 'Administrator' )
								&& ( adminInfoTO.accessLevel != 'Super User' &&  adminInfoTO.accessLevel != 'Administrator')}">
						<a href="<c:url value="delete-user.html?id=${adminInfoTO.id}" />" id="deleteUser">
								<img src="static/images/delete.png" width="16" height="16" alt="Delete" title="Delete" >
						 </a>
					</c:if>
				</display:column>
			<%--</c:if> --%>

		<display:setProperty name="export.excel.filename" value="Users.xls" />
		<display:setProperty name="export.csv.filename" value="Users.csv" />
		<display:setProperty name="export.pdf.filename" value="Users.pdf" />
		<display:setProperty name="export.xml.filename" value="Users.xml" />
		<display:setProperty name="export.rtf.filename" value="Users.rtf" />
	</display:table>
    <!-- Main tables ends --> 

<!-- Body ends --> 

<!--Pop up section starts -->
<div class="jqmWindow" id="ex2"> Please wait... </div>
<div class="jqmWindow" id="ex3"> Please wait... </div>
<div class="jqmWindow" id="ex4"> Please wait... </div>
<!--Pop up section ends -->