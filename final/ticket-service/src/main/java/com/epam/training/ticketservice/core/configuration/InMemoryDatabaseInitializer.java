package com.epam.training.ticketservice.core.configuration;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import com.epam.training.ticketservice.core.room.persistance.repository.RoomRepository;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Profile("!prod")
public class InMemoryDatabaseInitializer {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public InMemoryDatabaseInitializer(MovieRepository movieRepository,
                                       UserRepository userRepository,
                                       RoomRepository roomRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @PostConstruct
    public void init() {
        Movie spiritedAway = new Movie("Spirited Away", "animation", 125);
        Movie theExorcist = new Movie("The Exorcist", "horror", 202);
        Movie moana = new Movie("Moana", "animation", 106);
        movieRepository.saveAll(List.of(spiritedAway, theExorcist, moana));
        Room pedersoli = new Room("Pedersoli", 10, 10);
        Room lumiere = new Room("Lumiere", 5,7);
        roomRepository.saveAll(List.of(pedersoli, lumiere));
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);
    }
}