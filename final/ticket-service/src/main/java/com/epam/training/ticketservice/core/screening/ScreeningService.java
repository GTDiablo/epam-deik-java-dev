package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.screening.exception.OccupiedRoomException;
import com.epam.training.ticketservice.core.screening.exception.ScreeningBreakException;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.entity.Screening;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    void createScreening(ScreeningDto screening) throws ScreeningBreakException, OccupiedRoomException;

    void deleteScreening(String screeningMovie, String screeningRoom, LocalDateTime screeningDate);

    List<ScreeningDto> getScreeningList();

}
