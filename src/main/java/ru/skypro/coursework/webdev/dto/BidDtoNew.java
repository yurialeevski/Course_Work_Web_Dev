package ru.skypro.coursework.webdev.dto;

import java.time.LocalDateTime;

public class BidDtoNew {
    private Long id;
    private String bidderName;
    private LocalDateTime bidDate;

    public BidDtoNew() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "BidDtoNew{" +
                "id=" + id +
                ", bidderName='" + bidderName + '\'' +
                ", bidDate=" + bidDate +
                '}';
    }
}
