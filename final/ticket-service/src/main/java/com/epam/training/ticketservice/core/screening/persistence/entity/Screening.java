package com.epam.training.ticketservice.core.screening.persistence.entity;

import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.room.persistance.entity.Room;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Screening {

    @Id
    @GeneratedValue
    private Integer id;
    private String movie;
    private String room;
    private LocalDateTime date;

    public Screening(String movie, String room, LocalDateTime date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    public Screening() {
    }
    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Screening screening = (Screening) o;
        return Objects.equals(id, screening.id) && Objects.equals(movie, screening.movie)
                && Objects.equals(room, screening.room) && Objects.equals(date, screening.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, room, date);
    }

}

