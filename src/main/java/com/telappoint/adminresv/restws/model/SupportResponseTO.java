package com.telappoint.adminresv.restws.model;

import java.util.List;

public class SupportResponseTO {
	
	private List<TicketTO> ticketTOList;
	private List<TicketTO> closedTicketTOList;
	
	public List<TicketTO> getTicketTOList() {
		return ticketTOList;
	}
	public void setTicketTOList(List<TicketTO> ticketTOList) {
		this.ticketTOList = ticketTOList;
	}
	public List<TicketTO> getClosedTicketTOList() {
		return closedTicketTOList;
	}
	public void setClosedTicketTOList(List<TicketTO> closedTicketTOList) {
		this.closedTicketTOList = closedTicketTOList;
	}

}
