function validateEventForm(isAdd) {
	try{
		var errorMsg = "";

		if (isAdd) {
			/*
			var locationId = $('#locationId').val(); 
			if (locationId == '' || locationId == null) {
				errorMsg = errorMsg + "Please select proper Location";
			}*/
			var locationIds = [];
			$("input:checkbox:checked").map(function(){
				locationIds.push($(this).val());
			});	
			if(locationIds.length>0){
				//Nothing
			}else{
				errorMsg = errorMsg + "Please select proper Locations";
			}
		}
		var name = $('#eventName').val();   
		var duration = $('#duration').val();  
		
		
		if (name == '') {
			if(errorMsg!=""){
				errorMsg = errorMsg + "<br/>";
			}
			errorMsg = errorMsg + "Event name cannot be empty.";
		} 
		//alert("duration :::::::"+duration);
		if (duration != '') {
			if(isNaN(duration)){
				if(errorMsg!=""){
					errorMsg = errorMsg + "<br/>";
				}
				errorMsg = errorMsg + "Event Duration can be numeric only!";
			}else {
				if(duration<=0){
					if(errorMsg!=""){
						errorMsg = errorMsg + "<br/>";
					}
					errorMsg = errorMsg + "Event Duration should be greater than 0";
				}
			}
		}else{
			if(errorMsg!=""){
				errorMsg = errorMsg + "<br/>";
			}
			errorMsg = errorMsg + "Event Duration cannot be empty.";
		}

		$('#errors').html(errorMsg);
		//alert("errorMsg ::: "+errorMsg);
		if(errorMsg==""){
			return true;
		}else{
			return false;
		}
	}catch(e){
		//alert("Error ::: "+e);
		return false;
	}
}


$(document).ready(function () {
	$('#addEventSaveBtn').click(function () {
			var isValid = validateEventForm(true);			
			if(isValid){
				var isConfirmed = confirm("Adding new "+$('#eventDisplayName').val()+" will render IVR scheduler/reminder non-functional and/or play TTS (computer) voice. You will need to contact ITFrontDesk support to correct this problem and additional cost may be involved.");
				if(isConfirmed){
					$('#eventForm').submit();
				}else{
					$('#eventForm').attr('method', 'post');
					$('#eventForm').attr('action', 'events.html');
					$('#eventForm').submit();
				}
		}else{
			return false;
		}
    });

	$('#editEventSaveBtn').click(function () {
			var isValid = validateEventForm(false);
			if(isValid){
				var isConfirmed = confirm("Changing "+$('#eventDisplayName').val()+" names will render IVR scheduler/reminder non-functional and/or play TTS (computer) voice. You will need to contact ITFrontDesk support to correct this problem and additional cost may be involved.");
				if(isConfirmed){
					$('#eventForm').submit();
				}else{
					$('#eventForm').attr('method', 'post');
					$('#eventForm').attr('action', 'events.html');
					$('#eventForm').submit();
				}
		}else{
			return false;
		}
    });

});