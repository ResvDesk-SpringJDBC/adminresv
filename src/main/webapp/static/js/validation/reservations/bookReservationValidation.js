$(document).ready(function() {
		//Auto Tab
		$("input[class='phone']").bind('keyup', function() {
			var limit = parseInt($(this).attr('maxlength'));  
			var text = $(this).val();  			
			var chars = text.length; 
			//check if there are more characters then allowed
			if(chars >=limit){  							
				var nextIndex = $(this).next().index();				
				$("#"+$(this).next().attr("id")).focus();
			}
		});

		$('#btnSave').click(function() {	
			var url = contextPath+"/validateReservationForm.html";
			var data = $("#bookResvform").serialize();;
			data = data.replace( ' ', '%20' );				
			$.post(url,data,function(res,stat){
				//alert("Response: " + res + "\nStatus: " + stat);
				if(res==""){
					$('#validationResponse').html(" ");	
					$('#resvSave').hide();
					$('#resvBookingMsg').html("<div style='color:red;text-align:center;'><b>Please wait reservation is booking!</b></div>");	
					url = contextPath+"/bookResvAndUpdateCustDetails.html";
					$("#bookingResvDivId").load(url,data,function(response,status,xhr){
						$("#OKDIV").show();
					});
				}else{
					$('#validationResponse').html("<b>"+res+"</b>");	
				}
			});	
		});
		
		$('#okBtn').click(function(evt) {
			$('#changedParameter').val("Refresh");

			//As per Anantha mail we are not setting empty whenever we navigate other links
			$('#customerId').val("-1");
			$('#customerName').val("");

			$('#calendarForm').submit();		
		});

		$("#OKDIV").hide();
});