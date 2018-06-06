// fiunctions for default values in text fields
function onBlur(el) {
    if (el.value == '') {
        el.value = el.defaultValue;
    }
}
function onFocus(el) {
    if (el.value == el.defaultValue) {
        el.value = '';
    }
}


  

 // for Upcomint Reservation
function getStyleUpcomintReservation()
   {
      var temp = document.getElementById("UpcomintReservation").style.display;
  
      return temp;
   }

 function switchMainUpcomintReservation()
  {

      var current = getStyleUpcomintReservation();

      if( current == "none" )
       {
           document.getElementById("UpcomintReservation").style.display = "block";
           document.getElementById("expnClsp1").src = "static/images/minus.png";
       }
       else
       {
         document.getElementById("UpcomintReservation").style.display = "none";
        document.getElementById("expnClsp1").src = "static/images/plus.png";
       }
}  

// for CustmerInfo
function getStyleCustomerInfo()
   {
      var temp = document.getElementById("CustomerInfo").style.display;
  
      return temp;
   }

 function switchMainCustomerInfo()
  {

      var current = getStyleCustomerInfo();

      if( current == "none" )
       {
         document.getElementById("CustomerInfo").style.display = "block";
           document.getElementById("expnClsp2").src = "static/images/minus.png";
       }
       else
       {
         document.getElementById("CustomerInfo").style.display = "none";
        document.getElementById("expnClsp2").src = "static/images/plus.png";
       }
} 

//tabs

// for Past Reservation
function getStylePastReservation()
   {
      var temp = document.getElementById("PastReservation").style.display;
  
      return temp;
   }

 function switchMainPastReservation()
  {

      var current = getStylePastReservation();

      if( current == "none" )
       {
         document.getElementById("PastReservation").style.display = "block";
           document.getElementById("expnClsp3").src = "static/images/minus.png";
       }
       else
       {
         document.getElementById("PastReservation").style.display = "none";
        document.getElementById("expnClsp3").src = "static/images/plus.png";
       }
}  


