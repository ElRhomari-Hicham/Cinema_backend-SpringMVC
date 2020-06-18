package com.cinema.entite;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TicketForm {

	private String nomClient;
	private int codePaiement;
	private List<Long> tickets = new ArrayList<Long>();
	
}