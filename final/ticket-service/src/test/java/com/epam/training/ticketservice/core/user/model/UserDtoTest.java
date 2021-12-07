package com.epam.training.ticketservice.core.user.model;

import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private UserDto underTest;
    private final String name = "name";

    @BeforeEach
    public void init() {
        underTest = new UserDto(name, User.Role.ADMIN);
    }

    @Test
    void testGetUsernameShouldReturnName() {
        //Given

        String expected = name;

        //When
        String actual = underTest.getUsername();

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testToStringShouldReturnGivenString() {
        //Given
        String expected = " '" + underTest.getUsername() + "'";

        //When
        String actual = underTest.toString();

        //Then
        Assertions.assertEquals(expected, actual);
    }
}