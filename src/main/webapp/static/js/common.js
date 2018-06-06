function checkNumericFields(fieldId,errorDiv,messageField) {
     var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
    }else{
		if(checkForNumericValueOnly(inputvalue)){
			 $('#'+errorDiv).text("");
			 return 1;
		}else{
			 $('#'+errorDiv).text(messageField+"  should contain only Numerics (positive values)");
			 return 0;
		}
    }
  }

  function checkNumericFieldsWithSlash(fieldId,errorDiv,messageField) {
     var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
	$('#'+errorDiv).text(messageField+"  is required.");
	return 0;
    }else{
	if(checkForAlphaNumericWithSlashValues(inputvalue)){
	     $('#'+errorDiv).text("");
	     return 1;
	}else{
	     $('#'+errorDiv).text(messageField+"  should contain Numerics, Alphabets and / only");
	     return 0;
	}
    }
  }
  
  function checkAlphaNumericFields(fieldId,errorDiv,messageField) {
       var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
      }else{
		if(checkForAlphaNumericValues(inputvalue)){
			$('#'+errorDiv).text("");
			return 1;
		}else{
			$('#'+errorDiv).text(messageField+"  should contain only AlphaNumerics");
			return 0;
		}
     }
  }
  // alphanumeric Validation with space....
  function checkAlphaNumericFieldsWithSpace(fieldId,errorDiv,messageField) {
       var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
      }else{
		if(checkForAlphaNumericWithSpaceValues(inputvalue)){
			$('#'+errorDiv).text("");
			return 1;
		}else{
			$('#'+errorDiv).text(messageField+"  should contain only AlphaNumerics and Space");
			return 0;
		}
     }
  }
  
  function checkAlphabeticFields(fieldId,errorDiv,messageField) {

     var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
	$('#'+errorDiv).text(messageField+"  is required.");
	return 0;
    }else{
	if(checkForAlphabetValueOnly(inputvalue)){
	    $('#'+errorDiv).text("");
	    return 1;
	}else{
	    $('#'+errorDiv).text(messageField+"  should contain only Alphabets");
	     return 0;
	}
    }
  }	
  
   function checkAlphabeticNumWithSpaceFields(fieldId,errorDiv,messageField) {
  
     var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
	$('#'+errorDiv).text(messageField+"  is required.");
	return 0;
    }else{
	
	if(checkForAlphabetNumWithSpaceValue(inputvalue)){
	    $('#'+errorDiv).text("");
	    return 1;
	}else{
	    $('#'+errorDiv).text(messageField+"  should contain only AlphaNumeric and Space.");
	     return 0;
	}
    }
  }	
  // alphabetic Validation with space....
  function checkAlphabeticsWithSpaceFields(fieldId,errorDiv,messageField) {
     var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
	$('#'+errorDiv).text(messageField+"  is required.");
	return 0;
    }else{
	
	if(checkForAlphabetWithSpaceValue(inputvalue)){
	    $('#'+errorDiv).text("");
	    return 1;
	}else{
	    $('#'+errorDiv).text(messageField+"  should contain only Alphabets and Space.");
	     return 0;
	}
    }
  }	
  //mine...
   function checkAlphabeticNumWithNotificationFields(fieldId,errorDiv,messageField) {
     var inputvalue = $('#'+fieldId).val();
	 if(inputvalue=='' && inputvalue.length == 0)	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
    }else{
		var result = checkForAlphabetNumWithSpaceValue(inputvalue);
	
		if(result){
			$('#'+errorDiv).text("");
			return 1;
		}else{
			$('#'+errorDiv).text("");
			$('#'+errorDiv).text(messageField+"  should contain only Alphabets , Number and Spaces..");
	     return 0;
		}
    }
  }	
  

   
  function checkEmailIDFields(fieldId,errorDiv,messageField) {
	var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
    	}else{
	    var isCorrect=emailIDVerification(inputvalue,false);
	    if(!isCorrect)  {
		$('#'+errorDiv).text(messageField+"  is Invalid");
		return 0;
	     }else{
		$('#'+errorDiv).text("");
		 return 1;
	     }
	}
  }

  function checkCompanyEmailIDFields(fieldId,errorDiv,messageField) {
	var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
    	}else{
			var isCorrect=emailIDVerification(inputvalue,true);
	    if(!isCorrect)  {
		$('#'+errorDiv).text(messageField+"  is Invalid, Provide corpus mailId only");
		return 0;
	     }else{
		$('#'+errorDiv).text("");
		 return 1;
	     }
	}
  }
  
  function checkForEmptyFields(fieldId,errorDiv,messageField) {
  	var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')  {
       	    $('#'+errorDiv).text(messageField +"   is required.");
       	    return 0;
        }else{
	    $('#'+errorDiv).text("");
  	    return 1;
  	}
  } 

  function checkForDateFields(fieldId,errorDiv,messageField) {
  	var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')  {
       	    $('#'+errorDiv).text(messageField +" is required.");
       	    return 0;
        }else{
			var validate = validateDateFormat(inputvalue);
			if(validate){
				$('#'+errorDiv).text("");
				return 1;
			}else{
				$('#'+errorDiv).text(messageField +"  should be in dd-MM-yyyy format");
				return 0;
			}
  	}
  } 
  
  function emailIDVerification(inputvalue,isCompanyEmail) {
  	  var isCorrect=false;	 
	  if(isCompanyEmail){
		var pattern=/^([a-zA-Z0-9_.-])+@telappt.com/;
	  }else{
		   var pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
	  }
  		if(pattern.test(inputvalue)){
  			isCorrect=true;
  		}
  		return isCorrect;
    }

  function checkForNumericValueOnly(inputvalue){
  	   var isCorrect=false;
  	   var pattern =  /^[0-9]+$/;		
  	  if (pattern.test(inputvalue)) {
  		isCorrect =true;
  	   } 
  	  return isCorrect;
   }

    function checkForNumericValuesWithDot(inputvalue){		
  	   var isCorrect=false;
  	  // var pattern =  /^([0-9]+\.)$/;		
	  // var pattern =  /^[0-9]+(\.[0-9][0-9])?$/;
	   var pattern =  /^[0-9]*(\.[0-9]+)?$/;

  	  if (pattern.test(inputvalue)) {
  		isCorrect =true;
  	   } 
  	  return isCorrect;
   }
  
  function checkForAlphabetValueOnly(inputvalue){
  	  var isCorrect=false;
  	  var pattern = /^[a-zA-Z]+$/;	
  		if (pattern.test(inputvalue)) {
  			isCorrect =true;
  		} 
  	return isCorrect;
   }
   
   
   function checkForAlphabetNumWithSpaceValue(inputvalue){
  	  var isCorrect=false;
  	  var pattern = /^[a-zA-Z0-9\.\.\_.\#.\*.\&]+[a-zA-Z\d\.\.\_.\#.\*.\&\s]*[a-zA-Z\d\.\.\_.\#.\*.\&]$/;	
  		if (pattern.test(inputvalue)) {
  			isCorrect =true;
  		} 
  	return isCorrect;
   }
   // validation with space start.....
    function checkForAlphabetWithSpaceValue(inputvalue){
  	  var isCorrect=false;
  	  var pattern = /^[A-Za-z\s]+$/;
		if (pattern.test(inputvalue)) {
  			isCorrect =true;
  		} 
  	return isCorrect;
   }
   // the below one for VisaType validation with space ...... 
    function checkForAlphaNumericWithSpaceValues(inputvalue){
     	  var isCorrect=false;
     	  var pattern = /^[a-zA-Z0-9\s]+$/;	
     		if (pattern.test(inputvalue)) {
     			isCorrect =true;
     		} 
     	return isCorrect;
   }
   
   // the below one for VisaType validation with space ...... 
    function checkForAlphaNumericWithSlashValues(inputvalue){
     	  var isCorrect=false;
     	  var pattern = /^[a-zA-Z0-9/]+$/;	
     		if (pattern.test(inputvalue)) {
     			isCorrect =true;
     		} 
     	return isCorrect;
   }
   
   
   
	// validation with space .....end.....
   function checkForAlphaNumericValues(inputvalue){
     	  var isCorrect=false;
     	  var pattern = /^[a-zA-Z0-9]+$/;	
     		if (pattern.test(inputvalue)) {
     			isCorrect =true;
     		} 
     	return isCorrect;
   }
   
 
	  
   function compareDateWithTime(dateStr) {	   
		var datearray = dateStr.split("-");
		var date = new Date();
		var myDate=new Date();
		var month = getIndex(datearray[1]);
		myDate.setFullYear(datearray[2],month,datearray[0]);	
		
		if(myDate.getTime()>date.getTime()){
			return 1;
		}
		 else if(myDate.getTime()<date.getTime()){
			return -1;
		}else{
			return 0;
		}	
	}
	
	function validateDateFormat(dateStr){
	 // alert("checkDateFormat");
  	  var isCorrect=false;
  	  //var pattern = /^([0-9]{2})-([a-zA-Z]{3})-([0-9]{4})$/;	
	  var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
	  var sucess= pattern.test(dateStr);
	 // alert("sucess ----> "+sucess);
     if (sucess) {
			isCorrect=true;
	}
  	return isCorrect;
   }

  function checkForAmpenrcent(inputvalue){
  	  var result=false;
  	  var pattern = /^[&]$/;	
  		if (pattern.test(inputvalue)) {
  			result =true;
  		} 
  	return result;
   }

 function checkDateFormat(dateStr,dateStr1){
  	  var isCorrect=false;
  	  //var pattern = /^([0-9]{2})-([a-zA-Z]{3})-([0-9]{4})$/;
	  var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
	  var sucess= pattern.test(dateStr);
	  //alert("sucess ----> "+sucess);
	  var sucess1 = pattern.test(dateStr1);
	 //alert("sucess1 ----> "+sucess1);
     if (sucess && sucess1) {
			isCorrect=true;
	}
  	return isCorrect;
   }

	function comparedates(dateStr,dateStr1) {	
		var datearray = dateStr.split("-");
		var date = datearray[0];
		//var month = getIndex(datearray[1]);
		var month = datearray[1]
		var year = datearray[2];
		
		var datearray1 = dateStr1.split("-");
		var date1 = datearray1[0];
		//var month1 = getIndex(datearray1[1]);
		var month1 = datearray1[1];
		var year1 = datearray1[2];
		
		if(year>year1){
			//alert('Year is greater');
			return 1;
		} else if(year<year1){
			//alert('Year is smaller');
			return -1;
		}else{
			//alert('Year is equal');
			if(month>month1){
				//alert('Year is equal  and  Month is greater');
				return 1;
			}else if(month<month1){
				//alert('Year is equal  and  Month is smaller');
				return -1;
			} else{
				//alert('Month is equal');
				if(date>date1){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return 1;
				}else if(date<date1){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return -1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
		}
	}


function comparedates1(dateStr,dateStr1) {	
		var datearray = dateStr.split("-");
		var date = datearray[2];
		//var month = getIndex(datearray[1]);
		var month = datearray[1]
		var year = datearray[0];
		
		var datearray1 = dateStr1.split("-");
		var date1 = datearray1[2];
		//var month1 = getIndex(datearray1[1]);
		var month1 = datearray1[1];
		var year1 = datearray1[0];
		
		if(year>year1){
			//alert('Year is greater');
			return 1;
		} else if(year<year1){
			//alert('Year is smaller');
			return -1;
		}else{
			//alert('Year is equal');
			if(month>month1){
				//alert('Year is equal  and  Month is greater');
				return 1;
			}else if(month<month1){
				//alert('Year is equal  and  Month is smaller');
				return -1;
			} else{
				//alert('Month is equal');
				if(date>date1){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return 1;
				}else if(date<date1){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return -1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
		}
	}


	function comparedates_new(dateStr,dateStr1) {	
		var datearray = dateStr.split("/");
		var date = datearray[1];
		//var month = getIndex(datearray[1]);
		var month = datearray[0]
		var year = datearray[2];
		
		var datearray1 = dateStr1.split("/");
		var date1 = datearray1[1];
		//var month1 = getIndex(datearray1[1]);
		var month1 = datearray1[0];
		var year1 = datearray1[2];
		
		if(year>year1){
			//alert('Year is greater');
			return 1;
		} else if(year<year1){
			//alert('Year is smaller');
			return -1;
		}else{
			//alert('Year is equal');
			if(month>month1){
				//alert('Year is equal  and  Month is greater');
				return 1;
			}else if(month<month1){
				//alert('Year is equal  and  Month is smaller');
				return -1;
			} else{
				//alert('Month is equal');
				if(date>date1){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return 1;
				}else if(date<date1){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return -1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
		}
	}


	function comparedateswithMaxDays(fromDate,toDate,days) {	
		
		var fromDatearray = fromDate.split("-");
		var fromdate = parseInt(fromDatearray[0])+days;		
		//var month = getIndex(datearray[1]);
		var frommonth = fromDatearray[1];
		var fromyear = fromDatearray[2];
		
		var todatearray = toDate.split("-");
		var todate = todatearray[0];
		//var month = getIndex(todatearray[1]);
		var tomonth = todatearray[1];
		var toyear = todatearray[2];
		
		if(fromyear>toyear){
			//alert('Year is greater');
			return -1;
		} else if(fromyear<toyear){
			//alert('Year is smaller');
			return 1;
		}else{
			//alert('Year is equal');
			if(frommonth>tomonth){
				//alert('Year is equal  and  Month is greater');
				return -1;
			}else if(frommonth<tomonth){
				//alert('Year is equal  and  Month is smaller');
				return 1;
			} else{
				//alert('Month is equal');
				if(fromdate>todate){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return -1;
				}else if(fromdate<todate){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return 1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
		}
	}

	function comparedateswithMaxDays_Temp(fromDate,toDate,days) {	
		try{
			//alert("fromDate : "+fromDate);
			//alert("toDate : "+toDate);
			//alert("days : "+days);
			var fromDateArray = fromDate.split("-");	
			var fromDateAfterDaysAdd = parseInt(fromDateArray[0])+days;
			var fromMonth = parseInt(fromDateArray[1])-1;

			var toDateArray = toDate.split("-");		
			var toMonth = parseInt(toDateArray[1])-1;		
			
			var fromDateDate = new Date(fromDateArray[2]+","+fromMonth+","+fromDateAfterDaysAdd);
			var fromDateMills =fromDateDate.getTime();

			var toDateDate = new Date(toDateArray[2]+","+toMonth+","+toDateArray[0]);
			var toDateMills =toDateDate.getTime();
			
			//alert("fromDateMills : "+fromDateMills);
			//alert("toDateMills : "+toDateMills);

			if (toDateMills >= fromDateMills) {
				return 1;
			}else{
				return -1;
			}
		}catch(e){
			alert("Error : "+e);
			return -1;
		}
	}

		function compareDate(dateStr) {	

		var datearray = dateStr.split("-");
		var date1 = new Date();
		var nowDate = date1.getDate();
		var nowMonth = date1.getMonth();
		//getMonth() 	Returns the month (from 0-11)
		nowMonth = nowMonth+1;
		var nowYear = date1.getFullYear();

		var date = datearray[0];
		//var month = getIndex(datearray[1]);
		var month = datearray[1];
		var year = datearray[2];
		
		if(year>nowYear){
			//alert('Year is greater');
			return 1;
		} else if(year<nowYear){
			//alert('Year is smaller');
			return -1;
		}else{
			//alert('Year is equal');
			//alert("month  ----> "+month+" --  nowMonth "+nowMonth);
			if(month>nowMonth){
				//alert('Year is equal  and  Month is greater');
				return 1;
			}else if(month<nowMonth){
				//alert('Year is equal  and  Month is smaller');
				return -1;
			} else{
				//alert('Month is equal');
				if(date>nowDate){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return 1;
				}else if(date<nowDate){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return -1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
		}
	}


	function getIndex ( item) {
		//var monthNames =new Array("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
		var monthNames =new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		  for(var i=0;i<monthNames.length;i++){
				 if (monthNames [i] == item) {
					return i;
				}
			}
		   return -1;
	   }
   
   
   //------------------------------------------------------
   
   
   
   function checkNumericFieldsWithInputValue(inputvalue,errorDiv,messageField) {
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
    }else{
		if(checkForNumericValueOnly(inputvalue)){
			 $('#'+errorDiv).text("");
			 return 1;
		}else{
			 $('#'+errorDiv).text(messageField+"  should contain only Numerics");
			 return 0;
		}
    }
  }

   function checkNumericFieldsForDotWithInputValue(inputvalue,errorDiv,messageField) {
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
    }else{
		if(checkForNumericValuesWithDot(inputvalue)){
			 $('#'+errorDiv).text("");
			 return 1;
		}else{
			 $('#'+errorDiv).text(messageField+"  should contain only Numerics with dot");
			 return 0;
		}
    }
  }
  
  function checkAlphaNumericFieldsWithInputValue(inputvalue,errorDiv,messageField) {
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
      }else{
		if(checkForAlphaNumericValues(inputvalue)){
			$('#'+errorDiv).text("");
			return 1;
		}else{
			$('#'+errorDiv).text(messageField+"  should contain only AlphaNumerics");
			return 0;
		}
     }
  }
  
// the below one for VisaType validation with space ...... 
  function checkAlphaNumericSpaceFieldsWithInputValue(inputvalue,errorDiv,messageField) {
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
      }else{
		if(checkForAlphaNumericWithSpaceValues(inputvalue)){
			$('#'+errorDiv).text("");
			return 1;
		}else{
			$('#'+errorDiv).text(messageField+"  should contain only AlphaNumerics and space");
			return 0;
		}
     }
  }
  
 // alphabetic Validation with space....
  function checkAlphabeticsWithSpaceFieldsWithInputValue(inputvalue,errorDiv,messageField) {
    if(inputvalue=='')	{
	$('#'+errorDiv).text(messageField+"  is required.");
	return 0;
    }else{
	if(checkForAlphabetWithSpaceValue(inputvalue)){
	    $('#'+errorDiv).text("");
	    return 1;
	}else{
	    $('#'+errorDiv).text(messageField+"  should contain only Alphabets and Space.");
	     return 0;
	}
    }
  }	
  
  function checkAlphabeticFieldsWithInputValue(inputvalue,errorDiv,messageField) {
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
    }else{
		if(checkForAlphabetValueOnly(inputvalue)){
	    $('#'+errorDiv).text("");
		return 1;
		}else{
		$('#'+errorDiv).text(messageField+"  should contain only Alphabets");
	     return 0;
		}
	}
  }	

  function checkEmailIDFieldsWithInputValue(inputvalue,errorDiv,messageField) {
	if(inputvalue=='')	{
			$('#'+errorDiv).text(messageField+"  is required.");
			return 0;
    	}else{
			var isCorrect=emailIDVerification(inputvalue);
			if(!isCorrect)  {
				$('#'+errorDiv).text(messageField+"  is Invalid");
				return 0;
			 }else{
				$('#'+errorDiv).text("");
				 return 1;
			 }
		}
  }
  
  function checkForEmptyFieldsWithInputValue(inputvalue,errorDiv,messageField) {
	if(inputvalue=='')  {
       	    $('#'+errorDiv).text(messageField +"   is required.");
       	    return 0;
        }else{
			$('#'+errorDiv).text("");
			return 1;
		}
  } 
  
  function displayErroMessage(errorDiv,messageField) {
		$('#'+errorDiv).text(messageField);
		return 0;
		
  }
  function checkAlphaNumericFieldsWithSpecialCharacters(fieldId,errorDiv,messageField) {
       var inputvalue = $('#'+fieldId).val();
	   
    if(inputvalue=='') {
		  $('#'+errorDiv).text(messageField+" is required.");
		  return 0;
    }else{
	  if(checkForAlphaNumericWithSpecialCharacters(inputvalue)){
	   $('#'+errorDiv).text("");
	   return 1;
	  }else{
	   $('#'+errorDiv).text(messageField+" should contain only AlphaNumerics and special characters");
	   return 0;
	  }
    }
  }
  
  function checkForAlphaNumericWithSpecialCharacters(inputvalue){
      var isCorrect=false;
      //var pattern =  /^[0-9]+$/; 

		//var pattern = var pattern =  /^[A-Za-z0-9-_\s'$&@.,:;//]+$/;  
          var pattern =  /^[A-Za-z0-9-_\s'#$&+@.,:;//]+$/;
          
     if (pattern.test(inputvalue)) {
		isCorrect =true;
      } 
     return isCorrect;
   }
   
   // the below one for check first Character is Alaphabet 
    function checkForAlphabetnumaricWithFirstCharcaterAlaphabet(inputvalue){
     	  var isCorrect=false;
     	 // var pattern = /^[A-Za-z]{1}+$/;
		    var pattern = /^[A-Za-z]{1}[a-zA-Z0-9]+$/;
     	// /^[A-Za-z]{2}/

     		if (pattern.test(inputvalue)) {
     			isCorrect =true;
     		} 
     	return isCorrect;
   }
   
   
   function checkForAlphabetnumaricWithFirstCharcaterAlaphabetFields(fieldId,errorDiv,messageField) {
       var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
		$('#'+errorDiv).text(messageField+"  is required.");
		return 0;
      }else{
		if(checkForAlphabetnumaricWithFirstCharcaterAlaphabet(inputvalue)){
			$('#'+errorDiv).text("");
			return 1;
		}else{
			$('#'+errorDiv).text(messageField+"  should contain only Alphabet as first character");
			return 0;
		}
     }
  }

  function checkMoneyFields(fieldId,errorDiv,messageField) {
     var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')	{
	$('#'+errorDiv).text(messageField+"  is required.");
	return 0;
    }else{
	if(checkMoneyFieldValues(inputvalue)){
	     $('#'+errorDiv).text("");
	     return 1;
	}else{
	     $('#'+errorDiv).text(messageField+"  should contain Numerics , .  /  - only");
	     return 0;
	}
    }
  }

   // the below one for VisaType validation with space ...... 
    function checkMoneyFieldValues(inputvalue){
     	  var isCorrect=false;
     	  var pattern = /^[0-9,./-]+$/;	
     		if (pattern.test(inputvalue)) {
     			isCorrect =true;
     		} 
     	return isCorrect;
   }
   
   function checkForDateFieldsforNotRequired(fieldId,errorDiv,messageField) {
  	var inputvalue = $('#'+fieldId).val();
    if(inputvalue=='')  {
       	    //$('#'+errorDiv).text(messageField +"   is required.");
       	    return 1;
        }else{
			var validate = validateDateFormat(inputvalue);
			if(validate){
				$('#'+errorDiv).text("");
				return 1;
			}else{
				$('#'+errorDiv).text(messageField +"  should be in dd-MMM-yyyy format");
				return 0;
			}
  	}
  }
   
   function holdResource(contextPath) {
		var str = $("form").serialize();
		var request = $.post(contextPath+"/holdResource.html?"+str, function(data) {
			  if(data.success==true) {
				  var arg = data.data;
				  for(var argValue in arg) {
					  if(argValue == 'slotsAvailable') {
						  var slots = arg[argValue];
						  if(slots == false) {
							  $('#message').text(data.message);
							  var selectedDate = document.getElementById('date').value;
							  populateSelectdate(6,selectedDate);
						  }
					  }
				  } 
			  }
		});
	}
   
   /*Auto Tab On Phone number field.*/
	function autoTab(current,next){
			if (current.getAttribute&&current.value.length==current.getAttribute("maxlength")){
				next.focus()
			}
		}
	
	function compareTimes(startTime,startTimeMode,endTime,endTimeMode,errorDiv) {	
		var returnValue=-1;
		var isStartTimeValid = isTimeFormatValid(startTime);
        var isEndTimeValid = isTimeFormatValid(endTime);
		if(isStartTimeValid && isEndTimeValid){
			if(startTimeMode=="AM" && endTimeMode=="PM"){
				$(errorDiv).html("");
				return 0;				
			}else if(startTimeMode=="PM" && endTimeMode=="AM"){
				$(errorDiv).html("End Time Should be greater than Start Time...");
				return 1;
			}else if(startTimeMode==endTimeMode){
				var startTimeArray = startTime.split(":");
				var  startTimeHour = startTimeArray[0];
				var startTimeMinute = startTimeArray[1];

				var endTimeArray = endTime.split(":");
				var endTimeHour = endTimeArray[0];
				var endTimeMinute = endTimeArray[1];
				
				if(startTimeHour>endTimeHour){
					$(errorDiv).html("End Time Should be greater than Start Time...");
					return 1;
				}else if(startTimeHour<endTimeHour){
					$(errorDiv).html("");
					return 0;
				}else if(startTimeHour==endTimeHour){
						if(startTimeMinute>endTimeMinute){
							$(errorDiv).html("End Time Should be greater than Start Time...");
							return 1;
						}else if(startTimeMinute<endTimeMinute){
							$(errorDiv).html("");
							return 0;
						}if(startTimeMinute==endTimeMinute){
							$(errorDiv).html("End Time Should be greater than Start Time...");
							return 1;
						}
				}
			}
		}else{
			$(errorDiv).html("Time should be in hh:mm format");
		}
		return returnValue;
	}


 function checkForMandatoryFieldsWithLength(fieldId,errorDiv,messageField,fieldLength) {
	   var  returnValue = 1;
	   var fieldValue = $("#"+fieldId).val();	   
	  // alert("fieldValue ----> "+fieldValue);
		if(fieldValue==""){
			 returnValue = 0;
			 $("#"+errorDiv).html("&nbsp;&nbsp;  "+messageField+"  is mandatory"); 
		}else {		
			if(fieldLength>0){
				if(fieldValue.length>fieldLength){
					returnValue = 0;
					$("#"+errorDiv).html("&nbsp;&nbsp;  "+messageField+" should contain only "+fieldLength+" characters"); 
				}else{
					$("#"+errorDiv).html(""); 
				}
			}else{
				$("#"+errorDiv).html(""); 
			}
		}
		return returnValue;
  } 

function checkForMandatoryNumericFieldsWithLength(fieldId,errorDiv,messageField,fieldLength) {
	   var  returnValue = 0;
	   var fieldValue = $("#"+fieldId).val();	   
		if(fieldValue==""){
			 $("#"+errorDiv).html("&nbsp;&nbsp;  "+messageField+"  is mandatory"); 
		}else {	
			if(!isNaN(fieldValue)){
				if(fieldValue.length>fieldLength){
					returnValue = 0;
					$("#"+errorDiv).html("&nbsp;&nbsp;  "+messageField+" should contain only "+fieldLength+" characters"); 
				}else{
					$("#"+errorDiv).html(""); 
				}
			}else{
				$("#"+errorDiv).html("&nbsp;&nbsp;  "+messageField+" should contain only numbers"); 
			}
		}
		return returnValue;
} 

function checkForMandatoryNumericFields(fieldId,errorDiv,messageField,minValue) {
	   var  returnValue = 0;
	   var fieldValue = $("#"+fieldId).val();	   
		if(fieldValue==""){
			 $("#"+errorDiv).html("&nbsp;&nbsp;  "+messageField+"  is mandatory"); 
		}else {	
			if(!isNaN(fieldValue)){
				if(fieldValue>=minValue){
					returnValue = 1;
					$("#"+errorDiv).html(""); 					
				}else{
					$("#"+errorDiv).html("&nbsp;&nbsp;  "+messageField+" should be greater than or equal to "+minValue); 
				}
			}else{
				$("#"+errorDiv).html("&nbsp;&nbsp;  "+messageField+" should contain only numbers"); 
			}
		}
		return returnValue;
} 


function checkForMMDDYYYYDateFields(fieldId,errorDiv,messageField) {
	   var isValidDate = false;
  	    var inputvalue = $('#'+fieldId).val();
		if(inputvalue=='')  {
				$('#'+errorDiv).html("&nbsp;&nbsp; "+messageField+" is required.");
				isValidDate = false;
		}else{
				var pattern = /^([0-9]{2})-([0-9]{2})-([0-9]{4})$/;
				if(pattern.test(inputvalue)){
					$('#'+errorDiv).html("");
					isValidDate = true;
				}else{
					$('#'+errorDiv).html("&nbsp;&nbsp; "+messageField+" should be in MM-dd-yyyy format");
					isValidDate = false;
				}
		}
		return isValidDate;
  } 

  function compareMMDDYYYYDateFields(dateStr) {			
		var datearray = dateStr.split("/");
		var date1 = new Date();
		var nowDate = date1.getDate();
		var nowMonth = date1.getMonth();
		//getMonth() 	Returns the month (from 0-11)
		nowMonth = nowMonth+1;
		var nowYear = date1.getFullYear();

		var date = datearray[1];
		//var month = getIndex(datearray[1]);
		var month = datearray[0];
		var year = datearray[2];
		
		if(year>nowYear){
			//alert('Year is greater');
			return 1;
		} else if(year<nowYear){
			//alert('Year is smaller');
			return -1;
		}else{
			//alert('Year is equal');
			//alert("month  ----> "+month+" --  nowMonth "+nowMonth);
			if(month>nowMonth){
				//alert('Year is equal  and  Month is greater');
				return 1;
			}else if(month<nowMonth){
				//alert('Year is equal  and  Month is smaller');
				return -1;
			} else{
				//alert('Month is equal');
				if(date>nowDate){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return 1;
				}else if(date<nowDate){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return -1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
		}
	}

	function checkMMDDYYYYDateFields(fieldId,errorDiv,messageField) {
	   var isValidDate = false;
  	    var inputvalue = $('#'+fieldId).val();
		if(inputvalue=='')  {
				$('#'+errorDiv).html("&nbsp;&nbsp; "+messageField+" is required.");
				isValidDate = false;
		}else{
				var pattern = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
				if(pattern.test(inputvalue)){
					$('#'+errorDiv).html("");
					isValidDate = true;
				}else{
					$('#'+errorDiv).html("&nbsp;&nbsp; "+messageField+" should be in MM/dd/yyyy format");
					isValidDate = false;
				}
		}
		return isValidDate;
  } 

  function compareMMDDYYYYDateswithMaxDays(fromDate,toDate,days) {	
		//MM/DD/YYYY
		var fromDatearray = fromDate.split("/");
		var fromdate = parseInt(fromDatearray[1])+days;		
		//var month = getIndex(datearray[1]);
		var frommonth = fromDatearray[0];
		var fromyear = fromDatearray[2];
		
		var todatearray = toDate.split("/");
		var todate = todatearray[1];
		//var month = getIndex(todatearray[1]);
		var tomonth = todatearray[0];
		var toyear = todatearray[2];
		
		if(fromyear>toyear){
			//alert('Year is greater');
			return -1;
		} else if(fromyear<toyear){
			//alert('Year is smaller');
			return 1;
		}else{
			//alert('Year is equal');
			if(frommonth>tomonth){
				//alert('Year is equal  and  Month is greater');
				return -1;
			}else if(frommonth<tomonth){
				//alert('Year is equal  and  Month is smaller');
				return 1;
			} else{
				//alert('Month is equal');
				if(fromdate>todate){
					//alert('Year is equal ,Month is equal  and  date is greater');
					return -1;
				}else if(fromdate<todate){
					//alert('Year is equal ,Month is equal  and  date is smaller');
					return 1;
				}else{
					//alert('Date is equal');
					//alert('Year is equal ,Month is equal  and  date is equal');
					return 0;
				}
			}
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