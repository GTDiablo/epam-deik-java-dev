package com.epam.training.ticketservice.core.user.persistence.entity;

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
    void testGetUserNameShouldReturnName(){
        //Given

        String expected = name;

        //When
        String actual = underTest.getUsername();

        //Then
        Assertions.assertEquals(expected, actual);
    }
}