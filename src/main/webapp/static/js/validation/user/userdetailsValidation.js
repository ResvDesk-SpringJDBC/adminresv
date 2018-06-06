$(document).ready(function () {
	
	  $('#btnAddUser').click(function () { 
        var hasError = validateForm(true);  
        if (hasError) {  
            return false;
        } else {	
			hasError = validateAccessLevels();
			var isvalid = false;
			if(!hasError) { 	
				validateUserName(0);
				var userNameError = $("#userNameError").val();	
				if(userNameError==""){
					validatePassword();
					var response = $("#passwordValidationResponse").val();					
					if(response!=""){
						$("#txtPassword").val('');
						$("#txtConfirmPassword").val('');  
						$("#txtPassword").focus();					
						$('#error').html($('#error').html()+"<br/><b>* "+response+".</b>"); 					 
						isvalid = false;
					}else {
						$('#error').html(" "); 
						isvalid = true;
					}			
				}else{
					$('#error').html("<b>* "+userNameError+".</b>"); 		
					isvalid = false;
				}
				return isvalid;
			}else{
				return isvalid;
			}			
        }
    });

	 $('#btnUpdateUser').click(function () { 
        var hasError = validateForm(false);  
        if (hasError) {  
            return false;
        } else {	
			hasError = validateAccessLevels();
			var isvalid = false;
			if(!hasError) { 	
				    validateUserName($("#id").val());
					var userNameError = $("#userNameError").val();	
					if(userNameError==""){
						validatePassword();
						var response = $("#passwordValidationResponse").val();					
						if(response!=""){
							$("#txtPassword").val('');
							$("#txtPassword").focus();					
							$('#error').html($('#error').html()+"<br/><b>* "+response+".</b>"); 					 
							isvalid = false;
						}else {
							$('#error').html(" "); 
							isvalid = true;
						}			
					}else{
						$('#error').html("<b>* "+userNameError+".</b>"); 		
						isvalid = false;
					}
					return isvalid;
			}else{
				return isvalid;
			}		
        }
    });

	$('#btnUpdatePassword').click(function () { 
		var hasError = false;	
		var txtPassword = $("#txtPassword").val();
        var txtConfirmPassword = $("#txtConfirmPassword").val();
		 if (txtPassword == '') {       
			$('#error').html("<b>* Password cannot be empty.</b>");
			$("#txtConfirmPassword").focus(); 
			$("#txtPassword").focus();
			hasError = true;
         } else if (txtConfirmPassword == '') {   
			hasError = true;
			$('#error').html("<b>* Please re-enter your password.</b>"); 
			$("#txtConfirmPassword").focus();
         } else if (txtPassword != txtConfirmPassword ) {
			hasError = true;
			$("#txtPassword").val('');
			$("#txtConfirmPassword").val('');  
			$("#txtPassword").focus();
			$('#error').html("<b>* Passwords do not match.</b>"); 
        }
        
        if (hasError) {  
            return false;
        } else {				
			var isvalid = false;
			if(!hasError) { 	
				try{
				   validatePassword();
					var response = $("#passwordValidationResponse").val();					
					if(response!=""){
						$("#txtPassword").val('');
						$("#txtConfirmPassword").val('');  
						$("#txtPassword").focus();					
						$('#error').html($('#error').html()+"<br/><b>* "+response+".</b>"); 					 
						isvalid = false;
					}else {
						$('#error').html(" "); 
						isvalid = true;
					}	
				}catch(e){
					isvalid = false;
					//alert("Error : "+e);
				}
				return isvalid;
			}else{
				return isvalid;
			}		
        }
    });
    
    $('#btnCancelUser').click(function () {     
    	$('#txtFirstName').val('');  
        $('#txtLastName').val('');
        $('#txtUserName').val('');
        $("#txtPassword").val('');
        $("#txtConfirmPassword").val('');
        return false;        
    });
    
    function validateForm(passwordAlso){    	
	 	var hasError = false;	
    	var txtFirstName = $('#txtFirstName').val();  
        var txtLastName = $('#txtLastName').val();
        var txtUserName = $('#txtUserName').val();
        var txtPassword = $("#txtPassword").val();
        var txtConfirmPassword = $("#txtConfirmPassword").val();
		var contactEmail = $('#contactEmail').val();
		if (txtFirstName == '') {        	
        	hasError = true;        	
            $('#error').html("<b>* First name cannot be empty.</b>");   
            $("#txtFirstName").focus(); 
        } else if (txtLastName == '') {        
        	hasError = true;
            $('#error').html('<b>* Last name cannot be empty.</b>');  
            $("#txtLastName").focus();            
        } else if (txtUserName == '') {        	  
        	hasError = true;
            $('#error').html('<b>* User name cannot be empty.</b>');  
            $("#txtUserName").focus();
        } else if (txtPassword == '') {       
			if(passwordAlso){
				$('#error').html("<b>* Password cannot be empty.</b>");
				$("#txtConfirmPassword").focus(); 
				$("#txtPassword").focus();
				hasError = true;
			}
        } else if (txtConfirmPassword == '') {   
			if(passwordAlso){
				hasError = true;
				$('#error').html("<b>* Please re-enter your password.</b>"); 
				$("#txtConfirmPassword").focus();      
			}
        } else if (txtPassword != txtConfirmPassword ) {
			if(passwordAlso){
				hasError = true;
				$("#txtPassword").val('');
				$("#txtConfirmPassword").val('');  
				$("#txtPassword").focus();
				$('#error').html("<b>* Passwords do not match.</b>");        
			}
        }else if (contactEmail == '' ) {
        	hasError = true;
        	$('#error').html("<b>* Contact Email cannot be empty.</b>");             
        }else if (contactEmail != '' ) {
			 var pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
			 if(!pattern.test(contactEmail)){
				hasError = true;
				$('#error').html("<b>* Please provide valid Contact Email.</b>");    
			 }
        }
		return hasError;
    }
    
});

	function validatePassword(){
		 var txtUserName = $('#txtUserName').val();
         var txtPassword = $("#txtPassword").val();
		 var txtConfirmPassword = $("#txtConfirmPassword").val();
		
		 if(txtUserName != ''){
			 if (txtPassword != "") {
				 txtPassword = txtPassword.replace("%","%25"); 
				 txtPassword = txtPassword.replace("#","%23"); 
				 txtPassword = txtPassword.replace("-","%2D");
				 var url = "validatePassword.html?username="+txtUserName+"&password="+txtPassword;
				 $.get(url,function(data) {						
						$("#passwordValidationResponse").val(data);
						if(data!=""){
							$('#error').html("<b>* "+data+".</b>"); 
						}else{
							$('#error').html(""); 
						}
				 });
			 }else {
				 $('#error').html("<b>* Password cannot be empty.</b>");
        		 $("#txtPassword").focus();
			 }
		 }else{
			$('#error').html('<b>* User name cannot be empty.</b>');  
            $("#txtUserName").focus();
		 }
	}
	
	function validateUserName(ids){
		 var username = $('#txtUserName').val();
		
		 if(username != ''){
			var url = "validateUserName.html?username="+username+"&id="+ids;
			 $.get(url,function(data) {
					if(data=="NO"){
						$("#userNameError").val("User Name already exists! Please choose different username.");
						$('#error').html("<b>* User Name already exists! Please choose different username.</b>"); 
					}else{
						$('#error').html(""); 
						$("#userNameError").val("");
					}
			 });
		 }else{
			$('#error').html('<b>* User name cannot be empty.</b>');  
			$("#userNameError").val("<b>* User name cannot be empty.</b>");
            $("#txtUserName").focus();
		 }
	}

	function enableLocationDropDown(){
		$("#resourceIds").hide();
		$("#locationIds").show();
	}

	function enableResourceDropDown(){
		$("#resourceIds").show();
		$("#locationIds").hide();
	}

	function hideLocationAndResourceDropDowns(){
		$("#resourceIds").hide();
		$("#locationIds").hide();
	}


	function validateAccessLevels(){
		var hasError = false;
		var accessLevel = $("input:radio[name='accessLevel']:checked").val();
		var locationIds = $('#locationIds').val();
		var resourceIds = $('#resourceIds').val();
		var locationPrivilage = $('#locationPrivilage').val();
		var resourcePrivilage = $('#resourcePrivilage').val();		
	
		if(undefined==accessLevel){
			hasError = true;
			$('#error').html("<b>* Please select Administrative Privilege.</b>");
		}else if(accessLevel==locationPrivilage){		
			if(undefined==locationIds || locationIds==" "){
				hasError = true;
				$('#error').html("<b>* Please select "+locationPrivilage+".</b>");
			}
		}else if(accessLevel==resourcePrivilage){
			if(undefined==resourceIds || resourceIds==" "){
				hasError = true;
				$('#error').html("<b>* Please select "+resourcePrivilage+".</b>");
			}
		}
		return hasError;
	}