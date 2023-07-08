package ru.skypro.coursework.webdev.dto;

import java.time.LocalDateTime;

public class FrequentNameDto {
    String dtoBidderName;

    LocalDateTime dtoBidDate;

    public FrequentNameDto() {
    }

    public String getDtoBidderName() {
        return dtoBidderName;
    }

    public void setDtoBidderName(String dtoBidderName) {
        this.dtoBidderName = dtoBidderName;
    }

    public LocalDateTime getDtoBidDate() {
        return dtoBidDate;
    }

    public void setDtoBidDate(LocalDateTime dtoBidDate) {
        this.dtoBidDate = dtoBidDate;
    }

    @Override
    public String toString() {
        return "FrequentNameDto{" +
                "dtoBidderName='" + dtoBidderName + '\'' +
                ", dtoBidDate=" + dtoBidDate +
                '}';
    }
}
