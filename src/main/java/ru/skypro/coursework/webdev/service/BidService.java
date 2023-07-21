package ru.skypro.coursework.webdev.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.coursework.webdev.dto.BidDtoNew;
import ru.skypro.coursework.webdev.dto.BidProjectionDto;
import ru.skypro.coursework.webdev.enums.LotStatus;
import ru.skypro.coursework.webdev.exceptions.AppError;
import ru.skypro.coursework.webdev.model.Bid;
import ru.skypro.coursework.webdev.model.Lot;
import ru.skypro.coursework.webdev.model.projections.BidProjection;
import ru.skypro.coursework.webdev.repository.BidRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidService {
    private final DtoService dtoService;
    private final LotService lotService;
    private final BidRepository bidRepository;
    private static final Logger logger = LoggerFactory.getLogger(DtoService.class);

    public BidService(DtoService dtoService, LotService lotService, BidRepository bidRepository) {
        this.dtoService = dtoService;
        this.lotService = lotService;
        this.bidRepository = bidRepository;
    }
    public HttpStatus createBid(Long id, String bidderName) {
        logger.info("New Bid is being created");
        if(bidderName == null || bidderName.isEmpty()) {
            logger.info("The field 'bidderName' is not correct");
            throw new AppError("Field 'bidderName' in Request Body has not been filled correctly", HttpStatus.BAD_REQUEST);
        }
        Lot lot = lotService.getLotById(id);
        if (lot == null) {
            logger.error("Lot Not_Found");
            throw new AppError("Lot with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        switch (lot.getStatus()) {
            case CREATED:
            case STOPPED:
                return HttpStatus.BAD_REQUEST;
            case STARTED: {
                Bid bid = dtoService.fromBidInputDataToBid(id, bidderName, lot);
                Bid newSavedBid = bidRepository.save(bid);
                logger.info("New Bid successfully created: " + newSavedBid);
                return HttpStatus.OK;
            }
            default:
                logger.error("Lot Not_Found");
                throw new AppError("Field Status of Lot with id: " + id + " has wrong value" +
                        lot.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
