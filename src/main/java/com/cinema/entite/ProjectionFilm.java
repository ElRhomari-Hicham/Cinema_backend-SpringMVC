package com.cinema.entite;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="prj1", types= {com.cinema.entite.Projection.class})
public interface ProjectionFilm {
	
	public Long getId();
	public double getPrix();
	public Date getDateProjection();
	public Salle getSalle();
	public Film getFilm(); 
	public Seance getSeance();
	public Collection<Ticket> getTickets();
}
