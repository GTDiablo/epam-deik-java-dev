package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.exception.OccupiedRoomException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningBreakException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class ScreeningCommand {

    private final UserService userService;
    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final RoomService roomService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    private Availability isAvailable() {
        Optional<UserDto> user = userService.describeAccount();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }

    public ScreeningCommand(UserService userService,
                            ScreeningService screeningService,
                            MovieService movieService,
                            RoomService roomService) {
        this.userService = userService;
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    //create, delete, list,

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Create a new screening")
    public String createScreening(String title, String roomName, String screeningStartDateString)
            throws OccupiedRoomException, ScreeningBreakException {
        LocalDateTime screeningStartDate = LocalDateTime.parse(screeningStartDateString, formatter);
        try {
            Optional<MovieDto> movieDto = movieService.getMovieByName(title);
            Optional<RoomDto> roomDto = roomService.getRoomByName(roomName);
            ScreeningDto screeningDto = ScreeningDto.builder()
                    .withMovie(movieDto.get())
                    .withRoom(roomDto.get())
                    .withDate(screeningStartDate)
                    .build();
            screeningService.createScreening(screeningDto);
            return screeningDto + " is added to database.";
        } catch (ScreeningBreakException | OccupiedRoomException e) {
            return e.getMessage();
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Delete az existing screening.")
    public String deleteScreening(String movie, String room, String dateString) {
        LocalDateTime date = LocalDateTime.parse(dateString, formatter);

        screeningService.deleteScreening(movie, room, date);

        if (movieService.getMovieByName(movie).isEmpty() || roomService.getRoomByName(room).isEmpty()) {
            return "Screening does not exist";
        } else {
            screeningService.deleteScreening(movie, room, date);
            return movie + " deleted successfully.";
        }
    }

    @ShellMethod(key = "list screenings", value = "List the screenings")
    public void listScreenings() {
        List<ScreeningDto> screeningList = screeningService.getScreeningList();
        if (screeningList.isEmpty()) {
            System.out.println("There are no screenings");
        } else {
            screeningList.forEach(System.out::println);
        }
    }
}
