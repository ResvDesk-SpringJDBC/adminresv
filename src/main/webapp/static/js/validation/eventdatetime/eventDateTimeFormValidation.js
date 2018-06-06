function loadEvents() {
	try {
		 var locationId = $("#locationId").val();
		 if(locationId!="-1"){
			var url = contextPath+"/getEventListByLocationId.html?locationId="+locationId+"&eventDropDownType=Select"; //Select or All
			$("#eventIdDD").load(url,function(data){
			});
		}else{
			alert("&nbsp;&nbsp; Please select proper "+($("#locationDisplayName").val()));
		}
	} catch (e) {
		//alert("Error : "+e);
	}
}

function validateAddEventDateTimeForm(){
		try{
			var successCount = 0;
			
			if($("#locationId").val() !=-1 && $("#locationId").val() !="-1"){
				successCount = successCount+1;
				$("#locationError").html(" ");
			}else{
				$("#locationError").html("&nbsp;&nbsp; Please select proper "+($("#locationDisplayName").val()));
			}
			
			if($("#eventId").val() !="null" && $("#eventId").val() !=null &&  $("#eventId").val() !=-1 && $("#eventId").val() !="-1" ){
				successCount = successCount+1;
				$("#eventError").html(" ");
			}else{
				$("#eventError").html("&nbsp;&nbsp; Please select proper "+($("#eventDisplayName").val()));
			}
			
			if(checkMMDDYYYYDateFields("date","dateError","Date")>0){				
				if(compareMMDDYYYYDateFields($('#date').val())>=0){
					$('#dateError').html(" ");
					successCount = successCount+1;
				}else{
					$("#dateError").html("&nbsp;&nbsp; Date should be future date");
					successCount = successCount-1;
				}
			}else{
				successCount = successCount-1;
			}
			
			if($("#noOfSeats").val() !="" ){
				if(!isNaN($("#noOfSeats").val()) ){
					successCount = successCount+1;
					$("#noOfSeatsError").html(" ");
				}else{
					$("#noOfSeatsError").html("&nbsp;&nbsp; No. of Seats shoukd be numeric only!");
				}
			}else{
				successCount = successCount-1;
				$("#noOfSeatsError").html("&nbsp;&nbsp; No. of Seats is mandatory!");
			}

			//alert("Success Count :  "+successCount);
			if(successCount==4){
				return true;
			}else{
				return false;
			}
		}catch(e){
			alert("Error : "+e);
			return false;
		}
	}

	
	function validateEditEventSeatsForm(){
		try{
			var successCount = 0;
									
			if($("#noOfSeats").val() !="" ){
				if(!isNaN($("#noOfSeats").val()) ){
					successCount = successCount+1;
					$("#noOfSeatsError").html(" ");
				}else{
					$("#noOfSeatsError").html("&nbsp;&nbsp; No. of Seats shoukd be numeric only!");
				}
			}else{
				successCount = successCount-1;
				$("#noOfSeatsError").html("&nbsp;&nbsp; No. of Seats is mandatory!");
			}

			//alert("Success Count :  "+successCount);
			if(successCount==1){
				return true;
			}else{
				return false;
			}
		}catch(e){
			//alert("Error : "+e);
			return false;
		}
	}

	function validateEditEventDateTimeForm(){
		try{
			var successCount = 0;
			
			if(checkMMDDYYYYDateFields("date","dateError","Date")>0){				
				if(compareMMDDYYYYDateFields($('#date').val())>=0){
					$('#dateError').html(" ");
					successCount = successCount+1;
				}else{
					$("#dateError").html("&nbsp;&nbsp; Date should be future date");
					successCount = successCount-1;
				}
			}else{
				successCount = successCount-1;
			}
			//alert("Success Count :  "+successCount);
			if(successCount==1){
				return true;
			}else{
				return false;
			}
		}catch(e){
			//alert("Error : "+e);
			return false;
		}
	}