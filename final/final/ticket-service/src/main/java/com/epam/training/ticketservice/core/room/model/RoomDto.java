package com.epam.training.ticketservice.core.room.model;

import java.util.Objects;

public class RoomDto {
    private final String name;
    private final Integer rows;
    private final Integer columns;

    public RoomDto(String name, Integer rows, Integer columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getColumns() {
        return columns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rows, columns);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RoomDto roomDto = (RoomDto) obj;
        return rows == roomDto.rows
                && columns == roomDto.columns && Objects.equals(name, roomDto.name);
    }

    public static class Builder {
        private String name;
        private Integer rows;
        private Integer columns;

        public RoomDto.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public RoomDto.Builder withRows(Integer rows) {
            this.rows = rows;
            return this;
        }

        public RoomDto.Builder withColumns(Integer columns) {
            this.columns = columns;
            return this;
        }

        public RoomDto build() {
            return new RoomDto(name, rows, columns);
        }
    }
}

