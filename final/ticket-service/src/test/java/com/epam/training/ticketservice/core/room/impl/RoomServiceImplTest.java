package com.epam.training.ticketservice.core.room.impl;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import com.epam.training.ticketservice.core.room.persistance.repository.RoomRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    private static final Room PEDERSOLI_ENTITY = new Room("Pedersoli", 10, 10);
    private static final Room LUMIERE_ENTITY = new Room("Lumiere", 5, 7);

    private static final RoomDto PEDERSOLI_DTO = RoomDto.builder()
            .withName("Pedersoli")
            .withRows(10)
            .withColumns(10)
            .build();

    private static final RoomDto LUMIERE_DTO = RoomDto.builder()
            .withName("Lumiere")
            .withRows(5)
            .withColumns(7)
            .build();

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomService underTest = new RoomServiceImpl(roomRepository);

    @Test
    void testGetRoomListShouldCallRoomRepositoryAndReturnListofMovies() {
        // Given
        when(roomRepository.findAll()).thenReturn(List.of(PEDERSOLI_ENTITY, LUMIERE_ENTITY));
        List<RoomDto> expected = List.of(PEDERSOLI_DTO, LUMIERE_DTO);

        // When
        List<RoomDto> actual = underTest.getRoomList();

        // Then
        assertEquals(expected, actual);
        verify(roomRepository).findAll();
    }

    @Test
    void testGetRoomByRoomNameShouldReturnAPedersoliDtoWhenTheRoomExists() {
        // Given
        when(roomRepository.findByName("Pedersoli")).thenReturn(Optional.of(PEDERSOLI_ENTITY));
        Optional<RoomDto> expected = Optional.of(PEDERSOLI_DTO);

        // When
        Optional<RoomDto> actual = underTest.getRoomByName("Pedersoli");

        // Then
        assertEquals(expected, actual);
        verify(roomRepository).findByName("Pedersoli");
    }

    @Test
    void testGetRoomByRoomNameShouldReturnOptionalEmptyWhenInputNameDoesNotExist() {
        // Given
        when(roomRepository.findByName("dummy")).thenReturn(Optional.empty());
        Optional<RoomDto> expected = Optional.empty();

        // When
        Optional<RoomDto> actual = underTest.getRoomByName("dummy");

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
        verify(roomRepository).findByName("dummy");
    }

    @Test
    void testGetRoomByNameShouldReturnOptionalEmptyWhenInputNameIsNull() {
        // Given
        when(roomRepository.findByName(null)).thenReturn(Optional.empty());
        Optional<RoomDto> expected = Optional.empty();

        // When
        Optional<RoomDto> actual = underTest.getRoomByName(null);

        // Then
        assertTrue(actual.isEmpty());
        assertEquals(expected, actual);
        verify(roomRepository).findByName(null);
    }

    @Test
    void testCreateRoomShouldCallRoomRepositoryWhenTheInputRoomIsValid() {
        // Given
        when(roomRepository.save(PEDERSOLI_ENTITY)).thenReturn(PEDERSOLI_ENTITY);

        // When
        underTest.createRoom(PEDERSOLI_DTO);

        // Then
        verify(roomRepository).save(PEDERSOLI_ENTITY);
    }

    @Test
    void testCreateRoomShouldThrowNullPointerExceptionWhenRoomIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.createRoom(null));
    }

    @Test
    void testCreateRoomShouldThrowNullPointerExceptionWhenNameIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .withName(null)
                .withRows(20)
                .withColumns(20)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.createRoom(roomDto));
    }

    @Test
    void testCreateRoomShouldThrowNullPointerExceptionWhenRowIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .withName("Pedersoli")
                .withRows(null)
                .withColumns(20)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.createRoom(roomDto));
    }

    @Test
    void testCreateRoomShouldThrowNullPointerExceptionWhenColumnIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .withName("Pedersoli")
                .withRows(20)
                .withColumns(null)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.createRoom(roomDto));
    }
  @Test
    void testUpdateRoomShouldCallRoomRepositoryWhenTheInputRoomIsValid() {
        // Given
        when(roomRepository.save(PEDERSOLI_ENTITY)).thenReturn(PEDERSOLI_ENTITY);

        RoomDto UPDATED_PEDERSOLI_DTO = RoomDto.builder()
                .withName("Pedersoli")
                .withRows(20)
                .withColumns(30)
                .build();

        // When
        underTest.updateRoom(UPDATED_PEDERSOLI_DTO);

        // Then
        verify(roomRepository).updateRoom("Pedersoli", 20, 30);
    }

    @Test
    void testUpdateRoomShouldThrowNullPointerExceptionWhenRoomIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateRoom(null));
    }

    @Test
    void testUpdateRoomShouldThrowNullPointerExceptionWhenNameIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .withName(null)
                .withRows(10)
                .withColumns(20)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateRoom(roomDto));
    }

    @Test
    void testUpdateRoomShouldThrowNullPointerExceptionWhenRowIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .withName("Pedersoli")
                .withRows(null)
                .withColumns(20)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateRoom(roomDto));
    }

    @Test
    void testUpdateRoomShouldThrowNullPointerExceptionWhenColumnIsNull() {
        // Given
        RoomDto roomDto = RoomDto.builder()
                .withName("Pedersoli")
                .withRows(20)
                .withColumns(null)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateRoom(roomDto));
    }

    @Test
    void testDeleteRoomShouldCallRoomRepositoryWhenTheInputRoomIsValid() {
        // Given
        when(roomRepository.save(PEDERSOLI_ENTITY)).thenReturn(PEDERSOLI_ENTITY);

        // When
        underTest.deleteRoom("Pedersoli");

        // Then
        verify(roomRepository).deleteByName("Pedersoli");
    }

    @Test
    void testDeleteRoomShouldThrowNullPointerExceptionWhenNameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteRoom(null));
    }

    @Test
    void testListRoomsShouldReturnRoomDtoList() {
        // Given
        when(roomRepository.findAll()).thenReturn(List.of(PEDERSOLI_ENTITY, LUMIERE_ENTITY));

        List<RoomDto> expected = List.of(PEDERSOLI_DTO, LUMIERE_DTO);

        // When
        List<RoomDto> actual = underTest.getRoomList();

        // Then
        assertEquals(expected, actual);
    }
}