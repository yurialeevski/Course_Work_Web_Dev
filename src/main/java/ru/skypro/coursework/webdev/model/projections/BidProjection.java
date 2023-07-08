package ru.skypro.coursework.webdev.model.projections;

import java.time.LocalDateTime;

public interface BidProjection {
    String getBidderName();
    LocalDateTime getBidDate();
}
