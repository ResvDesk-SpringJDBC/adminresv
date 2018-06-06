<title>Reservation > Calendar</title>

<!-- Body starts -->
  <form name="">
    <div class="dailyCal_head">
      <div class="cal_head-new">Reservation &gt; Calendar   &gt;</div>
      <div class="cal_button"> 
		<a href="seat-view-calendar.html">Seat View</a> 
		<a href="daily-view-calendar.html">Daily View</a> 
		<a href="javascript:void(0)" class="active">Monthly View</a> 
	  </div>
      <div id="special-demo-left" class="sm2-inline-list" style="float:right">
        <div class="dailyCal_refresh" style="float:right"> <a title="Refresh" href="javascript:doNothing()"></a> </div>
      </div>
      <div style="clear:both"></div>
    </div>
    <!--Search bar starts -->
    <div class="search-bar">
      <div class="float-left"> Locations:
        <select id="locationId" name="locationId">
          <option value="4" selected="selected">Midfield - 589 Bessemer Super Hwy</option>
        </select>
      </div>
      <div class="float-left"> Intakes:
        <select id="resourceId" name="resourceId">
          <option value="-1">All Intakes</option>
          <option value="1" selected="selected"> I 1A</option>
          <option value="9"> I 1B</option>
          <option value="2"> I 2A</option>
          <option value="10"> I 2B</option>
          <option value="3"> I 3A</option>
          <option value="11"> I 3B</option>
          <option value="4"> I 4A</option>
          <option value="12"> I 4B</option>
          <option value="5"> I 5A</option>
          <option value="13"> I 5B</option>
          <option value="6"> I 6A</option>
          <option value="14"> I 6B</option>
          <option value="7"> I 7A</option>
          <option value="15"> I 7B</option>
        </select>
      </div>
      <input id="serviceId" name="serviceId" value="-1" type="hidden">
      <div class="clear-all"></div>
    </div>
    <!--Search bar starts --> 
    
    <!--Options starts -->
    <div class="options">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tbody>
          <tr>
            <td style="text-align:center;" class="font16 txt-bold">
				<a href="javascript:doNothing()" onclick="previousMonthSelected();" style="text-decoration: none;"> 
					<img src="static/images/previous-month.png" alt="Previous Month" title="Previous Month" style="vertical-align:bottom"> 
				</a> February, 2015
              <input id="arrowTypeReq" name="arrowTypeReq" type="hidden" value="0">
              <input id="selectedDate" name="selectedDate" value="02/02/2015" type="hidden">
              <a href="javascript:doNothing()" onclick="nextMonthSelected();" style="text-decoration: none;"> 
				<img src="static/images/next-month.png" alt="Next Week" title="Next Month" style="vertical-align:bottom"> 
			  </a>
			</td>
            <td align="right" width="200" style="text-align:right"><span style="position: relative;">
              <input id="jsCalenderDate" name="jsCalenderDate" value="02/02/2015" type="text" globalnumber="393">
              </span>
              <input name="btnSearch" type="submit" class="btn" value="Go" onclick="jsCalenderDateSelected();"></td>
          </tr>
        </tbody>
      </table>
    </div>
    
    
    <!--Options ends --> 
    
    <!-- Main tables starts -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="main-table">
      <tbody>
        <tr>
          <th width="14%" class="center-align">Sun</th>
          <th width="14%" class="center-align">Mon</th>
          <th width="14%" class="center-align">Tue</th>
          <th width="14%" class="center-align">Wed</th>
          <th width="14%" class="center-align">Thu</th>
          <th width="14%" class="center-align">Fri</th>
          <th width="14%" class="center-align">Sat</th>
        </tr>
        <tr>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-01');"> 1 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp; </div></td>
          <td class="current-date" bgcolor="#9BD4A1" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-02');"> 2 </a>
            <div class="mon-cal-info-no-dots">&nbsp; </div>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">22</span> </div></td>
          <td bgcolor="#9BD4A1" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-03');"> 3 </a>
            <div class="mon-cal-info-no-dots">&nbsp; </div>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">22</span> </div></td>
          <td bgcolor="#9BD4A1" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-04');"> 4 </a>
            <div class="mon-cal-info-no-dots">&nbsp; </div>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">22</span> </div></td>
          <td bgcolor="#9BD4A1" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-05');"> 5 </a>
            <div class="mon-cal-info-no-dots">&nbsp; </div>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">22</span> </div></td>
          <td bgcolor="#9BD4A1" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-06');"> 6 </a>
            <div class="mon-cal-info-no-dots">&nbsp; </div>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">22</span> </div></td>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-07');"> 7 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp; </div></td>
        </tr>
        <tr>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-08');"> 8 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp; </div></td>
          <td bgcolor="#9BD4A1" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-09');"> 9 </a>
            <div class="mon-cal-info-no-dots">&nbsp; </div>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">22</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-10');"> 10 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">9</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">13</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-11');"> 11 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">3</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">19</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-12');"> 12 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">2</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">20</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-13');"> 13 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">6</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">16</span> </div></td>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-14');"> 14 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp; </div></td>
        </tr>
        <tr>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-15');"> 15 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp; </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-16');"> 16 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">5</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">17</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-17');"> 17 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">3</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">19</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-18');"> 18 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">0</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">22</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-19');"> 19 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">1</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">21</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-20');"> 20 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">0</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">14</span> </div></td>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-21');"> 21 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp; </div></td>
        </tr>
        <tr>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-22');"> 22 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp; </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-23');"> 23 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">2</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">20</span> </div></td>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" alt="Closed Day" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-24');"> 24 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Closed Day</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp;</div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-25');"> 25 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">3</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">19</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-26');"> 26 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">1</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">21</span> </div></td>
          <td bgcolor="#E4E38F" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-27');"> 27 </a>
            <div class="mon-cal-info"> <img src="images/cal-confirmed.png" width="20" height="20">Booked Seats: <span class="txt-bold">7</span> </div>
            <div class="mon-cal-info"> <img src="images/cal-cancelled.png" width="20" height="20">Open Seats: <span class="txt-bold">15</span> </div></td>
          <td bgcolor="#FFA16D" height="100"><a class="cal-date" href="javascript:donothing();" onclick="getDailyCalendar('2015-02-28');"> 28 </a>
            <div class="mon-cal-info-no-dots" style="text-align:center;"><font size="20">Not Available</font> </div>
            <div class="mon-cal-info-no-dots">&nbsp; </div></td>
        </tr>
        <tr> </tr>
      </tbody>
    </table>
    
    <!-- Main tables ends -->
    <div id="tips">
      <div class="tips-box appt"></div>
      <div class="tips-box-text">Open</div>
      <div class="tips-box new-patient"></div>
      <div class="tips-box-text">In Progress</div>
      <div class="tips-box avail"></div>
      <div class="tips-box-text">Booked</div>
      <div class="tips-box nowrk"></div>
      <div class="tips-box-text">No Time Slots Available</div>
      <div class="tips-box other"></div>
      <div class="tips-box-text">Others</div>
      <div class="clear-all"></div>
    </div>
  </form>

<!-- Body ends -->