$(document).ready(function() {
	//Auto Tab
	$("input[class='phone']").bind('keyup', function() {
		var limit = parseInt($(this).attr('maxlength'));  
		var text = $(this).val();  			
		var chars = text.length; 
		//check if there are more characters then allowed
		if(chars >=limit){ 			
			$("#"+$(this).next().attr("id")).focus();
		}
	});	

	$('#btnSave').click(function() {	
		var url = "validateCustomerDetails.html";
		var data = $("#customerForm").serialize();;
		data = data.replace( ' ', '%20' );				
		$.post(url,data,function(res,stat){
			//alert("Response: " + res + "\nStatus: " + stat);
			if(res==""){
				$('#validationResponse').html(" ");	
				$("#customerForm").submit();
			}else{
				$('#validationResponse').html("<b>"+res+"</b>");	
			}
		});	
	});
});