package com.cinema.entite;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="ticketProj", types= {Ticket.class})
public interface ProjectionTicket {
	
	public Long getId();
	public String gteNomClient();
	public double getPrix();
	public int getCodePayement();
	public boolean getReserve();
	public Place getPlace();

}
