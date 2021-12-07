package com.epam.training.ticketservice.core.room.persistance.repository;

import com.epam.training.ticketservice.core.room.persistance.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByName(String name);

    void deleteByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE Room r "
            + "SET r.rows = :newRows, r.columns = :newColumns "
            + "WHERE r.name = :name")
    void updateRoom(@Param("name") String name,
                    @Param("newRows") int newRows,
                    @Param("newColumns") int newColumns);
}
