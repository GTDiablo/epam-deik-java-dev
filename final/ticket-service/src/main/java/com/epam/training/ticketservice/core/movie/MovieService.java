package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<MovieDto> getMovieList();

    Optional<MovieDto> getMovieByName(String movieName);

    void createMovie(MovieDto movie);

    void updateMovie(MovieDto movie);

    void deleteMovie(String movieDto);

    Integer getMovieID(String title);
}
