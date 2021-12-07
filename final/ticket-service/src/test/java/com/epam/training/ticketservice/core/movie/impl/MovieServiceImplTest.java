package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    private static final Movie SPIRITED_AWAY_ENTITY = new Movie("Spirited Away", "animation", 125);
    private static final Movie MOANA_ENTITY = new Movie("Moana", "animation", 106);

    private static final MovieDto SPIRITED_AWAY_DTO = MovieDto.builder()
            .withName("Spirited Away")
            .withGenre("animation")
            .withLength(125)
            .build();
    private static final MovieDto MOANA_DTO = MovieDto.builder()
            .withName("Moana")
            .withGenre("animation")
            .withLength(106)
            .build();

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieService underTest = new MovieServiceImpl(movieRepository);


    @Test
    void testGetMovieListShouldCallMovieRepositoryAndReturnListofMovies() {
        // Given
        when(movieRepository.findAll()).thenReturn(List.of(SPIRITED_AWAY_ENTITY, MOANA_ENTITY));
        List<MovieDto> expected = List.of(SPIRITED_AWAY_DTO, MOANA_DTO);

        // When
        List<MovieDto> actual = underTest.getMovieList();

        // Then
        assertEquals(expected, actual);
        verify(movieRepository).findAll();
    }

    @Test
    void testGetMovieByNameShouldReturnAMoanaDtoWhenTheMovieExists() {
        // Given
        when(movieRepository.findByName("Moana")).thenReturn(Optional.of(MOANA_ENTITY));
        Optional<MovieDto> expected = Optional.of(MOANA_DTO);

        // When
        Optional<MovieDto> actual = underTest.getMovieByName("Moana");

        // Then
        assertEquals(expected, actual);
        verify(movieRepository).findByName("Moana");
    }

    @Test
    void testGetMovieByNameShouldReturnOptionalEmptyWhenInputMovieNameDoesNotExist() {
        // Given
        when(movieRepository.findByName("dummy")).thenReturn(Optional.empty());
        Optional<MovieDto> expected = Optional.empty();

        // When
        Optional<MovieDto> actual = underTest.getMovieByName("dummy");

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
        verify(movieRepository).findByName("dummy");
    }

    @Test
    void testGetMovieByNameShouldReturnOptionalEmptyWhenInputMovieNameIsNull() {
        // Given
        when(movieRepository.findByName(null)).thenReturn(Optional.empty());
        Optional<MovieDto> expected = Optional.empty();

        // When
        Optional<MovieDto> actual = underTest.getMovieByName(null);

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
        verify(movieRepository).findByName(null);
    }

    @Test
    void testCreateMovieShouldCallMovieRepositoryWhenTheInputMovieIsValid() {
        // Given
        when(movieRepository.save(MOANA_ENTITY)).thenReturn(MOANA_ENTITY);

        // When
        underTest.createMovie(MOANA_DTO);

        // Then
        verify(movieRepository).save(MOANA_ENTITY);
    }

    @Test
    void testCreateMovieShouldThrowNullPointerExceptionWhenMovieIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.createMovie(null));
    }

    @Test
    void testCreateMovieShouldThrowNullPointerExceptionWhenMovieNameIsNull() {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .withName(null)
                .withGenre("animation")
                .withLength(230)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.createMovie(movieDto));
    }

    @Test
    void testCreateMovieShouldThrowNullPointerExceptionWhenMovieGenreIsNull() {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .withName("Moana")
                .withGenre(null)
                .withLength(230)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.createMovie(movieDto));
    }


    @Test
    void testUpdateMovieShouldCallMovieRepositoryWhenTheInputMovieIsValid() {
        // Given
        when(movieRepository.save(MOANA_ENTITY)).thenReturn(MOANA_ENTITY);

        MovieDto UPDATED_MOANA_DTO = MovieDto.builder()
                .withName("Moana")
                .withGenre("animation")
                .withLength(190)
                .build();

        // When
        underTest.updateMovie(UPDATED_MOANA_DTO);

        // Then
        verify(movieRepository).updateMovie("Moana", "animation", 190);
    }

    @Test
    void testUpdateMovieShouldThrowNullPointerExceptionWhenMovieIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateMovie(null));
    }

    @Test
    void testUpdateMovieShouldThrowNullPointerExceptionWhenMovieNameIsNull() {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .withName(null)
                .withGenre("animation")
                .withLength(150)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateMovie(movieDto));
    }

    @Test
    void testUpdateMovieShouldThrowNullPointerExceptionWhenMovieGenreIsNull() {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .withName("Moana")
                .withGenre(null)
                .withLength(150)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateMovie(movieDto));
    }

    @Test
    void testDeleteMovieShouldCallMovieRepositoryWhenTheInputMovieIsValid() {
        // Given
        when(movieRepository.save(MOANA_ENTITY)).thenReturn(MOANA_ENTITY);

        // When
        underTest.deleteMovie("Moana");

        // Then
        verify(movieRepository).deleteByName("Moana");
    }

    @Test
    void testDeleteMovieShouldThrowNullPointerExceptionWhenMovieNameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteMovie(null));
    }
    @Test
    void testGetMovieIdShouldReturnInteger() {
        // Given
        when(movieRepository.findByName("Moana")).thenReturn(Optional.of(MOANA_ENTITY));

        Integer expected = MOANA_ENTITY.getId();

        // When
        Integer actual = underTest.getMovieID("Moana");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetMovieIdShouldThrowNullPointerExceptionWhenMovieNonexistent() {
        // Given
        when(movieRepository.findByName("The Exorcist")).thenReturn(Optional.empty());

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.getMovieID("The Exorcist"));
    }
}