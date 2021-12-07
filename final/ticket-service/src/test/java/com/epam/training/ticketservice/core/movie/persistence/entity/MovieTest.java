package com.epam.training.ticketservice.core.movie.persistence.entity;

import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    private Movie underTest;
    private final String name = "name";
    private final String drama = "drama";
    private final int length = 100;

    @BeforeEach
    public void init() {
        underTest = new Movie(name,drama, length);
    }


    @Test
    public void testConstructor() {
        Movie underTest = new Movie("Sátántangó", "drama", 450);
        assertNotNull(underTest);
    }

    @Test
    public void testSetNameShouldChangeName(){
        //Given

        String expected = "Incredibles";

        //When
        underTest.setName("Incredibles");
        String actual = underTest.getName();

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getNameShouldReturnTheNameOfTheMovie() {
        Movie undertest = new Movie ("Spirited Away", "animation", 125);

        assertEquals("Spirited Away", undertest.getName());
    }

    @Test
    void getGenreShouldReturnTheGenreOfTheMovie() {
        Movie undertest = new Movie ("Spirited Away", "animation", 125);

        assertEquals("animation", undertest.getGenre());
    }

    @Test
    public void testToStringShouldReturn() {
        //Given

        String expected = name + "(" + drama + "," + length + " minutes)";

        //When
        String actual = underTest.toString();

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getLengthShouldReturnTheLenghtOfTheMovie() {
        Movie undertest = new Movie ("Spirited Away", "animation", 125);

        assertEquals(125, undertest.getLength());
    }
}