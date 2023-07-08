package ru.skypro.coursework.webdev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class BidDto {
    @JsonIgnore
    private Long id;
    private String bidderName;
    @JsonIgnore
    private LocalDateTime bidDate;
    @JsonIgnore
    private Long lotId;

    public BidDto() {
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

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    @Override
    public String toString() {
        return "BidDto{" +
                "id=" + id +
                ", bidderName='" + bidderName + '\'' +
                ", bidDate=" + bidDate +
                ", lotId=" + lotId +
                '}';
    }
}
