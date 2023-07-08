package ru.skypro.coursework.webdev.dto;

import java.time.LocalDateTime;

public class BidProjectionDto {
    private String bidderName;
    private LocalDateTime bidDate;

    public BidProjectionDto() {
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
        return "LastBidDto{" +
                "bidderName='" + bidderName + '\'' +
                ", bidDate=" + bidDate +
                '}';
    }
}
