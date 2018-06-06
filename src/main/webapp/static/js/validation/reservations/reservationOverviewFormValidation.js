 function validateReservationOverviewForm() {
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
			
			if(sucessCount==2){
				return true;
			}else{
				return false;
			}
		}catch(e){
			return false;
		}
}