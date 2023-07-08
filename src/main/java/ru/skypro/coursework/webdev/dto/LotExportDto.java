package ru.skypro.coursework.webdev.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.skypro.coursework.webdev.enums.LotStatus;
import ru.skypro.coursework.webdev.model.Bid;

import java.time.LocalDateTime;

public class LotExportDto {
    private Long id;
    private LotStatus status;
    private String title;
    private Long currentPrice;
    private String lastBidName;
    private LocalDateTime lastBidDatTime;

    public LotExportDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LotStatus getStatus() {
        return status;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getLastBidName() {
        return lastBidName;
    }

    public void setLastBidName(String lastBidName) {
        this.lastBidName = lastBidName;
    }

    public LocalDateTime getLastBidDatTime() {
        return lastBidDatTime;
    }

    public void setLastBidDatTime(LocalDateTime lastBidDatTime) {
        this.lastBidDatTime = lastBidDatTime;
    }

    @Override
    public String toString() {
        return "LotExportDto{" +
                "id=" + id +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", currentPrice=" + currentPrice +
                ", lastBidName='" + lastBidName + '\'' +
                ", lastBidDatTime=" + lastBidDatTime +
                '}';
    }
}

