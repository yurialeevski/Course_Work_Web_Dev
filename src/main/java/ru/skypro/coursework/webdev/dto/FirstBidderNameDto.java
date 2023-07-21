package ru.skypro.coursework.webdev.dto;

import java.time.LocalDateTime;

public class FirstBidderNameDto {
    String name;
    LocalDateTime localDateTime;

    public FirstBidderNameDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "FirstBidderNameDto{" +
                "name='" + name + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
