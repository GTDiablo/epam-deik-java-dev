package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class MovieCommand {
    private final UserService userService;
    private final MovieService movieService;

    public MovieCommand(MovieService movieService, UserService userService) {
        this.movieService = movieService;
        this.userService = userService;
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describeAccount();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Create a movie.")
    public MovieDto createMovie(String name, String genre, Integer length) {
        MovieDto movie = MovieDto.builder()
                .withName(name)
                .withGenre(genre)
                .withLength(length)
                .build();
        movieService.createMovie(movie);
        return movie;
    }

    @ShellMethod(key = "list movies", value = "List all available movies")
    public String listMovies() {
        List<MovieDto> movies = movieService.getMovieList();
        if (movies.size() == 0) {
            return "There are no movies at the moment";
        }
        StringBuilder buffer = new StringBuilder();
        for (MovieDto movie : movies) {
            buffer.append(movie).append("\n");
        }
        return buffer.deleteCharAt(buffer.length() - 1).toString();
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Update and existing movie.")
    public void updateMovie(String name, String genre, Integer length) {
        MovieDto movieDto = new MovieDto(
                name,
                genre,
                length);
        if (movieService.getMovieByName(movieDto.getName()).isEmpty()) {
            System.out.println("Movie does not exist");
        } else {
            movieService.updateMovie(movieDto);
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Deletes a movie.")
    public String deleteMovie(String name) {
        if (movieService.getMovieByName(name).isEmpty()) {
            return "Movie does not exist";
        } else {
            movieService.deleteMovie(name);
            return name + " deleted successfully.";
        }
    }
}

