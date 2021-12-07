package com.epam.training.ticketservice.core.room.persistance.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

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