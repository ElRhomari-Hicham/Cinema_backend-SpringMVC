package com.cinema.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.dao.CinemaRepository;
import com.cinema.dao.FilmRepository;
import com.cinema.dao.TicketRepository;
import com.cinema.entite.Cinema;
import com.cinema.entite.Film;
import com.cinema.entite.Ticket;
import com.cinema.entite.TicketForm;

@RestController
public class cinemaController {
	
	@Autowired 
	private CinemaRepository cinemaRepository;
	@Autowired 
	private FilmRepository filmRepository;
	@Autowired 
	private TicketRepository ticketRepository;
	
	@GetMapping(path="/imageFilm/{id}", produces=MediaType.IMAGE_JPEG_VALUE)
	public byte[] images(@PathVariable Long id) throws IOException{
		Film f = filmRepository.findById(id).get();
		String image = f.getPhoto();
		File file = new File(System.getProperty("user.home")+"/cinema/images/"+image);
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}
	
	@CrossOrigin("http://localhost:4200")
	@PostMapping(path="/paiementTickets")
	@Transactional
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
		List<Ticket> tickets = new ArrayList<>();
		ticketForm.getTickets().forEach(idTicket -> {
			Ticket ticket = ticketRepository.findById(idTicket).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setCodePayement(ticketForm.getCodePaiement());
			ticket.setReserve(true);
			ticketRepository.save(ticket);
			tickets.add(ticket);
		});
		return tickets;
	}
	
	@GetMapping(path="/cinema")
	public List<Cinema> getCinemas(){
		return cinemaRepository.findAll();
	}
	
	@GetMapping(path="/cinema/{id}")
	public Cinema getCinema(@PathVariable Long id) {
		return cinemaRepository.findById(id).get();
	}
	
	@PostMapping(path="/cinema")
	public Cinema saveCinema(@RequestBody Cinema cinema) {
		return cinemaRepository.save(cinema);
	}
	
	@DeleteMapping(path="/cinema/{id}")
	public void deleteCinema(@PathVariable Long id) {
		cinemaRepository.deleteById(id);
	}
	
	@PutMapping(path="/cinema/{id}")
	public Cinema updateCinema(@RequestBody Cinema cinema,@PathVariable Long id) {
		cinema.setId(id);
		return cinemaRepository.save(cinema);
	}
}
