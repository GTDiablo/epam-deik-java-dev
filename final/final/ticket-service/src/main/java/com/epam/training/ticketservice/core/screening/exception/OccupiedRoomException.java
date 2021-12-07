package com.epam.training.ticketservice.core.screening.exception;

public class OccupiedRoomException extends RuntimeException {

    public OccupiedRoomException(String message) {
        super(message);
    }
}
