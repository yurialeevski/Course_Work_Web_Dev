package ru.skypro.coursework.webdev.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "bid_new")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bidderName;
    private LocalDateTime bidDate;
    //private Long lotId;
    @ManyToOne(fetch = FetchType.EAGER)
    private Lot lot;

    public Bid() {
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
/*
    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }
*/

        public Lot getLot() {
            return lot;
        }

        public void setLot(Lot lot) {
            this.lot = lot;
        }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", bidderName='" + bidderName + '\'' +
                ", bidDate=" + bidDate +
                //", lotId=" + lotId +
                ", lot=" + lot +
                '}';
    }
}
