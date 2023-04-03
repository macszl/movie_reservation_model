package com.oro_2.services;

import com.oro_2.entities.*;
import com.oro_2.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.Optional;

@Service
@Transactional
public class MovieHallShowingService {

	private final MovieRepository movieRepository;
	private final HallRepository hallRepository;
	private final ShowingRepository showingRepository;

	public MovieHallShowingService(MovieRepository movieRepository, HallRepository hallRepository,
								   ShowingRepository showingRepository) {
		this.movieRepository = movieRepository;
		this.hallRepository = hallRepository;
		this.showingRepository = showingRepository;
	}

	public void createMovieHallShowing(Movie movie, Hall hall, LocalDateTime showTime) {
		Optional<Movie> existingMovie = movieRepository.findById(movie.getId());
		if (existingMovie.isPresent()) {
			movie = existingMovie.get();
		} else {
			return;
		}

		Optional<Hall> existingHall = hallRepository.findById(hall.getId());
		if (existingHall.isPresent()) {
			hall = existingHall.get();
		} else {
			return;
		}

		Showing showing = Showing.builder().movie(movie).hall(hall).showTime(showTime).build();
		showingRepository.save(showing);

		movie.getShowings().add(showing);
		hall.getShowings().add(showing);
	}
}
