package com.epam.training.ticketservice.core.user.persistence.entity;

import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User underTest;
    private final String name = "name";
    private final String pass = "pass";

    @BeforeEach
    public void init() {
        underTest = new User(name,pass, User.Role.ADMIN);
    }


    @Test
    public void testConstructor() {
        User underTest = new User("user", "user", User.Role.USER);
        assertNotNull(underTest);
    }

    @Test
    void testGetUserNameShouldReturnName(){
        //Given

        String expected = name;

        //When
        String actual = underTest.getUsername();

        //Then
        Assertions.assertEquals(expected, actual);
    }
}