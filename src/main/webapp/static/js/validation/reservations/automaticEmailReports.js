function loadEvents() {
	try {
		 var locationId = $("#locationId").val();
		 if(locationId!="-1"){
			var url = contextPath+"/getEventListByLocationId.html?locationId="+locationId+"&eventDropDownType=All"+"&dropDownId=eventIds"; //Select or All
			$("#eventIdDD").load(url,function(data){
			});
		}else{
			alert("&nbsp;&nbsp; Please select proper "+($("#locationDisplayName").val()));
		}
	} catch (e) {
		//alert("Error : "+e);
	}
}

$(document).ready(function () {
	try{
		$('#addAutomaticReportSaveBtn').click(function () {
			var result = validateAutomaticReport();
			if(result){
				$('#automaticEmailReports').submit();
			}
		});
	} catch (e) {
		alert("Error - "+e);
	}
});

function validateAutomaticReport(){
	try{
		var sucessCount = 0;
		var reportName = $("#reportName").val();
		if(reportName!=""){
			sucessCount = sucessCount + 1;
			$('#reportNameError').html(" ");
		}else{
			$('#reportNameError').html("Report Name is mandatory");
		}

		sucessCount = sucessCount + checkEmailIDFields("email1","email1Error","Email1") ;
		if($("#email2").val()!=""){
			sucessCount = sucessCount + checkEmailIDFields("email2","email2Error","Email2") ;
		}else{
			sucessCount = sucessCount + 1;
		}
		if($("#email3").val()!=""){
			sucessCount = sucessCount + checkEmailIDFields("email3","email3Error","Email3") ;
		}else{
			sucessCount = sucessCount + 1;
		}
 
		if(sucessCount==4){
			return true;
		}else{
			return false;
		}
	}catch(e){
		return false;
		//alert("Exception  : "+e);
	}
}