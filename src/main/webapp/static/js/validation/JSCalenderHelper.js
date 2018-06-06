function initiateJSCalender(dateFiledId){
	try {
		$( "#"+dateFiledId).datepicker({
			changeMonth: true,
			changeYear: true,
			dateFormat: 'mm/dd/yy',
			onSelect: jsCalenderDateSelected,
			beforeShowDay: showAvaliableDates
		});	
		$( "#"+dateFiledId).datepicker( "option", "minDate", getMin_Date());
		$( "#"+dateFiledId).datepicker( "option", "maxDate", getMax_Date());
		$( "#"+dateFiledId).datepicker( "option", "defaultDate", getDefault_Date());
	} catch (e) {
		alert("error - "+e);
	}
}

function getMin_Date() {
   try {
	   var jsCalendarMinDate = $("#jsCalendarMinDate").val();
	   if(jsCalendarMinDate=="" || jsCalendarMinDate==undefined){
		   jsCalendarMinDate= $.datepicker.formatDate('mm/dd/yy', new Date());;
	   }
   } catch (e) {
		alert("getMin_Date ::::: error - "+e);
   }
   return jsCalendarMinDate;
}

function getMax_Date() {
   try {
	   var jsCalendarMaxDate = $("#jsCalendarMaxDate").val();
	   if(jsCalendarMaxDate=="" || jsCalendarMaxDate==undefined){
		   jsCalendarMaxDate= $.datepicker.formatDate('mm/dd/yy', new Date());;
	   }
   } catch (e) {
		alert("getMax_Date ::::: error - "+e);
   }
   return jsCalendarMaxDate;
}

function getDefault_Date() {
	var defaultDate = "";
    var availableDate = $("#availableDate").val();
	if(availableDate=="" || availableDate==undefined){
		defaultDate = $.datepicker.formatDate('mm/dd/yy', new Date());
	}else {
		defaultDate = availableDate;
	}
	return defaultDate;
}

var Map = {};

function  prepareDatesMapAfterAvailDatesLoaded(){
	Map = {};
	try {
		$('div[class="datePicker_Class"] input[type="hidden"]').each(function(){
			var currentDate=$.datepicker.formatDate('mm/dd/yy', new Date());
			var key=$(this).attr("id");
			var value=$(this).val();
			//if(comparedates_new(key,currentDate)>=0) {
			Map[key]=value;
			//}	
		 });
		 /*for(key in Map){
			 alert(":::::: key ::: "+key+" ,\t value "+( Map[key]));
		 }*/
	} catch (e) {
		alert("Error -- "+e);
	}
}

function showAvaliableDates(date) {	
	try {
		Map[date]
		date = $.datepicker.formatDate('mm/dd/yy', date);
		
		var currentDate=$.datepicker.formatDate('mm/dd/yy', new Date());
		if(comparedates_new(date,currentDate)>=0) {
			if(date in Map) {
				var values=Map[date];
				var statusMsg=values.split("_");
				var status=statusMsg[1]; //status - Y - if fully booked else N 
				if(status=="N") {
					return [true,"availableFutureDates","Available"];
				}else{
					return [true,"notAvailableFutureDates","Not Available"];
				}
			}else{
				 //return [true,"notAvailableFutureDates","Not Available"];
				 return [false,"","Disabled"];
			}
		}else{
			if(date in Map) {
				var values=Map[date];
				var statusMsg=values.split("_");
				var status=statusMsg[1]; //status - Y - if fully booked else N 
				if(status=="N") {
					return [true,"availablePastDates","Available"];
				}else {
					return [true,"notAvailablePastDates","Not Available"];
				}				
			}else{
				 //return [true,"notAvailablePastDates","Not Available"];
				 return [false,"","Disabled"];
			}
		}
	} catch (e) {
		alert("Error -- "+e);
	}
}