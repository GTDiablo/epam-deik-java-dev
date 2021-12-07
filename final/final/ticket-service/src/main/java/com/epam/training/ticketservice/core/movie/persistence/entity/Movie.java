package com.epam.training.ticketservice.core.movie.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.Objects;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String genre;
    private Integer length;

    public Movie() {
    }

    public Movie(String name, String genre, Integer length) {
        this.name = name;
        this.genre = genre;
        this.length = length;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return Integer.compare(movie.length, length) == 0
                && Objects.equals(id, movie.id)
                && Objects.equals(name, movie.name)
                && Objects.equals(genre, movie.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, length);
    }

    @Override
    public String toString() {
        return name + "(" + genre + "," + length + " minutes)";
    }
}