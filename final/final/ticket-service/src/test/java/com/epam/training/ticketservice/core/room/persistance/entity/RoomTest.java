package com.epam.training.ticketservice.core.room.persistance.entity;

import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {


    @Test
    public void testConstructor() {
        Room underTest = new Room("asd", 30, 40);
        assertNotNull(underTest);
    }

    @Test
    void getNameShouldReturnTheNameOfTheRoom() {
        Room underTest = new Room("asd", 30, 45);

        assertEquals("asd", underTest.getName());
    }

    @Test
    void getRowsShouldReturnTheRowsOfTheRoom() {
        Room underTest = new Room("asd", 30, 45);

        assertEquals(30, underTest.getRows());
    }

    @Test
    void getColumnsShouldReturnTheColumnsOfTheRoom() {
        Room underTest = new Room("asd", 30, 45);

        assertEquals(45, underTest.getColumns());
    }
}