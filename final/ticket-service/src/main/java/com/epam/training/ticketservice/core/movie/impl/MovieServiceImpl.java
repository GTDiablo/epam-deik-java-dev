package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDto> getMovieList() {
        return movieRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<MovieDto> getMovieByName(String movieName) {
        return convertEntityToDto(movieRepository.findByName(movieName));
    }


    @Override
    public void createMovie(MovieDto movieDto) {
        Objects.requireNonNull(movieDto, "Movie cannot be null.");
        Objects.requireNonNull(movieDto.getName(), "Movie name cannot be null.");
        Objects.requireNonNull(movieDto.getGenre(),"Movie genre cannot be null.");
        Objects.requireNonNull(movieDto.getLength(), "Movie length cannot be null.");
        Movie movie = new Movie(movieDto.getName(),movieDto.getGenre(), movieDto.getLength());
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(MovieDto movieDto) {
        Objects.requireNonNull(movieDto, "Movie cannot be null");
        Objects.requireNonNull(movieDto.getName(), "Movie Name cannot be null");
        Objects.requireNonNull(movieDto.getGenre(), "Movie genre cannot be null.");

        movieRepository.updateMovie(movieDto.getName(), movieDto.getGenre(), movieDto.getLength());
    }

    @Override
    public void deleteMovie(String movieName) {
        Objects.requireNonNull(movieName, "Movie name cannot be null");
        movieRepository.deleteByName(movieName);
    }

    @Override
    public Integer getMovieID(String title) {
        Optional<Movie> movie =
                movieRepository.findByName(title);
        if (movie.isPresent()) {
            return movie.get().getId();
        }
        throw new NullPointerException("The movie doesn't exist.");
    }

    private MovieDto convertEntityToDto(Movie movie) {
        return MovieDto.builder()
                .withName(movie.getName())
                .withGenre(movie.getGenre())
                .withLength(movie.getLength())
                .build();
    }

    private Optional<MovieDto> convertEntityToDto(Optional<Movie> movie) {
        return movie.isEmpty() ? Optional.empty() : Optional.of(convertEntityToDto(movie.get()));
    }

}
