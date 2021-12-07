package com.epam.training.ticketservice.core.movie.model;

import java.util.Objects;

public class MovieDto {
    private final String name;
    private final String genre;
    private final Integer length;

    public MovieDto(String name, String genre, Integer length) {
        this.name = name;
        this.genre = genre;
        this.length = length;

    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
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
        MovieDto movie = (MovieDto) o;
        return Objects.equals(name, movie.name)
                && Objects.equals(genre, movie.genre)
                && Objects.equals(length, movie.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genre, length);
    }

    @Override
    public String toString() {
        return name + "(" + genre + "," + length + " minutes)";
    }

    public static class Builder {
        private String name;
        private String genre;
        private Integer length;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder withLength(Integer length) {
            this.length = length;
            return this;
        }

        public MovieDto build() {
            return new MovieDto(name, genre, length);
        }
    }

}
