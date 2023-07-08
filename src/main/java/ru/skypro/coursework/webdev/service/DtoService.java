package ru.skypro.coursework.webdev.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.coursework.webdev.dto.*;
import ru.skypro.coursework.webdev.enums.LotStatus;
import ru.skypro.coursework.webdev.model.Bid;
import ru.skypro.coursework.webdev.model.LastBid;
import ru.skypro.coursework.webdev.model.Lot;
import ru.skypro.coursework.webdev.model.projections.BidProjection;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DtoService {
    private static final Logger logger = LoggerFactory.getLogger(DtoService.class);
    public boolean validateLotDtoNewFields (LotDtoNew lotDtoNew) {
        logger.info("Run Validation of LotDtoNew fields");
        if(lotDtoNew.getTitle() == null || lotDtoNew.getTitle().isEmpty()) {
            logger.info("The field 'Title' is not correct");
            return false;
        } else if (lotDtoNew.getDescription() == null || lotDtoNew.getDescription().isEmpty()) {
            logger.info("The field 'Description' is not correct");
            return false;
        } else if (lotDtoNew.getStartPrice() == null || lotDtoNew.getBidPrice() == null) {
            logger.info("The field 'startPrice' is not correct");
            return false;
        }
        logger.info("Fields of LotDtoNew are correct" + lotDtoNew);
        return true;
    }
    public Lot fromLotDtoNewToLot(LotDtoNew lotDtoNew){
        Lot lot = new Lot();
        lot.setStatus(LotStatus.CREATED);
        lot.setTitle(lotDtoNew.getTitle());
        lot.setDescription(lotDtoNew.getDescription());
        lot.setStartPrice(lotDtoNew.getStartPrice());
        lot.setBidPrice(lotDtoNew.getBidPrice());
        return lot;
    }
    public LotDto fromLotToLotDto(Lot lot) {
        if(lot == null) {
            return null;
        }
        LotDto lotDto = new LotDto();
        lotDto.setId(lot.getId());
        lotDto.setStatus(lot.getStatus());
        lotDto.setTitle(lot.getTitle());
        lotDto.setDescription(lot.getDescription());
        lotDto.setStartPrice(lot.getStartPrice());
        lotDto.setBidPrice(lot.getBidPrice());
        return lotDto;
    }
    public Bid fromBidInputDataToBid(Long id, String bidderName, Lot lot){
        Bid bid = new Bid();
        bid.setBidderName(bidderName);
        bid.setBidDate(LocalDateTime.now().withNano(0));
        bid.setLot(lot);
        //bid.setLotId(id);
        return bid;
    }
    public FrequentNameDto fromFrequentNameToFrequentNameDto(Bid bidFrequentName) {
        FrequentNameDto frequentNameDto = new FrequentNameDto();
        String convertFrequentName = bidFrequentName.getBidderName().replace("\"", "");
        frequentNameDto.setDtoBidderName(convertFrequentName);
        frequentNameDto.setDtoBidDate(bidFrequentName.getBidDate());
        return frequentNameDto;
    }
    public FirstBidderNameDto fromBidToFirstBidderNameDto(Bid bid) {
        FirstBidderNameDto firstBidderNameDto = new FirstBidderNameDto();
        String reformatNameString = bid.getBidderName().replace("\"", "");
        firstBidderNameDto.setName(reformatNameString);
        firstBidderNameDto.setLocalDateTime(bid.getBidDate());
        return firstBidderNameDto;
    }

    public FullLotInfoDto fromLotInputDataToLotDto(Lot lot, Long currentPrice, BidProjectionDto lastBidDto) {
        FullLotInfoDto fullLotInfoDto = new FullLotInfoDto();
        fullLotInfoDto.setId(lot.getId());
        fullLotInfoDto.setStatus(lot.getStatus());
        fullLotInfoDto.setTitle(lot.getTitle());
        fullLotInfoDto.setDescription(lot.getDescription());
        fullLotInfoDto.setStartPrice(lot.getStartPrice());
        fullLotInfoDto.setBidPrice(lot.getBidPrice());
        fullLotInfoDto.setCurrentPrice(currentPrice);
        LastBid lastBid = new LastBid();
        lastBid.setBidderName(lastBidDto.getBidderName());
        lastBid.setBidDate(lastBidDto.getBidDate());
        fullLotInfoDto.setLastBid(lastBid);
        return fullLotInfoDto;
    }
    public LotExportDto fromSourceToLotExportDto(Lot lot, Long currentPrice, BidProjectionDto lastBidData) {
        LotExportDto lotExportDto = new LotExportDto();
        lotExportDto.setId(lot.getId());
        lotExportDto.setStatus(lot.getStatus());
        lotExportDto.setTitle(lot.getTitle());
        lotExportDto.setCurrentPrice(currentPrice);
        lotExportDto.setLastBidName(lastBidData.getBidderName());
        lotExportDto.setLastBidDatTime(lastBidData.getBidDate());
        return lotExportDto;
    }


    /*LotDto lotDto = new LotDto();
            lotDto.setId(1L); //id;
            lotDto.setStatus(LotStatus.CREATED); //status;
            lotDto.setTitle("First lot"); //title;
            lotDto.setDescription("Test Description"); //description;
            lotDto.setStartPrice(100); //startPrice;
            lotDto.setBidPrice(200); //bidPrice;
            System.out.println(lotDto);*/
}
