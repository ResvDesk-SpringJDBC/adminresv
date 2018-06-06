$(document).ready(function() {
	$('#locationId').change(function() {	
		if(validateResvRemaindersForm()){
			$('#changedParameter').val("Location");
			$('#resvReminders').submit();
		}
	});
	$('#campaignId').change(function() {	
		if(validateResvRemaindersForm()){
			$('#changedParameter').val("Campaign");
			$('#resvReminders').submit();
		}
	});
});

function previousDateLinkClicked(){
	if(validateResvRemaindersForm()){
		$('#changedParameter').val("PreviousDateLink");
		$('#resvReminders').submit();	
	}
}

function nextDateLinkClicked(){
	if(validateResvRemaindersForm()){
		$('#changedParameter').val("NextDateLink");
		$('#resvReminders').submit();	
	}
}

function jsCalenderDateSelected(){
	if(validateResvRemaindersForm()){
		$('#changedParameter').val("JsCalender");
		$('#resvReminders').submit();	
	}
}

 function validateResvRemaindersForm() {
	  try{
		     var success = 1;
			 var successCount = 0;

			 var date = $("#jsCalendarDate").val();
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
			if(success==successCount){
				return true;
			}else{
				return false;
			}
		}catch(e){
			return false;
		}
}