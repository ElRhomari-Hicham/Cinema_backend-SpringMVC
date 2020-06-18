package com.cinema.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.entite.Categorie;
import com.cinema.entite.Cinema;
import com.cinema.entite.Film;
import com.cinema.entite.Place;
import com.cinema.entite.Projection;
import com.cinema.entite.Salle;
import com.cinema.entite.Seance;
import com.cinema.entite.Ticket;
import com.cinema.entite.Ville;

@Service
@Transactional
public class CinemaInitServiceIMPL implements CinemaInitService{

	@Autowired private VilleRepository villeRepository;
	@Autowired private CinemaRepository cinemaReposiroty;
	@Autowired private FilmRepository filmRepository;
	@Autowired private CategorieRepository categorieRepository;
	@Autowired private PlaceRepository placeRepository;
	@Autowired private ProjectionRepository projectionRepository;
	@Autowired private SalleRepository salleRepository;
	@Autowired private SeanceRepository seanceRepository;
	@Autowired private TicketRepository ticketRepository;
	
	@Override
	public void initVilles() {
		// TODO Auto-generated method stub
		Stream.of("Agadir","Casablaca","Rabat","Mohemmmadia").forEach(nameVille -> {
			System.out.println(nameVille);
			Ville ville = new Ville();
			ville.setName(nameVille);
			villeRepository.save(ville);
		});
	}
	@Override
	public void initCinemas() {
		// TODO Auto-generated method stub
		villeRepository.findAll().forEach(v -> {
			Stream.of("Megarama","IMAX","FOUNOUN","CHAHRAZAD","DAOULIZ").forEach(nameCinema -> {
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setNbrSalles(3+(int)(Math.random()*7));
				cinema.setVille(v);
				cinemaReposiroty.save(cinema);
			});
		});
	}
	@Override
	public void initSalles() {
		// TODO Auto-generated method stub
		cinemaReposiroty.findAll().forEach(c -> {
			for(int i=0; i<c.getNbrSalles(); i++) {
				Salle salle = new Salle();
				salle.setName("Salle N° "+(i+1));
				salle.setCinema(c);
				salle.setNbrPlaces(15+(int)(Math.random())*20);
				salleRepository.save(salle);
			}
		});
	}
	@Override
	public void initPlaces() {
		// TODO Auto-generated method stub
		salleRepository.findAll().forEach(s -> {
			for(int i=0; i<s.getNbrPlaces(); i++) {
				Place place = new Place();
				place.setNumero(i+1);
				place.setSalle(s);
				placeRepository.save(place);
			}
		});
	}
	@Override
	public void initSeances() {
		// TODO Auto-generated method stub
		DateFormat dateFormat= new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(d -> {
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(d));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seanceRepository.save(seance);
		});
		
	}
	@Override
	public void initCategories() {
		// TODO Auto-generated method stub
		Stream.of("Histoire","Action","Science-Fiction","Drama").forEach(c -> {
			Categorie categorie = new Categorie();
			categorie.setName(c);
			categorieRepository.save(categorie);
		});
	}
	@Override
	public void initFilms() {
		// TODO Auto-generated method stub
		double[] durees = new double[] {1,1.5,2,2.5,3};
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("Fifty Shades","Exctraction","No Escape","Need For Speed","Ip Man","Gemini").forEach(f -> {
			Film film = new Film();
			film.setTitre(f);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setPhoto(f.replaceAll(" ", " ")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);
		});
	}
	@Override
	public void initProjections() {
		// TODO Auto-generated method stub
		double[] prices = new double[] {30,40,50,60,70,80,90,100};
		List<Film> films = filmRepository.findAll(); 
		villeRepository.findAll().forEach(v -> {
			v.getCinemas().forEach(c -> {
				c.getSalles().forEach(s -> {
					int index = new Random().nextInt(films.size());
					Film f = films.get(index);
						seanceRepository.findAll().forEach(se -> {
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(f);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSalle(s);
							projection.setSeance(se);
							projectionRepository.save(projection);
						});
				});
			});
		});
	}
	@Override
	public void initTickets() {
		// TODO Auto-generated method stub
		projectionRepository.findAll().forEach(pr -> {
			pr.getSalle().getPlaces().forEach(pl -> {
				Ticket ticket = new Ticket();
				ticket.setNomClient("Unkown");
				ticket.setPlace(pl);
				ticket.setPrix(pr.getPrix());
				ticket.setProjection(pr);
				//ticket.setReserve(false);
				ticketRepository.save(ticket);
			});
		});
	}
}
