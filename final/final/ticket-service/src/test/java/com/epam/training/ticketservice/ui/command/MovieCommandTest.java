package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.shell.Availability;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieCommandTest {
    private final MovieService movieService = mock(MovieService.class);
    private final UserService userService = mock(UserService.class);

    private MovieCommand underTest = new MovieCommand(movieService, userService);

    @Test
    public void testCreateMovie() {
        MovieDto movieDto = new MovieDto("movie1", "drama", 10);


        MovieDto actual = underTest.createMovie("movie1", "drama", 10);

        verify(movieService).createMovie(movieDto);
        assertEquals(movieDto, actual);
    }

    @Test
    public void testIsAvailableUser() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        UserDto userDto = new UserDto("user", User.Role.USER);
        when(userService.describeAccount()).thenReturn(Optional.of(userDto));
        Availability expected = Availability.unavailable("You are not an admin!");

        Method isAvailable = MovieCommand.class.getDeclaredMethod("isAvailable");
        isAvailable.setAccessible(true);
        Availability actual = (Availability)isAvailable.invoke(underTest);

        assertEquals(expected.isAvailable(), actual.isAvailable());
    }

    @Test
    public void testIsAvailableAdmin() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        UserDto userDto = new UserDto("user", User.Role.ADMIN);
        when(userService.describeAccount()).thenReturn(Optional.of(userDto));
        Availability expected = Availability.available();

        Method isAvailable = MovieCommand.class.getDeclaredMethod("isAvailable");
        isAvailable.setAccessible(true);
        Availability actual = (Availability)isAvailable.invoke(underTest);

        assertEquals(expected.isAvailable(), actual.isAvailable());
        assertEquals(expected.getReason(), actual.getReason());
    }

    @Test
    public void testIsAvailableNotLoggedIn() throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        when(userService.describeAccount()).thenReturn(Optional.empty());
        Availability expected = Availability.unavailable("You are not an admin!");

        Method isAvailable = MovieCommand.class.getDeclaredMethod("isAvailable");
        isAvailable.setAccessible(true);
        Availability actual = (Availability)isAvailable.invoke(underTest);

        assertEquals(expected.isAvailable(), actual.isAvailable());
        assertEquals(expected.getReason(), actual.getReason());
    }
    @Test
    public void testListMoviesShouldReturnCorrectListOfExistingMovies() {
        //Given
        List<MovieDto> list = List.of(
                MovieDto.builder()
                        .withName("Spirited Away")
                        .withGenre("animation")
                        .withLength(125)
                        .build());
        when(movieService.getMovieList()).thenReturn(list);
        String expected = "[Spirited Away(animation,125 minutes)]";

        //When
        String actual = underTest.listAvailableMovies().toString();

        //Then
        assertEquals(expected, actual);
        verify(movieService).getMovieList();
    }

    @Test
    public void testListMoviesShouldReturnEmptyList() {
        //Given
        when(movieService.getMovieList()).thenReturn(List.of());
        String expected = "[]";

        //When
        String actual = underTest.listAvailableMovies().toString();

        //Then
        assertEquals(expected, actual);
        verify(movieService).getMovieList();
    }

}