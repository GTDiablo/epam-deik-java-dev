package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import com.epam.training.ticketservice.core.room.persistance.repository.RoomRepository;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDatabaseInitializerTest {

    private InMemoryDatabaseInitializer underTest;

    private UserRepository userRepository;

    private RoomRepository roomRepository;

    private MovieRepository movieRepository;

    @BeforeEach
    public void init() {
        movieRepository = Mockito.mock(MovieRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new InMemoryDatabaseInitializer( movieRepository, userRepository, roomRepository);
    }

    @Test
    public void testInitShouldCallUserRepository(){
        // Given

        // When

        underTest.init();

        // Then
        Mockito.verify(userRepository).save( new User("admin", "admin", User.Role.ADMIN));
        Mockito.verifyNoMoreInteractions(userRepository);


    }

}