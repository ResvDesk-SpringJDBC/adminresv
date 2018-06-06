	function validatePassword(){
		 var userName = $('#userName').val();
		 var oldpassword = $("#oldpassword").val();
         var newpassword = $("#newpassword").val();
		 //alert("userName ------validatePassword 77------> "+userName);
		 if(userName != ''){
			 $('#userNameerror').html(' '); 
			 if (oldpassword != "") {
				 $('#oldpassworderror').html(' '); 
				 if (newpassword != "") {
					 $('#newpassworderror').html(' '); 
					 if (oldpassword != newpassword) {
						 newpassword = newpassword.replace("%","%25"); 
						 newpassword = newpassword.replace("#","%23"); 
						 newpassword = newpassword.replace("-","%2D");
						 var url = "validatePassword.html?username="+userName+"&password="+newpassword;
						 //alert("url ------------> "+url);		
						 $.get(url,function(data) {
								//alert( "Data ---------------> "+data);
								$("#passwordValidationResponse").html(data);
								if(data!=""){
									$('#newpassworderror').html("<b>* "+data+".</b>"); 
								}else{
									$('#newpassworderror').html( ); 
									$("#passwordValidationResponse").html('');
								}
						 });
					}else {
						$('#newpassworderror').html("<b>* Old and New Passwords cannot be same.</b>");
						$("#oldpassword").focus();
					}
				 }else {
					 $('#newpassworderror').html("<b>* New Password cannot be empty.</b>");
					 $("#newpassword").focus();
				 }
			}else {
				 $('#oldpassworderror').html("<b>* Old Password cannot be empty.</b>");
        		 $("#oldpassword").focus();
			}
		 }else{
			$('#userNameerror').html('<b>* User name cannot be empty.</b>');  
            $("#userName").focus();
		 }
	}

	function validateOldPassword(){
		 var userName = $('#userName').val();
         var oldpassword = $("#oldpassword").val();
		 //alert("userName ------validateOldPassword 7------> "+userName);		
		 if (oldpassword != "") {
			 oldpassword = oldpassword.replace("%","%25"); 
			 oldpassword = oldpassword.replace("#","%23"); 
			 oldpassword = oldpassword.replace("-","%2D");
			 var url = "validateoldpassword.html?userName="+userName+"&oldpassword="+oldpassword;
			 //alert("url ------------> "+url);		
			 $.get(url,function(data) {
					//alert( "Data ---------------> "+data);
					$("#oldPasswordValidationResponse").html(data);
					if(data!=""){
						$('#oldpassworderror').html("<b>* "+data+".</b>"); 
					}else{
						$('#oldpassworderror').html(""); 
						$("#oldPasswordValidationResponse").html("");
					}
			 });
		 }else {
			 $('#oldpassworderror').html("<b>* Old Password cannot be empty.</b>");
			 $("#oldpassword").focus();
		 }
	}

	function validateChangePasswordForm(){
		var hasError = validateForm();  
        if (hasError) {  
            return false;
        } else {	
			var isvalid = false;
			validatePassword();
			var response = $("#passwordValidationResponse").html();
			//alert(" Response Sucess -77777777777---> "+response);
			var oldPasswordValidationResponse = $("#oldPasswordValidationResponse").html();
			//alert(" Response Sucess -77777777777---> "+oldPasswordValidationResponse);
			if(response!=""){
				$("#newpassword").val('');
				$("#conformpassword").val('');  
				$("#newpassword").focus();
				$('#newpassworderror').html("<b>* "+response+".</b>"); 					 
				isvalid = false;
			}else if(oldPasswordValidationResponse!=""){
				$("#oldpassword").val('');
				$("#oldpassword").focus();
				$('#oldpassworderror').html("<b>* "+oldPasswordValidationResponse+".</b>"); 					 
				isvalid = false;
			}else {
				$('#newpassworderror').html(" "); 
				isvalid = true;
			}
			return isvalid;
        }
	}

	    
    function validateForm(){    	
	 	var hasError = false;	
		var oldpassword = $("#oldpassword").val();
    	var newpassword = $("#newpassword").val();
        var conformpassword = $("#conformpassword").val();		
		if (oldpassword == '') {  
			//alert("oldpassword --- "+oldpassword);
        	$('#oldpassworderror').html("<b>* Old Password cannot be empty.</b>");
        	$("#oldpassword").focus(); 
            hasError = true;
        }else {
			$('#oldpassworderror').html(" ");
		}
		
		if (newpassword == '') {        	
        	$('#newpassworderror').html("<b>* Password cannot be empty.</b>");
        	$("#newpassword").focus();
            hasError = true;
        }else {
			$('#newpassworderror').html(" ");
		}
		if (conformpassword == '') {   
        	hasError = true;
        	$('#conformpassworderror').html("<b>* Please re-enter your password.</b>"); 
        	$("#conformpassword").focus();            
        } else {
			$('#conformpassworderror').html(" ");
		}
		
		if (oldpassword!="" && newpassword!="") {
        	if (oldpassword == newpassword ) {
				hasError = true;
				$("#newpassword").val('');
				$("#oldpassword").val('');  
				$("#oldpassword").focus();
				$('#oldpassworderror').html("<b>* Old and New Passwords cannot be same.</b>");             
			}else {
				$('#oldpassworderror').html(" ");
			}             
        }
		if (newpassword!="" && conformpassword!="") {
			 if (newpassword != conformpassword ) {
				hasError = true;
				$("#newpassword").val('');
				$("#conformpassword").val('');  
				$("#newpassword").focus();
				$('#newpassworderror').html("<b>* Passwords do not match.</b>");             
			}else {
				$('#newpassworderror').html(" ");
			}	
		}
		return hasError;    	
    }  