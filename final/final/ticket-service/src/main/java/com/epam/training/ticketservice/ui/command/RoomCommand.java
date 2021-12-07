package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import java.util.Optional;

@ShellComponent
public class RoomCommand {

    private final RoomService roomService;
    private final UserService userService;

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describeAccount();
        if (user.isPresent() && user.get().getRole() == User.Role.ADMIN) {
            return Availability.available();
        }
        return Availability.unavailable("You are not an admin!");
    }

    public RoomCommand(RoomService roomService, UserService userService) {
        this.roomService = roomService;
        this.userService = userService;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Create a room.")
    public RoomDto createRoom(String name, Integer rows, Integer columns) {
        RoomDto room =  RoomDto.builder()
                .withName(name)
                .withRows(rows)
                .withColumns(columns)
                .build();
        roomService.createRoom(room);
        return room;
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Updates an already existing room.")
    public void updateRoom(String name, Integer rows, Integer columns) {
        RoomDto roomDto = new RoomDto(
                name,
                rows,
                columns);
        if (roomService.getRoomByName(roomDto.getName()).isEmpty()) {
            System.out.println("Room does not exist");
        } else {
            roomService.updateRoom(roomDto);
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Deletes a room.")
    public void deleteRoom(String name) {
        if (roomService.getRoomByName(name).isEmpty()) {
            System.out.println("Room does not exist");
        } else {
            roomService.deleteRoom(name);
        }
    }

    @ShellMethod(key = "list rooms", value = "List all available rooms.")
    public void listRooms() {
        if (roomService.getRoomList().isEmpty()) {
            System.out.println("There are no rooms at the moment");
        } else {
            roomService.getRoomList()
                    .forEach((m) ->
                            System.out.println("Room "
                                    + m.getName() + " with "
                                    + m.getColumns() * m.getRows() + " seats, "
                                    + m.getRows() + " rows and "
                                    + m.getColumns() + " columns"
                            ));
        }
    }
}
