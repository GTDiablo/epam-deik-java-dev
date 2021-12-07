package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import com.epam.training.ticketservice.core.room.persistance.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> getRoomList() {
        return roomRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<RoomDto> getRoomByName(String roomName) {
        return convertEntityToDto(roomRepository.findByName(roomName));
    }

    @Override
    public void createRoom(RoomDto roomDto) {
        Objects.requireNonNull(roomDto, "Room cannot be null.");
        Objects.requireNonNull(roomDto.getName(), "Room name cannot be null.");
        Objects.requireNonNull(roomDto.getRows(), "Room rows cannot be null.");
        Objects.requireNonNull(roomDto.getColumns(), "Room columns cannot be null.");
        Room room = new Room(roomDto.getName(), roomDto.getRows(), roomDto.getColumns());
        roomRepository.save(room);

    }

    @Override
    public void updateRoom(RoomDto roomDto) {
        Objects.requireNonNull(roomDto, "Room cannot be null");
        Objects.requireNonNull(roomDto.getName(), "Room name cannot be null");

        roomRepository.updateRoom(roomDto.getName(), roomDto.getRows(), roomDto.getColumns());
    }

    @Override
    public void deleteRoom(String roomName) {
        Objects.requireNonNull(roomName, "Room name cannot be null.");
        roomRepository.deleteByName(roomName);

    }

    private RoomDto convertEntityToDto(Room room) {
        return RoomDto.builder()
                .withName(room.getName())
                .withRows(room.getRows())
                .withColumns(room.getColumns())
                .build();
    }

    private Optional<RoomDto> convertEntityToDto(Optional<Room> room) {
        return room.isEmpty() ? Optional.empty() : Optional.of(convertEntityToDto(room.get()));
    }
}
