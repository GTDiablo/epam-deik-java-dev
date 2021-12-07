package com.epam.training.ticketservice.core.screening.impl;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exception.OccupiedRoomException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningBreakException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import com.epam.training.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScreeningServiceImplTest {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

    private static final RoomDto PEDERSOLI_DTO = RoomDto.builder()
            .withName("Pedersoli")
            .withRows(20)
            .withColumns(10)
            .build();

    private static final RoomDto LUMIERE_DTO = RoomDto.builder()
            .withName("Lumiere")
            .withRows(5)
            .withColumns(7)
            .build();

    private static final Screening SPIRITED_AWAY_SCREENING_ENTITY = new Screening("Spirited Away", "Pedersoli", LocalDateTime.parse("2021-12-06 18:00", formatter));
    private static final Screening MOANA_SCREENING_ENTITY = new Screening("Moana", "Lumiere", LocalDateTime.parse("2021-12-05 19:00", formatter));

    private final ScreeningDto SPIRITED_AWAY_SCREENING_DTO = ScreeningDto.builder()
            .withMovie(SPIRITED_AWAY_DTO)
            .withRoom(PEDERSOLI_DTO)
            .withDate(LocalDateTime.parse("2021-12-06 18:00", formatter))
            .build();

    private final ScreeningDto MOANA_SCREENING_DTO = ScreeningDto.builder()
            .withMovie(MOANA_DTO)
            .withRoom(LUMIERE_DTO)
            .withDate(LocalDateTime.parse("2021-12-05 19:00", formatter))
            .build();

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final MovieService movieService = mock(MovieService.class);
    private final RoomService roomService = mock(RoomService.class);
    private final ScreeningService underTest = new ScreeningServiceImpl(screeningRepository, movieService, roomService);


    @Test
    void testGetScreeningListShouldCallScreeningRepositoryAndReturnListofScreenings() {
        // Given
        when(movieService.getMovieByName("Spirited Away")).thenReturn(Optional.of(SPIRITED_AWAY_DTO));
        when(movieService.getMovieByName("Moana")).thenReturn(Optional.of(MOANA_DTO));
        when(roomService.getRoomByName("Pedersoli")).thenReturn(Optional.of(PEDERSOLI_DTO));
        when(roomService.getRoomByName("Lumiere")).thenReturn(Optional.of(LUMIERE_DTO));
        when(screeningRepository.findAll()).thenReturn(List.of(SPIRITED_AWAY_SCREENING_ENTITY, MOANA_SCREENING_ENTITY));
        List<ScreeningDto> expected = List.of(SPIRITED_AWAY_SCREENING_DTO, MOANA_SCREENING_DTO);

        // When
        List<ScreeningDto> actual = underTest.getScreeningList();

        // Then
        assertEquals(expected, actual);
        verify(screeningRepository).findAll();
    }

    @Test
    void testGetScreeningListShouldThrowNullPointerExceptionWhenAMovieDtoIsNonexistent() {
        // Given
        when(movieService.getMovieByName("Moana")).thenReturn(Optional.empty());
        when(roomService.getRoomByName("Lumiere")).thenReturn(Optional.of(LUMIERE_DTO));
        when(screeningRepository.findAll()).thenReturn(List.of(MOANA_SCREENING_ENTITY));

        // When - Then
        assertThrows(NullPointerException.class, underTest::getScreeningList);
    }

    @Test
    void testGetScreeningListShouldThrowNullPointerExceptionWhenARoomDtoIsNonexistent() {
        // Given
        when(movieService.getMovieByName("Moana")).thenReturn(Optional.of(MOANA_DTO));
        when(roomService.getRoomByName("Lumiere")).thenReturn(Optional.empty());
        when(screeningRepository.findAll()).thenReturn(List.of(MOANA_SCREENING_ENTITY));

        // When - Then
        assertThrows(NullPointerException.class, underTest::getScreeningList);
    }

    @Test
    void createScreeningShouldCallScreeningRepositoryWhenTheInputScreeningIsValid() throws ScreeningBreakException, OccupiedRoomException {
        // Given
        when(screeningRepository.save(MOANA_SCREENING_ENTITY)).thenReturn(MOANA_SCREENING_ENTITY);

        // When
        underTest.createScreening(MOANA_SCREENING_DTO);

        // Then
        verify(screeningRepository).save(MOANA_SCREENING_ENTITY);
    }

    @Test
    void createScreeningShouldThrowNullPointerExceptionWhenScreeningIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.createScreening(null));
    }

    @Test
    void createScreeningShouldThrowNullPointerExceptionWhenDateIsNull() {
        // Given
        ScreeningDto screeningDto = ScreeningDto.builder()
                .withMovie(MOANA_DTO)
                .withRoom(LUMIERE_DTO)
                .withDate(null)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.createScreening(screeningDto));
    }

    @Test
    void testCreateScreeningShouldThrowOccupiedRoomExceptionWhenAScreeningStartsAfterAnother() {
        // Given
        when(screeningRepository.findByRoom("Pedersoli")).thenReturn(List.of(SPIRITED_AWAY_SCREENING_ENTITY));
        when(movieService.getMovieByName("Spirited Away")).thenReturn(Optional.of(SPIRITED_AWAY_DTO));
        when(roomService.getRoomByName("Pedersoli")).thenReturn(Optional.of(PEDERSOLI_DTO));

        ScreeningDto screeningDto = ScreeningDto.builder()
                .withMovie(MOANA_DTO)
                .withRoom(PEDERSOLI_DTO)
                .withDate(LocalDateTime.parse("2021-12-06 19:05", formatter))
                .build();

        // When - Then
        assertThrows(OccupiedRoomException.class, () -> underTest.createScreening(screeningDto));
    }

    @Test
    void testCreateScreeningShouldThrowOccupiedRoomExceptionWhenAScreeningStartsExactlyAtTheEndOfAnother() {
        // Given
        when(screeningRepository.findByRoom("Pedersoli")).thenReturn(List.of(SPIRITED_AWAY_SCREENING_ENTITY));
        when(movieService.getMovieByName("Spirited Away")).thenReturn(Optional.of(SPIRITED_AWAY_DTO));
        when(roomService.getRoomByName("Pedersoli")).thenReturn(Optional.of(PEDERSOLI_DTO));

        ScreeningDto screeningDto = ScreeningDto.builder()
                .withMovie(MOANA_DTO)
                .withRoom(PEDERSOLI_DTO)
                .withDate(LocalDateTime.parse("2021-12-06 18:49", formatter))
                .build();

        // When - Then
        assertThrows(OccupiedRoomException.class, () -> underTest.createScreening(screeningDto));
    }


    @Test
    void testCreateScreeningShouldThrowScreeningBreakExceptionWhenAScreeningStartsInTheBreakOfAnother() {
        // Given
        when(screeningRepository.findByRoom("Pedersoli")).thenReturn(List.of(SPIRITED_AWAY_SCREENING_ENTITY));
        when(movieService.getMovieByName("Spirited Away")).thenReturn(Optional.of(SPIRITED_AWAY_DTO));
        when(roomService.getRoomByName("Pedersoli")).thenReturn(Optional.of(PEDERSOLI_DTO));

        ScreeningDto screeningDto = ScreeningDto.builder()
                .withMovie(MOANA_DTO)
                .withRoom(PEDERSOLI_DTO)
                .withDate(LocalDateTime.parse("2021-12-06 20:14", formatter))
                .build();

        // When - Then
        assertThrows(ScreeningBreakException.class, () -> underTest.createScreening(screeningDto));
    }

    @Test
    void testCreateScreeningShouldThrowScreeningBreakExceptionWhenAScreeningStartsExactlyAtTheEndOfABreak() throws ScreeningBreakException{
        // Given
        when(screeningRepository.findByRoom("Pedersoli")).thenReturn(List.of(SPIRITED_AWAY_SCREENING_ENTITY));
        when(movieService.getMovieByName("Spirited Away")).thenReturn(Optional.of(SPIRITED_AWAY_DTO));
        when(roomService.getRoomByName("Pedersoli")).thenReturn(Optional.of(PEDERSOLI_DTO));

        ScreeningDto screeningDto = ScreeningDto.builder()
                .withMovie(MOANA_DTO)
                .withRoom(PEDERSOLI_DTO)
                .withDate(LocalDateTime.parse("2021-12-06 20:15", formatter))
                .build();

        // When - Then
        assertThrows(ScreeningBreakException.class, () -> underTest.createScreening(screeningDto));
    }

    @Test
    void testDeleteScreeningShouldCallScreeningRepositoryWhenTheInputScreeningIsValid() {
        // Given
        when(screeningRepository.save(MOANA_SCREENING_ENTITY)).thenReturn(MOANA_SCREENING_ENTITY);

        // When
        underTest.deleteScreening("Moana", "Lumiere", LocalDateTime.parse("2021-12-05 19:00", formatter));

        // Then
        verify(screeningRepository).deleteByMovieAndRoomAndDate("Moana", "Lumiere", LocalDateTime.parse("2021-12-05 19:00", formatter));
    }

    @Test
    void testDeleteScreeningShouldThrowNullPointerExceptionWhenMovieIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteScreening(null, "Lumiere", LocalDateTime.parse("2021-11-26 14:00", formatter)));
    }

    @Test
    void testDeleteScreeningShouldThrowNullPointerExceptionWhenRoomIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteScreening("Moana", null, LocalDateTime.parse("2021-12-05 19:00", formatter)));
    }

    @Test
    void testDeleteScreeningShouldThrowNullPointerExceptionWhenScreeningStartDateIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteScreening("Moana", "Lumiere", null));
    }
}