package ru.skypro.coursework.webdev.dto;

import ru.skypro.coursework.webdev.enums.LotStatus;
import ru.skypro.coursework.webdev.model.LastBid;

public class FullLotInfoDto {
    private Long id;
    private LotStatus status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
    private Long currentPrice;
    private LastBid lastBid;

    public FullLotInfoDto() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public Integer getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Integer bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LastBid getLastBid() {
        return lastBid;
    }

    public void setLastBid(LastBid lastBid) {
        this.lastBid = lastBid;
    }

    @Override
    public String toString() {
        return "FullLotInfoDto{" +
                "id=" + id +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startPrice=" + startPrice +
                ", bidPrice=" + bidPrice +
                ", currentPrice=" + currentPrice +
                ", lastBid=" + lastBid +
                '}';
    }
}

