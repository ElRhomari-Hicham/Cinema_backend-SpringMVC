package com.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.cinema.dao.CinemaInitService;
import com.cinema.dao.CinemaRepository;
import com.cinema.entite.Cinema;
import com.cinema.entite.Film;
import com.cinema.entite.Salle;
import com.cinema.entite.Ticket;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner{

//	@Autowired private CinemaInitService cinemaInitService;
	@Autowired private RepositoryRestConfiguration restconfiguration;

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		restconfiguration.exposeIdsFor(Film.class);
		restconfiguration.exposeIdsFor(Salle.class);
		restconfiguration.exposeIdsFor(Ticket.class);
//		cinemaInitService.initVilles();
//		cinemaInitService.initCinemas();
//		cinemaInitService.initSalles();
//		cinemaInitService.initPlaces();
//		cinemaInitService.initSeances();
//		cinemaInitService.initCategories();
//		cinemaInitService.initFilms();
//		cinemaInitService.initProjections();
//		cinemaInitService.initTickets();
	}

}
