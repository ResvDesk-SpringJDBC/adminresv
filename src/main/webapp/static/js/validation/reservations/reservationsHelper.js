$(document).ready(function() {
	 //Auto populate customer names
	$( "#customerName" ).autocomplete({
		minLength: 0,
		source: function( request, response ) {
			$.ajax({
				type: "GET",
				url: "getCustomerNames.html?customerName="+$("#customerName").val(),
				dataType: "json",
				success: function( data ) { 
					//alert("data ::: "+data);
					response(data);
				},
				error: function (request, status, error) {
					try{
						//alert("request.responseText  ::: "+(request.responseText));
					}catch(e){
						//alert("Exception ::::: "+e);
					}
				}
			});
		},
		focus: function( event, ui ) {
			$( "#customerName" ).val( ui.item.firstName + " " + ui.item.lastName +   ( (ui.item.accountNumber !="undefined"  && ui.item.accountNumber !=undefined )? "  - " +ui.item.accountNumber : "" ));
			return false;
		},
		select: function( event, ui ) {
			//alert("Selected Customer Id :::: "+(ui.item.customerId));
			$("#customerId").val(ui.item.customerId);
			return false;
		}

	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
	  return $( "<li>" )
		.append( "<a>" + item.firstName + " " + item.lastName  + ((item.accountNumber !="undefined"  && item.accountNumber !=undefined ) ?  "  - " +  item.accountNumber : "")+ "</a>" )
		.appendTo( ul );
	};
	
	 //Opening popup window for Customer with Booked Reservations
	 $('.showCustWithBookedResvsWindow').click(function() {	
		  var ids = $(this).attr('id');
		  //alert(" showCustWithBookedResvsWindow  ::::: ids :::: "+(ids));		  
		  var url = "customer-reservation-details-window.html?"+ids;
		  url = url.replace( ' ', '%20' );
		  $('#ex2').jqm({modal: true,ajax: url, cache: false,trigger: 'a.showCustWithBookedResvsWindow'}).jqmShow();
	  });
	 
	 //Opening popup window for Booking Reservation
	 $('.showReservationBookingWindow').click(function() {	
		  var ids = $(this).attr('id');
		  var locationId = $("#locationId").val();
		  var eventId = $("#eventId").val();		 
		  var locationName = $("#locationId").find("option[value='" + locationId + "']").text();
		  var eventName = $("#eventId").find("option[value='" + eventId + "']").text();
		  var date = $("#availableDate").val();
		  var customerId = $("#customerId").val();

		  var extraData = "locationId="+locationId+"&eventId="+eventId+"&"+ids+"&locationName="+locationName+"&eventName="+eventName+"&date="+date+"&customerId="+customerId
		  var url = "reservation-booking-window.html?"+extraData;
		  //url = url.replace( ' ', '%20' );
		  url = url.replace(/(\s)+/g,'%20');
		  //alert(" ReservationBookingWindow  ::::: URL :::: "+(url)); 
		  $('#ex2').jqm({modal: true,ajax: url, cache: false,trigger: 'a.showReservationBookingWindow'}).jqmShow();
	  });
	
	 //Update Screened status
	 $('.updateScreenedStatus').click(function() {	
		  var thisImage = $(this);
		  var params = thisImage.attr('params'); //customerId=6&scheduleId=13&screened=N

		  var paramsArr = params.split("&");		  
		  //var customerId = (paramsArr[0].split("=")[1]);
		  //var scheduleId = (paramsArr[1].split("=")[1]);
		  var screened = (paramsArr[2].split("=")[1]);

		  var updatedScreened = 'N';
		  if(screened=="N"){
		     updatedScreened = "Y";
		  }else{
		     updatedScreened = "N";
		  }
		  params = (paramsArr[0])+"&"+(paramsArr[1])+"&screened="+updatedScreened;

		  var url = "update-screened-status.html?"+params;
		  url = url.replace(/(\s)+/g,'%20');
		  //alert(" updateScreenedStatus  ::::: URL :::: "+(url)); 
		  
		  $.get(url, function(data) {
				try{
					if(data=='SUCCESS'){
						if(screened=="N"){
						   thisImage.attr('src','static/images/screened.ico');
						   thisImage.attr('alt','Screened');
						   thisImage.attr('title','Screened');
						}else{
						   thisImage.attr('src','static/images/not_screened.ico');
						   thisImage.attr('alt','Not Screened');
						   thisImage.attr('title','Not Screened');
						}
						thisImage.attr('params',params);
					} else {
					   alert("Error while updating screened status!");
					}
				}catch(e){
					alert("Error :::: "+e);
				}
		  });
	 });


	//UnReserv, Reserved Seat
	 $('.unReserveReservedSeat').click(function() {	
		  var params = $(this).attr('params'); //seatId=1&reserved=N&eventDateTimeId=1
		  var paramsArr = params.split("&");		  
		  var seatId = (paramsArr[0].split("=")[1]);
		  var reserved = (paramsArr[1].split("=")[1]);
		  var eventDateTimeId = (paramsArr[2].split("=")[1]);

		  var url = "update-seat-reserved-status.html?"+params;
		  url = url.replace(/(\s)+/g,'%20');
		  //alert(" Un-Reserv, Reserved Seat  ::::: URL :::: "+(url)); 
		  var isConfirmed = confirm('Are you sure, do you want to unreserv the reserved Seat');
			if(isConfirmed){
				$.get(url,function(res,stat){
					res = JSON.parse(res);
					if((res.responseStatus=="true" || res.responseStatus==true) && (res.errorStatus=="false" || res.errorStatus==false)){
						$("#Reserved_"+seatId+"_"+eventDateTimeId).hide();
						$("#Book_"+seatId+"_"+eventDateTimeId).show();
						$("#Reserve_"+seatId+"_"+eventDateTimeId).show();
						$("#TD_"+seatId+"_"+eventDateTimeId).attr("class","appt");
						$("#errorMsgDivId").html("");	
						$("#sucessesMsgDivId").html("Sucessfully unreserved the Seat!");
					}else{
						//$("#sucessesMsgDivId").html("");
						//$("#errorMsgDivId").html(res.errorMessage);	
						alert(res.errorMessage);
						onRefreshClick();
					}					
				});				
			}
	  });

	  //Reserv, UnReserved Seat
	 $('.reserveUnReservedSeat').click(function() {	
		  var params = $(this).attr('params'); //seatId=1&reserved=Y&eventDateTimeId=1
		  var paramsArr = params.split("&");		  
		  var seatId = (paramsArr[0].split("=")[1]);
		  var reserved = (paramsArr[1].split("=")[1]);
		  var eventDateTimeId = (paramsArr[2].split("=")[1]);

		  var url = "update-seat-reserved-status.html?"+params;
		  url = url.replace(/(\s)+/g,'%20');
		  //alert(" Reserv, Un-Reserved Seat  ::::: URL :::: "+(url)); 
		  var isConfirmed = confirm('Are you sure, do you want to reserve the Seat');
			if(isConfirmed){				
				$.get(url,function(res,stat){
					res = JSON.parse(res);
					if((res.responseStatus=="true" || res.responseStatus==true) && (res.errorStatus=="false" || res.errorStatus==false)){
						$("#Book_"+seatId+"_"+eventDateTimeId).hide();
						$("#Reserve_"+seatId+"_"+eventDateTimeId).hide();
						$("#Reserved_"+seatId+"_"+eventDateTimeId).show();
						$("#TD_"+seatId+"_"+eventDateTimeId).attr("class","solid rline2  reserved blocked");
						$("#errorMsgDivId").html("");
						$("#sucessesMsgDivId").html("Sucessfully reserved the Seat!");
					}else{
						//$("#sucessesMsgDivId").html("");
						//$("#errorMsgDivId").html(res.errorMessage);
						alert(res.errorMessage);
						onRefreshClick();
					}					
				});		
			}
	  });
});


function searchSeatViewCalender(carrentPage){ //dailyview or seatview
	if(carrentPage=='dailyview'){
		$('#date').val($("#jsCalendarDate").val());
	}
	
	$('#changedParameter').val("Refresh");
	$('#calendarForm').attr('method', 'post');
	$('#calendarForm').attr('action', 'search-seat-view-calendar.html');
	$('#calendarForm').submit();		 
}

function searchDailyViewCalender (carrentPage){ //dailyview or seatview
	if(carrentPage=='seatview'){
		$('#jsCalendarDate').val($("#date").val());
	}

	$('#changedParameter').val("Refresh");
	$('#calendarForm').attr('method', 'post');
	$('#calendarForm').attr('action', 'search-daily-view-calendar.html');
	$('#calendarForm').submit();	 
}