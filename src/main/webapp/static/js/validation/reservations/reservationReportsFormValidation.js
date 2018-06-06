 $(document).ready(function() {
		startDate_calendarObject = new JsDatePick({
			useMode : 2,
			target : "startDate",
			dateFormat : "%M/%d/%Y"
		});
		startDate_calendarObject.addOnSelectedDelegate(function(){
			loadEventDateTimes(true);
		});

		endDate_calendarObject = new JsDatePick({
			useMode : 2,
			target : "endDate",
			dateFormat : "%M/%d/%Y"
		});
		endDate_calendarObject.addOnSelectedDelegate(function(){
			loadEventDateTimes(true);
		});

		$('#resetBtn').click(function () {   
			window.location="reservation-reports.html";
		});
  });	


function loadEvents() {
	try {
		 var locationId = $("#locationId").val();
		 if(locationId!="-1"){
			var url = contextPath+"/getEventListByLocationId.html?locationId="+locationId+"&eventDropDownType=All&page=REPORT"; //Select or All
			$("#eventIdDD").load(url,function(data){
			});
		}else{
			alert(" Please select proper "+($("#locationDisplayName").val()));
		}
	} catch (e) {
		alert("Error : "+e);
	}
}

function loadEventDateTimes(ignoreErrorDisplay) {
	try {
		 var eventId = $("#eventId").val();
		 if(eventId!="-2"){
		    var locationId = $("#locationId").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();

			var url = contextPath+"/getEventDateTimeForLocEventDateRange.html?locationId="+locationId
				+"&eventId="+eventId
				+"&startDate="+startDate
				+"&endDate="+endDate
				+"&dropDownId=eventDateTimeIds&dropDownType=CheckBox&page=REPORT"; //Select or CheckBox
			//alert("URL ::: "+url);
			$("#eventDateTimeDD").load(url,function(data){
			});
		}else{
			if(!ignoreErrorDisplay){
				alert(" Please select proper "+($("#eventDisplayName").val()));
			}
		}
	} catch (e) {
		alert("Error : "+e);
	}
}
 
 function validateReservationReportsForm() {
	try{
		var sucessCount = validateDateFieldsFormat("startDate","startDateError","* Start Date");
		sucessCount = sucessCount + validateDateFieldsFormat("endDate","endDateError","* End Date");
		
		if(sucessCount==2){
			if(comparedates_new($('#endDate').val(),$('#startDate').val())<0){
				sucessCount = sucessCount-1;
				$('#endDateError').html("* End date should be greater than or equal to Start date.");
			}else{
				$('#endDateError').html("");				
			}
		}		
		
		var otherResvStatusList = $("#otherResvStatusList").val();
		var resvStatusFields = $('input[name=resvStatusFields]:checked');
		
		if((otherResvStatusList!="" && otherResvStatusList!=null) || resvStatusFields.length>0){
			sucessCount = sucessCount+1;
			$('#resvStatusError').html("");
		}else{
			sucessCount = sucessCount-1;
			$('#resvStatusError').html("* Please select Reservation Status");
		}

		var eventId = $("#eventId").val();
		if(eventId!="-2"){
			sucessCount = sucessCount+1;
			$('#eventIdError').html("");
		}else{
			sucessCount = sucessCount-1;
			$('#eventIdError').html("* Please select "+($("#eventDisplayName").val()));
		}
		
		var eventDateTimeIds = $('input[name=eventDateTimeIds]:checked');
		if(eventDateTimeIds.length>0){
			sucessCount = sucessCount+1;
			$('#eventDateTimeIdsError').html("");
		}else{
			sucessCount = sucessCount-1;
			$('#eventDateTimeIdsError').html("* Please select Event date times");
		}

		if(sucessCount==5){
			return true;
		}else{
			return false;
		}
	}catch(e){
		return false;
	}
}