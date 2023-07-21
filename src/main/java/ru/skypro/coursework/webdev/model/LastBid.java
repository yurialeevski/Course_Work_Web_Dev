package ru.skypro.coursework.webdev.model;

import java.time.LocalDateTime;

public class LastBid {
    private String bidderName;
    private LocalDateTime bidDate;

    public LastBid() {
    }

    public String getBidderName() {
        return bidderName;
    }

    public void setBidderName(String bidderName) {
        this.bidderName = bidderName;
    }

    public LocalDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDateTime bidDate) {
        this.bidDate = bidDate;
    }

    @Override
    public String toString() {
        return "LastBid{" +
                "bidderName='" + bidderName + '\'' +
                ", bidDate=" + bidDate +
                '}';
    }
}
