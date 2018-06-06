$(document).ready(function() {
	$('#locationId').change(function() {	
		if(validateSeatViewCaledarForm(1)){
			$('#changedParameter').val("Location");
			$('#calendarForm').submit();
		}
	});
	$('#eventId').change(function() {
		if(validateSeatViewCaledarForm(2)){
			$('#changedParameter').val("Event");
			$('#calendarForm').submit();
		}
	});
	$('#time').change(function() {
		if(validateSeatViewCaledarForm(4)){
			$('#changedParameter').val("Time");
			$('#calendarForm').submit();
		}
	});
	$('#seatViewSearchBtn').click(function() {
		if(validateSeatViewCaledarForm(5)){
			$('#changedParameter').val("SearchBtn");
			$('#calendarForm').submit();
		}
	});		

	$("input[name='showRemainderIcons' ]").change(function(event) {
		 if(validateSeatViewCaledarForm(6)){
			$('#changedParameter').val("ShowRemainderIcons");
			$('#calendarForm').submit();	
		}		
	 });
});

function onRefreshClick(){
	if(validateSeatViewCaledarForm(6)){
		$('#changedParameter').val("Refresh");
		$('#calendarForm').submit();	
	}
}

function jsCalenderDateSelected(){
	if(validateSeatViewCaledarForm(3)){
		$('#changedParameter').val("JsCalender");
		$('#calendarForm').submit();	
	}
}
 
function clearCustomerDetails() {
	$("#customerName").val("");
	$("#customerId").val("-1");
}


 function validateSeatViewCaledarForm(changedParameter) {
	  try{
		     var success = 0;
			 var successCount = 0;
			 //changedParameter = 1- Location
			//changedParameter = 2- Event
			//changedParameter = 3- Date
			//changedParameter = 4- Time
			//changedParameter = 5 - Search	
			//changedParameter = 6 - Refresh	
			switch (changedParameter) {
				case 6:
				case 5:
				case 4:
						 success = success + 1;
						 var time = $("#time").val();
						 if(time!="-1" && time!=-1){
								 $("#timeError").html("");
								successCount = successCount + 1;
						 }else{
								 $("#timeError").html("Please select proper time");
								successCount = successCount - 1;
						 }
				case 3:
						 success = success + 1;
						 var date = $("#date").val();
						  if(date!=""){
								var match = /^(\d{2})\/(\d{2})\/(\d{4})$/.exec(date);
								 if(match){
									  $("#dateError").html("");
									 successCount = successCount + 1;
								 }else{
									  $("#dateError").html("Date should be in MM/DD/YYYY format");
								 }								
						 }else{
								$("#dateError").html("Please select proper date");
								successCount = successCount - 1;
						 }
						 
				case 2:
						 success = success + 1;
						 var eventId = $("#eventId").val();
						 if(eventId!="-1" && eventId!=-1){
								 $("#eventIdError").html("");
								successCount = successCount + 1;
						 }else{
								 $("#eventIdError").html("Please select proper "+($("#eventDisplayName").val()));
								successCount = successCount - 1;
						 }
				case 1:
						success = success + 1;
						var locationId = $("#locationId").val();
						 if(locationId!="-1" && locationId!=-1){
								 $("#locationIdError").html("");
								successCount = successCount + 1;
						 }else{
							    $("#locationIdError").html("Please select proper "+($("#locationDisplayName").val()));
								successCount = successCount - 1;
						 }
			}
			if(success==successCount){
				return true;
			}else{
				return false;
			}
		}catch(e){
			return false;
		}
}