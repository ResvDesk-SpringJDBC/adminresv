function validateLocationForm() {
	var isValid = true;
	var name = $('#locationName').val();   
	if (name == '') {
		isValid = false;
		$('#errors').html("Location name cannot be empty.");
	}
	return isValid;
}


$(document).ready(function () {
	//Auto Tab
	$(".phone,.phone1").bind('keyup', function() {
		var limit = parseInt($(this).attr('maxlength'));  
		var text = $(this).val();  			
		var chars = text.length; 
		//check if there are more characters then allowed
		if(chars >=limit){  			
			$("#"+$(this).next().attr("id")).focus();
		}	
	});	

	$('#addlocationSaveBtn').click(function () {
		var isValid = validateLocationForm();			
		if(isValid){
			var isConfirmed = confirm("Adding new "+$('#locationDisplayName').val()+" will render IVR scheduler/reminder non-functional and/or play TTS (computer) voice. You will need to contact ITFrontDesk support to correct this problem and additional cost may be involved.");
			if(isConfirmed){
				$('#locationForm').submit();
			}else{
				$('#locationForm').attr('method', 'post');
				$('#locationForm').attr('action', 'locations.html');
				$('#locationForm').submit();
			}
		}else{
			return false;
		}
    });

	$('#editlocationSaveBtn').click(function () {
		var isValid = validateLocationForm();
		if(isValid){
			var isConfirmed = confirm("Changing "+$('#locationDisplayName').val()+" names will render IVR scheduler/reminder non-functional and/or play TTS (computer) voice. You will need to contact ITFrontDesk support to correct this problem and additional cost may be involved.");
			if(isConfirmed){
				$('#locationForm').submit();
			}else{
				$('#locationForm').attr('method', 'post');
				$('#locationForm').attr('action', 'locations.html');
				$('#locationForm').submit();
			}
		}else{
			return false;
		}
    });

});