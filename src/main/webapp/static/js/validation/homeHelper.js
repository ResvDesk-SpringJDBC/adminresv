$(document).ready(function () {
	var schedulerClosedStatus = $("#schedulerClosedStatus").val();
	if((schedulerClosedStatus=='N' || schedulerClosedStatus=='n')){
		$("#opendSchedulerDiv").show();
		$("#closedSchedulerDiv").hide();
		$("#home_openstatusDiv").show();
		$("#home_closestatusDiv").hide();
	}else{
		$("#opendSchedulerDiv").hide();
		$("#closedSchedulerDiv").show();
		$("#home_openstatusDiv").hide();
		$("#home_closestatusDiv").show();
	}
});

function changeSchedulerStatus(schedulerStatus) { 
	try{
		var isConfirmed = true;
		if(schedulerStatus=='close'){
			isConfirmed = confirm("Do you want to close the Online / Phone Scheduler for Maintenance");
		}
		if(isConfirmed){
			var url = contextPath+"/changeSchedulerStatus.html?schedulerStatus="+schedulerStatus;	
			$.get(url, function(data) {
				if(data=="SUCCESS"){
				  if(schedulerStatus=='open'){
					  $("#schedulerClosedStatus").val('N');
					  $("#closedSchedulerDiv").hide();
					  $("#opendSchedulerDiv").show();
					  $("#home_openstatusDiv").show();
					  $("#home_closestatusDiv").hide();
					  $("#schedularValChangeResponce").html("Scheduler opened successfully!");
				  }else{
					  $("#schedulerClosedStatus").val('Y');
					  $("#opendSchedulerDiv").hide();
					  $("#closedSchedulerDiv").show();
					  $("#home_openstatusDiv").hide();
					  $("#home_closestatusDiv").show();
					  $("#schedularValChangeResponce").html("Scheduler closed successfully!");
				  }				  
				  $("#schedularValChangeResponce").css('color', 'green');
			   }else{	
					if(schedulerStatus=='open'){
						$("#schedularValChangeResponce").html("Error while opening Scheduler!");
					}else{
						$("#schedularValChangeResponce").html("Error while closing Scheduler!");
					}
				 $("#schedularValChangeResponce").css('color', 'red');
			   }
		   });
		}
	}catch(e){
		alert("Error : "+e);
	}
}