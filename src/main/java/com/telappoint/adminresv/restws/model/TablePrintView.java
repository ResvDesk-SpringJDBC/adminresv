package com.telappoint.adminresv.restws.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * @author Murali G
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TablePrintView {
	
	private String totalSeats;
	private String bookedSeats;
	private String date;
	private String time;
	private String eventDateTimeId;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(String totalSeats) {
		this.totalSeats = totalSeats;
	}

	public String getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(String bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public String getEventDateTimeId() {
		return eventDateTimeId;
	}

	public void setEventDateTimeId(String eventDateTimeId) {
		this.eventDateTimeId = eventDateTimeId;
	}
	
	/*@JsonCreator
    public static TablePrintView fromJSON(String val) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        TablePrintView a = mapper.readValue(val,TablePrintView.class);
        return a;
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventDateTimeId == null) ? 0 : eventDateTimeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TablePrintView other = (TablePrintView) obj;
		if (eventDateTimeId == null) {
			if (other.eventDateTimeId != null)
				return false;
		} else if (!eventDateTimeId.equals(other.eventDateTimeId))
			return false;
		return true;
	}*/

	@Override
	public String toString() {
		return "TablePrintView [totalSeats=" + totalSeats + ", bookedSeats=" + bookedSeats + ", date=" + date + ", time=" + time + ", eventDateTimeId=" + eventDateTimeId + "]";
	}
}
