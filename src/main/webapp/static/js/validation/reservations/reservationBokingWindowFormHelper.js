function validateForm(){
    	
	 	var hasError = false;	

    	var first_name = $('#first_name').val();  
        var last_name = $('#last_name').val();
        var contact_phone1 = $('#contact_phone1').val();
		var contact_phone2 = $('#contact_phone2').val();
		var contact_phone3 = $('#contact_phone3').val();
        var email = $("#email").val();
	
		if(typeof  first_name != "undefined") {
			if (first_name == '') {        	
				hasError = true;        	
				$('#first_nameError').html("<b>* First name cannot be empty.</b>");   
			} else{
				 $('#first_nameError').html(" "); 
			}
		}

		if(typeof  last_name != "undefined") {
			if (last_name == '') {        
				hasError = true;
				$('#last_nameError').html('<b>* Last name cannot be empty.</b>');  
			}else{
				 $('#last_nameError').html(' ');
			}
		}
		
		if(typeof  contact_phone1 != "undefined" && typeof  contact_phone2 != "undefined" && typeof  contact_phone3 != "undefined") {
			if (contact_phone1 != '' && contact_phone2!='' && contact_phone3!='') {        	  
				 var pattern=/^([0-9])+/;
				 if(pattern.test(contact_phone1) && pattern.test(contact_phone2) && pattern.test(contact_phone3)){
						$('#contact_phoneError').html("");
				 }else{
					 hasError = true;
					$('#contact_phoneError').html("<b>* Please provide valid Contact Phone.</b>"); 				
				 }
			} else{
				hasError = true;
				$('#contact_phoneError').html('<b>* Contact Phone cannot be empty.</b>');  
			}
		}

		if(typeof  email != "undefined") {
			if (email == '' ) {
				hasError = true;
				$('#emailError').html("<b>* Email cannot be empty.</b>");             
			}else {
				 var pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
				 if(!pattern.test(email)){
					hasError = true;
					$('#emailError').html("<b>* Please provide valid Email.</b>");    
				 }else{
					 $('#emailError').html("");
				 }
			}
		}
		if(hasError){
			return false;
		}else{
			return true;
		}
    }