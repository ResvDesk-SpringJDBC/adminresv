  $(function () {
		prepareDatesMapAfterAvailDatesLoaded();
		initiateJSCalender("stackedChartDate");

		//Stacked/Bar Chart
		$('#stackedChartLocId').change(function() {
			var stackedChartLocId = $("#stackedChartLocId").val();
			var url = "loadJSCalendarDates.html?locationId="+stackedChartLocId;
			//alert("url  ::::::::::: "+url);
			$( "#dateHiddenDivId" ).load(url,function() {				
				prepareDatesMapAfterAvailDatesLoaded();
				initiateJSCalender("stackedChartDate");
				$("#stackedChartDate").val($("#jsCalFirstDate").val());
				getAndDrawStackedChartGraphData();				
			});			
		});
		
		/*
		stackedChart_date_calendarObject = new JsDatePick({
			useMode : 2,
			target : "stackedChartDate",
			dateFormat : "%M/%d/%Y"
		});
		stackedChart_date_calendarObject.addOnSelectedDelegate(function(){
			getAndDrawStackedChartGraphData();
		});	
		*/
		
		getAndDrawStackedChartGraphData();
  });

function jsCalenderDateSelected(){
	getAndDrawStackedChartGraphData();
}

function prepareArray(str){
	var temp= new Array();
	temp = str.split(",");
	for (a in temp ) {
		temp[a] = parseInt(temp[a], 10);
	}
	return temp;
}


//***************************************************   Stacked Chart Related Functionality Starts  ****************************

	function getAndDrawStackedChartGraphData(){				
		var stackedChartLocId = $("#stackedChartLocId").val();
		var stackedChartDate = $("#stackedChartDate").val();
		var graphsURL = "getGraphsData.html?requestedGraphType=STACKED&locationId="+stackedChartLocId+"&date="+stackedChartDate;

		$.get( graphsURL, function( data ) {
			var json = $.parseJSON(data);
			//alert("eventTimeStr ::::: "+(json.eventTimeStr));
			//alert("noOfBookedResv ::::: "+(json.noOfBookedResv));
			//alert("noOfOpenResv ::::: "+(json.noOfOpenResv));
			//alert("noOfResvered ::::: "+(json.noOfResvered));
			drawStackedChart(json.eventTimeStr,json.noOfBookedResv,json.noOfOpenResv,json.noOfResvered);
		});
	}

	function drawStackedChart(eventTimeStr,noOfBookedResv,noOfOpenResv,noOfResvered){
		applyHighChartsTheams();
	
	    // Apply the theme
       var highchartsOptions = Highcharts.setOptions(Highcharts.theme);

		eventTimeStr = eventTimeStr.split(",");
		noOfBookedResv = prepareArray(noOfBookedResv);
		noOfOpenResv = prepareArray(noOfOpenResv);
		noOfResvered = prepareArray(noOfResvered);
		
		$('#stackedcharts').highcharts({    
            chart: {
                type: 'column'
            },    
            title: {
                text: 'Total Reservations'
            },
			/*
            subtitle: {
                text: '(in Time)'
            },*/
            xAxis: {
                //categories: ['10/04/2014', '11/04/2014', '14/04/2014', '15/04/2014', '16/04/2014']eventTimeStr 
				categories: eventTimeStr 
            },  
            yAxis: {
                allowDecimals: false,
                min: 0,
                title: {
                    text: 'Number of Reservations'
                }
            },    
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
						//'<b>'+  this.series.stack  +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>'+
                        'Total: '+ this.point.stackTotal;
                }
            },    
            plotOptions: {
                column: {
                    stacking: 'normal'
                }
            },    
            series: [{
                name: 'Open ',
                //data: [3, 4, 4, 2, 5],
				data: noOfOpenResv ,   
				color: '#ED561B',
                stack: 'Reservations'
            }, {
                name: 'Booked ',
                //data: [5, 3, 4, 7, 2],
				data: noOfBookedResv ,
				color: '#50B432',
                stack: 'Reservations'
            }, {
                name: 'Reserved ',
                //data: [5, 3, 4, 7, 2],
				data: noOfResvered ,
				color: '#24CBE5',
                stack: 'Reservations'
            }
			/*, {
                name: 'Confirmed ',
                //data: [3, 4, 4, 2, 5],
				data: noOfConfirmedNotifications ,
				color: '#24CBE5',
                stack: 'Notification'
            }, {
                name: 'UnConfirmed ',
                //data: [2, 5, 6, 2, 1],
				data: noOfUnConfirmedNotifications ,
				color: '#FFF263',
                stack: 'Notification'
            }*/
			]
        });
	}

//***************************************************   Stacked Chart Related Functionality Ends  ****************************

function applyHighChartsTheams(){
	Highcharts.theme = {
		   //colors: ['#50B432', '#ED561B', '#50B432', '#ED561B', '#24CBE5', '#DDDF00'],
		   colors: ['#00CED1','#00FA9A','#00FFFF','#4682B4','#66CDAA','#778899','#7FFFD4','#87CEEB','#8FBC8F','#A0522D','#A9A9A9','#B0C4DE','#BC8F8F','#C0C0C0','#D2B48C','#DCDCDC','#EEE8AA','#F5DEB3','#FAEBD7','#FF7F50','#FFDEAD','#996633','#33CCCC','#6699FF','#FF99CC','#FF6600'],
		   chart: {
			  backgroundColor: {
				 linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
				 stops: [
					[0, 'rgb(255, 255, 255)'],
					[1, 'rgb(240, 240, 255)']
				 ]
			  },
			  borderWidth: 2,
			  plotBackgroundColor: 'rgba(255, 255, 255, .9)',
			  plotShadow: true,
			  plotBorderWidth: 1
		   },
		   title: {
			  style: {
				 color: '#000',
				 font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
			  }
		   },
		   subtitle: {
			  style: {
				 color: '#666666',
				 font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
			  }
		   },
		   xAxis: {
			  gridLineWidth: 1,
			  lineColor: '#000',
			  tickColor: '#000',
			  labels: {
				 style: {
					color: '#000',
					font: '11px Trebuchet MS, Verdana, sans-serif'
				 }
			  },
			  title: {
				 style: {
					color: '#333',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: 'Trebuchet MS, Verdana, sans-serif'

				 }
			  }
		   },
		   yAxis: {
			  minorTickInterval: 'auto',
			  lineColor: '#000',
			  lineWidth: 1,
			  tickWidth: 1,
			  tickColor: '#000',
			  labels: {
				 style: {
					color: '#000',
					font: '11px Trebuchet MS, Verdana, sans-serif'
				 }
			  },
			  title: {
				 style: {
					color: '#333',
					fontWeight: 'bold',
					fontSize: '12px',
					fontFamily: 'Trebuchet MS, Verdana, sans-serif'
				 }
			  }
		   },
		   legend: {
			  itemStyle: {
				 font: '9pt Trebuchet MS, Verdana, sans-serif',
				 color: 'black'

			  },
			  itemHoverStyle: {
				 color: '#039'
			  },
			  itemHiddenStyle: {
				 color: 'gray'
			  }
		   },
		   labels: {
			  style: {
				 color: '#99b'
			  }
		   },

		   navigation: {
			  buttonOptions: {
				 theme: {
					stroke: '#CCCCCC'
				 }
			  }
		   }
		};
}