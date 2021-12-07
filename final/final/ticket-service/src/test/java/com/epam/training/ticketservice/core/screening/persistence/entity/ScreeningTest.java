package com.epam.training.ticketservice.core.screening.persistence.entity;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.expression.ParseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


class ScreeningTest {
    private String movie;
    private String room;

    @BeforeEach
    public void setUp() throws ParseException {
        movie = "Spirited Away";
        room = "asd";
    }

    @Test
    public void testConstructor() {
        Screening underTest = new Screening(movie,room,LocalDateTime.of(2020, Month.JANUARY, 18,21,00));
        assertNotNull(underTest);

    }

    @Test
    void getMovieShouldReturnTheScreeningMovieName() {
        Screening underTest = new Screening(movie,room,LocalDateTime.of(2020, Month.JANUARY, 18,21,00));

        assertEquals("Spirited Away",underTest.getMovie());



    }

    @Test
    void getRoomShouldReturnTheScreeningRoomName() {
        Screening underTest = new Screening(movie,room,LocalDateTime.of(2020, Month.JANUARY, 18,21,00));

        assertEquals("asd", underTest.getRoom());
    }

    @Test
    void getDateShouldReturnTheScreeningDate() {
        Screening underTest = new Screening(movie,room,LocalDateTime.of(2020, Month.JANUARY, 18,21,00));

        assertEquals(LocalDateTime.of(2020, Month.JANUARY, 18,21,00), underTest.getDate());
    }
}