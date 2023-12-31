package ru.skypro.coursework.webdev.model;

import jakarta.persistence.*;
import ru.skypro.coursework.webdev.enums.LotStatus;

import java.util.List;
@Entity
@Table(name = "lot_new")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private LotStatus status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
    //@OneToMany(mappedBy = "lot")
    //private List<Bid> bids;

    public Lot() {
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
/*
    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
*/
    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startPrice=" + startPrice +
                ", bidPrice=" + bidPrice +
                //", bids=" + bids +
                '}';
    }
}
