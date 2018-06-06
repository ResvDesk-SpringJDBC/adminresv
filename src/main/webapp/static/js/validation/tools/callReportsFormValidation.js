function validateCallReport(){
	try{
		var reportType = $('#reportType').val();		
		var sucessCount = 0;
		var from ="";
		var to = "";
		
		if(reportType=="inbound"){		
			sucessCount = sucessCount + validateDateFieldsFormat("inBoundPeriodFrom","fromDateError","* From Date");
			sucessCount = sucessCount + validateDateFieldsFormat("inBoundPeriodTo","toDateError","* To Date");
			
			from = $('#inBoundPeriodFrom').val();
			to = $('#inBoundPeriodTo').val();
		} else {
			sucessCount = sucessCount + validateDateFieldsFormat("outBoundPeriodFrom","fromDateError","* From Date");
			sucessCount = sucessCount + validateDateFieldsFormat("outBoundPeriodTo","toDateError","* To Date");
			
			from = $('#outBoundPeriodFrom').val();
			to = $('#outBoundPeriodTo').val();
		}
		if(sucessCount==2){
			if(comparedates_new(to,from)<0){
				sucessCount = sucessCount-1;
				$('#toDateError').html("* To date should be greater than or equal to From date.");
			}else{
				$('#toDateError').html(" ");
			}
		}		
		
		if(sucessCount==2){
			return true;
		}else{
			return false;
		}
	}catch(e){
		alert("Exception  : "+e);
		return false;
		
	}
}


function validateDateFieldsFormat(fieldId,errorDivId,messageField){
	try{
		var inputvalue = $('#'+fieldId).val();
		if(inputvalue=='')  {
				$('#'+errorDivId).text(messageField +" is required.");
				return 0;
		}else{
				var pattern = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
				if(pattern.test(inputvalue)){
					$('#'+errorDivId).text("");
					return 1;
				}else{
					$('#'+errorDivId).html(messageField +"  should be in MM/dd/yyyy format");
					return 0;
				}
		}
	}catch(e){
		//alert("Exception  : "+e);
	}
}