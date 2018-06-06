$(document).ready(function() {
	$('#btnSaveId').hide();
	$('#btnCommentsSaveId').hide();
	$('#cancelBtn').hide();

	$('.cancleUpcomingResv').click(function() {	
		$('#doneBtn').hide();
		  var isConfirmed = confirm("Are you sure, do you want to cancel the Reservation");
		  if(isConfirmed){
				$('#resvMsg').html("<div style='color:red;text-align:center;'><b>Please wait Reservation is canceling!</b></div>");
				var sceduleId = $(this).attr('id');
				var url = "cancle-resv-and-load-updated-resvs.html";
				var customerId = $("#editCustomerId").val();
				var data =  "customerId="+customerId+"&sceduleId="+sceduleId;
				//alert("DATA  ----> "+data);
				data = data.replace( ' ', '%20' );
				$("#upcomingResvDivId").load(url,data,function(response,status,xhr){
				});
		  }else{
			  $('#doneBtn').show();
		  }
	});

	$('#doneBtn').click(function(evt) {
		$('#changedParameter').val("Refresh");

		//As per Anantha mail we are not setting empty whenever we navigate other links
		$('#customerId').val("-1");
		$('#customerName').val("");

		$('#calendarForm').submit();		
	});

	$('#popupClose').click(function(evt) {
		$('#changedParameter').val("Refresh");
		$('#calendarForm').submit();		
	});

	$('#btnSaveId').click(function() {	
		var url = "validateReservationForm.html";
		var queryString = $("#editCustomerForm").serialize();
		queryString = queryString.replace( ' ', '%20' );				
		$.post(url,queryString,function(res,stat){
			//alert("Response: " + res + "\nStatus: " + stat);
			if(res==""){
				$('#validationResponse').html(" ");	
				var requestURL = "update-customer-details.html";
				/* Error Handling  needs to be added*/
				$.post(requestURL, queryString, function(response){ 
					//alert("Response: " + response + "\nStatus: " + stat);
					if(response == "SUCCESS"){				
						 var customerId = $("#editCustomerId").val();			
						 var url = "updated-customer-reservation-details-window.html?customerId="+customerId;
						 //alert("URL :::::::::::: "+url);
						$("#dataId").load(url,function(response,status,xhr){
								$("#customerUpdateResponseId").html("<b style='color:green;text-align:center;'>Customer details updated successfully!</b>");
						});						
					}else{
						$("#validationResponse").html("<b style='color:red;text-align:center;'>Error while updating Customer Details!</b>");
					}
				});
		}else{
			$('#validationResponse').html("<b>"+res+"</b>");	
		}
	});
 });

  $('#cancelBtn').click(function() {
	    var customerId = $("#editCustomerId").val();			
		var url = "updated-customer-reservation-details-window.html?customerId="+customerId;
		$("#dataId").load(url,function(response,status,xhr){
		});
  });
});