package com.oro_2;

import com.oro_2.entities.*;

import com.oro_2.entities.*;
import com.oro_2.repositories.*;
import com.oro_2.services.MovieHallShowingService;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Oro2ApplicationTests {

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ShowingRepository showingRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private TicketTypeRepository ticketTypeRepository;

	@Autowired
	private UserRepository userRepository;

	private List<Hall> halls;
	private List<Movie> movies;
	private List<Reservation> reservations;
	private List<Seat> seats;
	private List<TicketType> ticketTypes;
	private List<User> users;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	@Before
	public void setUp() {

		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		if (movieRepository.count() > 0 ||
			hallRepository.count() > 0 ||
			seatRepository.count() > 0 ||
			showingRepository.count() > 0 ||
			userRepository.count() > 0 ||
			ticketTypeRepository.count() > 0 ||
			reservationRepository.count() > 0) {
			return; // Returns if data present
		}


		MovieHallShowingService movieHallShowingService =
				new MovieHallShowingService(movieRepository, hallRepository, showingRepository);

		ticketTypes = List.of(
				TicketType.builder()
						  .price(new BigDecimal("123.45"))
						  .name("Student")
						  .build(),
				TicketType.builder()
						  .price(new BigDecimal("333.33"))
						  .name("Normal")
						  .build(),
				TicketType.builder()
						  .price(new BigDecimal("203.33"))
						  .name("Elderly")
						  .build()
		);


		halls = List.of(
				Hall.builder()
					.hallLocation("1st floor")
					.showings(new ArrayList<>())
					.seats(new ArrayList<>())
					.build(),
				Hall.builder()
					.hallLocation("2nd floor")
					.showings(new ArrayList<>())
					.seats(new ArrayList<>())
					.build(),
				Hall.builder()
					.hallLocation("Basement")
					.showings(new ArrayList<>())
					.seats(new ArrayList<>())
					.build()
		);

		seats = new ArrayList<>();
		int numRows = 3;
		int numColumns = 3;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				Seat seat = Seat.builder()
								.row(i)
								.column(j)
								.hall(halls.get(i))
								.build();
				seats.add(seat);
			}
		}

		for (Seat seat : seats) {
			Hall hall = seat.getHall();
			hall.getSeats().add(seat);
		}


		movies = List.of(
				Movie.builder()
					 .movieTitle("Citizen Kane")
					 .showings(new ArrayList<>())
					 .build(),
				Movie.builder()
					 .movieTitle("21st Downing Street")
					 .showings(new ArrayList<>())
					 .build(),
				Movie.builder()
					 .movieTitle("James Bond")
					 .showings(new ArrayList<>())
					 .build()
		);

		hallRepository.saveAll(halls);
		movieRepository.saveAll(movies);
		seatRepository.saveAll(seats);

		movieHallShowingService.createMovieHallShowing(movies.get(0), halls.get(0),
													   LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusDays(1));

		movieHallShowingService.createMovieHallShowing(movies.get(1), halls.get(1),
													   LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusDays(2));

		movieHallShowingService.createMovieHallShowing(movies.get(1), halls.get(2),
													   LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusDays(2));

		movieHallShowingService.createMovieHallShowing(movies.get(1), halls.get(2),
													   LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusDays(3));

		movieHallShowingService.createMovieHallShowing(movies.get(1), halls.get(2),
													   LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).plusDays(1));

		users = List.of(
				User.builder()
					.username("John132")
					.reservations(new ArrayList<>())
					.build(),
				User.builder()
					.username("Jane555")
					.reservations(new ArrayList<>())
					.build(),
				User.builder()
					.username("Jamie777")
					.reservations(new ArrayList<>())
					.build()
		);

		ArrayList<Showing> showings = (ArrayList<Showing>) showingRepository.findAll();

		reservations = List.of( Reservation.builder()
										   .user(users.get(0))
										   .type(ticketTypes.get(0))
										   .showing(showings.get(0))
										   .seat(showings.get(0).getHall().getSeats().get(0))
										   .buyTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusDays(1L))
										   .build(),
								Reservation.builder()
										   .user(users.get(1))
										   .type(ticketTypes.get(1))
										   .showing(showings.get(1))
										   .seat(showings.get(1).getHall().getSeats().get(0))
										   .buyTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusDays(1L))
										   .build(),
								Reservation.builder()
										   .user(users.get(1))
										   .type(ticketTypes.get(1))
										   .showing(showings.get(2))
										   .seat(showings.get(2).getHall().getSeats().get(0))
										   .buyTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusDays(1L))
										   .build(),
								Reservation.builder()
										   .user(users.get(0))
										   .type(ticketTypes.get(2))
										   .showing(showings.get(3))
										   .seat(showings.get(3).getHall().getSeats().get(0))
										   .buyTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusDays(1L))
										   .build()
								);

		userRepository.saveAll(users);
		ticketTypeRepository.saveAll(ticketTypes);
		reservationRepository.saveAll(reservations);
	}

	@After
	public void tearDown() {
		entityManager.getTransaction().rollback();
		entityManager.close();
	}

	@Test
	public void testFindMoviesByRoomId() {
		TypedQuery<Movie> query = entityManager.createQuery("SELECT s.movie FROM Showing s WHERE s.hall.id = :roomId", Movie.class);
		query.setParameter("roomId", 1L);
		List<Movie> resultList = query.getResultList();
		System.out.println("Lista filmów, które grane są w pokoju o ID: 1");
		for (int i = 0; i < resultList.size(); i++)
		{
			System.out.println("Film id: " + resultList.get(i).getId());
			System.out.println("Film title: " + resultList.get(i).getMovieTitle());
		}
		assertEquals(1, resultList.size());
	}

	@Test
	public void testFindShowingsByRoomId() {

		TypedQuery<Showing> query = entityManager.createQuery("SELECT s FROM Showing s WHERE s.hall.id = :roomId", Showing.class);
		query.setParameter("roomId", 1L);
		List<Showing> resultList = query.getResultList();
		assertEquals(1, resultList.size());
	}

	@Test
	public void testFindShowingsByMovieId() {
		
		TypedQuery<Showing> query = entityManager.createQuery("SELECT s FROM Showing s WHERE s.movie.id = :movieId", Showing.class);
		query.setParameter("movieId", 1L);
		List<Showing> resultList = query.getResultList();
		assertEquals(1, resultList.size());
	}

	@Test
	public void testFindShowingsByMovieTitle() {
		
		TypedQuery<Showing> query = entityManager.createQuery("SELECT s FROM Showing s WHERE s.movie.movieTitle = :movieTitle", Showing.class);
		query.setParameter("movieTitle", "Citizen Kane");
		List<Showing> resultList = query.getResultList();
		assertEquals(1, resultList.size());
	}

	@Test
	public void testFindUsersByShowingId() {
		
		TypedQuery<User> query = entityManager.createQuery("SELECT b.user FROM Reservation b WHERE b.showing.id = :ShowingId", User.class);
		query.setParameter("ShowingId", 1L);
		List<User> resultList = query.getResultList();
		assertEquals(1, resultList.size());
	}

	@Test
	public void testFindShowingsByUserId() {
		
		TypedQuery<Showing> query = entityManager.createQuery("SELECT b.showing FROM Reservation b WHERE b.user.id = :userId", Showing.class);
		query.setParameter("userId", 1L);
		List<Showing> resultList = query.getResultList();
		assertEquals(2, resultList.size());
	}

	@Test
	public void testFindShowingsByUserLogin() {
		TypedQuery<Showing> query = entityManager.createQuery("SELECT b.showing FROM Reservation b WHERE b.user.username = :userLogin", Showing.class);
		query.setParameter("userLogin", "John132");
		List<Showing> resultList = query.getResultList();
		assertEquals(2, resultList.size());
	}

	@Test
	public void testFindOccupiedSeatsByRoomAndTime() {

		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(b.seat) FROM Reservation b WHERE b.showing.hall.id = :roomId AND b.showing.showTime = :time", Long.class);
		query.setParameter("roomId", 3L);
		query.setParameter("time", LocalDateTime.parse("2023-03-31 09:07:00.000000", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")));
		List<Long> resultList = query.getResultList();
		assertEquals(Long.valueOf(1), resultList.size());
	}


	@Test
	public void testFindRoomsByMovieId() {
		TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(DISTINCT s.hall.id) FROM Showing s WHERE s.movie.id = :movieId", Long.class);
		query.setParameter("movieId", 1L);
		List<Long> resultList = query.getResultList();
		assertEquals(Long.valueOf(1), resultList.size());
	}

	@Test
	public void testFindTotalSeatsBoughtByUserInDateRange() {
		TypedQuery<Long> query = entityManager.createQuery(
				"SELECT COUNT(b.seat) FROM Reservation b WHERE b.user.id = :userId AND b.buyTime BETWEEN :startDateTime AND :endDateTime", Long.class);
		query.setParameter("userId", 1L);
		query.setParameter("startDateTime", LocalDateTime.now().minusDays(7));
		query.setParameter("endDateTime", LocalDateTime.now());
		List<Long> resultList = query.getResultList();
		assertEquals(Long.valueOf(1), resultList.size());
	}

}