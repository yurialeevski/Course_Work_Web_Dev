package ru.skypro.coursework.webdev.service;

import jakarta.servlet.http.HttpServletResponse;
import jdk.swing.interop.SwingInterOpUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.skypro.coursework.webdev.dto.*;
import ru.skypro.coursework.webdev.enums.LotStatus;
import ru.skypro.coursework.webdev.enums.Response;
import ru.skypro.coursework.webdev.exceptions.AppError;
import ru.skypro.coursework.webdev.model.Bid;
import ru.skypro.coursework.webdev.model.Lot;
import ru.skypro.coursework.webdev.model.projections.BidProjection;
import ru.skypro.coursework.webdev.repository.BidRepository;
import ru.skypro.coursework.webdev.repository.LotRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LotService {
    private final DtoService dtoService;
    private final LotRepository lotRepository;
    private final BidRepository bidRepository;
    private static final Logger logger = LoggerFactory.getLogger(DtoService.class);

    public LotService(DtoService dtoService, LotRepository lotRepository, BidRepository bidRepository) {
        this.dtoService = dtoService;
        this.lotRepository = lotRepository;
        this.bidRepository = bidRepository;

    }

    public LotDto createLot(LotDtoNew lotDtoNew) {
        logger.info("New Lot is being created");
        Boolean lotDtoNewValidation = dtoService.validateLotDtoNewFields(lotDtoNew);
        if (!lotDtoNewValidation) {
            throw new AppError("Лот передан с ошибкой", HttpStatus.BAD_REQUEST);
        } else {
            Lot lot = dtoService.fromLotDtoNewToLot(lotDtoNew);
            Lot newSavedLot = lotRepository.save(lot);
            logger.info("New Lot successfully created: " + newSavedLot);
            LotDto lotDto = dtoService.fromLotToLotDto(newSavedLot);
            return lotDto;
        }
    }

    public FullLotInfoDto getFullInfoById(Long id) {
        logger.info("Getting full information about Lot by id: " + id);
        Lot lot = getLotById(id);
        if(lot == null) {
            logger.error("Lot Not_Found");
            throw new AppError("Lot with id: " + id + " not found", HttpStatus.NOT_FOUND);
        } else {
            Long countBids = bidRepository.countBidsByLot_Id(id);
            Long currentPrice = lot.getBidPrice() * countBids + lot.getStartPrice();
            BidProjectionDto lastBidDto = findLastBidderDto(id);
            FullLotInfoDto fullLotInfoDto = dtoService.fromLotInputDataToLotDto(lot, currentPrice, lastBidDto);
            return fullLotInfoDto;
        }
    }

    public Lot getLotById(Long id) {
        logger.info("Getting Lot by id: " + id);
        Optional<Lot> lot = lotRepository.findById(id);
        logger.debug("Value of Lot Optional - " + lot);
        return lot.orElse(null);
    }

    public ResponseEntity<String> startLotTrade(Long id) {
        logger.info("Changing Status of Lot with id: " + id + " from CREATED to STARTED");
        Lot lot = getLotById(id);
        if(lot == null) {
            logger.error("Lot Not_Found");
            throw new AppError("Lot with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        switch (lot.getStatus()) {
            case STARTED:
                return new ResponseEntity<>(Response.STARTED.toString(), HttpStatus.OK);
            case STOPPED:
                return new ResponseEntity<>(Response.ERROR_404.toString(), HttpStatus.BAD_REQUEST);
            case CREATED: {
                lot.setStatus(LotStatus.STARTED);
                Lot updatedLot = lotRepository.save(lot);
                logger.info("Status of Lot " + lot.getId() + "  " + lot.getDescription() +
                        " changed to STARTED");
                logger.info(updatedLot.toString());
                return new ResponseEntity<>(Response.STARTED.toString(), HttpStatus.OK);
            }
            default:
                logger.error("Lot Not_Found");
                throw new AppError("Field Status of Lot with id: " + id + " has wrong value" +
                        lot.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> stopLotTrade(Long id) {
        logger.info("Changing Status of Lot with id: " + id + " from STARTED to STOPPED");
        Lot lot = getLotById(id);
        if (lot == null) {
            logger.error("Lot Not_Found");
            throw new AppError("Lot with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        switch (lot.getStatus()) {
            case CREATED:
                return new ResponseEntity<>(Response.ERROR_404.toString(), HttpStatus.BAD_REQUEST);
            case STARTED: {
                lot.setStatus(LotStatus.STOPPED);
                Lot updatedLot = lotRepository.save(lot);
                logger.info("Status of Lot " + lot.getId() + "  " + lot.getDescription() +
                        " changed to STOPPED");
                logger.info(updatedLot.toString());
                return new ResponseEntity<>(Response.STOPPED.toString(), HttpStatus.OK);
            }
            case STOPPED:
                return new ResponseEntity<>(Response.STOPPED.toString(), HttpStatus.OK);
            default:
                logger.error("Lot Not_Found");
                throw new AppError("Field Status of Lot with id: " + id + " has wrong value" +
                                            lot.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public FirstBidderNameDto getFirstBidderToLot(Long id){
        logger.info("Getting information about First Bidder trading Lot with id: " + id);
        Lot lot = getLotById(id);
        if (lot == null) {
            logger.error("Lot Not_Found");
            throw new AppError("Lot with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        if(lot.getStatus().equals(LotStatus.CREATED)){
            throw new AppError("Lot is in status CREATED ", HttpStatus.BAD_REQUEST);
        }
        //List<Bid> bidList = bidRepository.findByIdOrderById(id);
        List<Bid> bidList = bidRepository.searchBidsByLot_IdOrderByBidDate(id);
        //System.out.println(bidList + "####" + id);
        Bid convertBid = bidList.get(0);
        FirstBidderNameDto firstBidderNameDto = dtoService.fromBidToFirstBidderNameDto(convertBid);
        return firstBidderNameDto;
    }
    public FrequentNameDto getFrequentBidderName(Long id){
        logger.info("Getting Name of the Bidder trading the biggest number of Lots with id: " + id);
        Lot lot = getLotById(id);
        if (lot == null) {
            logger.error("Lot Not_Found");
            throw new AppError("Lot with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        //List<String> frequentName = bidRepository.findFrequentName(id);
        List<Bid> bidList = bidRepository.getFrequentName(id);
        Bid bidFrequentName = bidList.get(0);
        FrequentNameDto frequentNameDto = dtoService.fromFrequentNameToFrequentNameDto(bidFrequentName);
        return frequentNameDto;
    }
    public List<LotDto> getAllByPageNumberByStatus(LotStatus lotStatus, int pageIndex, int unitPerPage, String sortBy) {
        logger.info("Getting all by Lot Status by specific page: " + pageIndex);
        Pageable paging = PageRequest.of(pageIndex, unitPerPage, Sort.by(sortBy));
        Page<Lot> pagedResult = lotRepository.getLotsByStatusByPageNumber(lotStatus, paging);
        if(pagedResult.hasContent()) {
            List<LotDto> lotDtoList = pagedResult.getContent().stream()
                    .map(dtoService::fromLotToLotDto)
                    .collect(Collectors.toList());
            //System.out.println("$$$$$$$$$$$" + lotDtoList);
            return lotDtoList;
        } else {
            return new ArrayList<LotDto>();
        }
    }
    public BidProjectionDto findLastBidderDto(Long id) {
        logger.info("Find information about last Bidder on Lot");
        List<BidProjection> bidProjection = bidRepository.getLastBidProjectionToLotId(id);

        if(bidProjection == null || bidProjection.isEmpty()) {
            BidProjectionDto emptyBidProjection = new BidProjectionDto();
            emptyBidProjection.setBidderName("No one Bid for Lot");
            emptyBidProjection.setBidDate(LocalDateTime.now().withNano(0));
            return emptyBidProjection;
        } else {
            BidProjection bid = bidProjection.get(0);
            BidProjectionDto projectionDto = new BidProjectionDto();
            projectionDto.setBidderName(bid.getBidderName());
            projectionDto.setBidDate(bid.getBidDate());
            return projectionDto;
        }
    }
    public void exportLots(HttpServletResponse response) throws IOException {
        logger.info("Export Lots to .csv fail");
        List<LotExportDto> fullLotInfoDtoList = getAllLotsForExport();
        StringWriter writer = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        for (LotExportDto lot : fullLotInfoDtoList) {
            csvPrinter.printRecord(
                    lot.getId(),
                    lot.getTitle(),
                    lot.getStatus(),
                    lot.getCurrentPrice(),
                    lot.getLastBidName() != null ? lot.getLastBidName(): "",
                    lot.getLastBidDatTime());
        }
        csvPrinter.flush();

        PrintWriter pWriter = response.getWriter();
        pWriter.write(writer.toString());
        pWriter.flush();
        pWriter.close();

        System.out.println(fullLotInfoDtoList);
    }

    public List<LotExportDto> getAllLotsForExport() {
        logger.info("Getting all Lots for export to file");
        // Получаю все лоты из БД
        List<Lot> lotList = lotRepository.findAll();
        //Формирую LotExportDto для каждого Лота
        List<LotExportDto> lotExportDtoList = new ArrayList<>();
        for(int i=0; i<lotList.size(); i++) {
            Lot lot = lotList.get(i);
            //Получить текущую цену лота по формуле из БД
            Long countBids = bidRepository.countBidsByLot_Id(lot.getId());
            Long currentPrice = lot.getBidPrice() * countBids + lot.getStartPrice();
            //Получить lastBid для каждого Лота и взять из него инф. для ДТО
            BidProjectionDto lastBidDto = findLastBidderDto(lot.getId());

            //Передать исходные данные в ДТО
            lotExportDtoList.add(dtoService.fromSourceToLotExportDto(lot, currentPrice, lastBidDto));
        }
        return lotExportDtoList;
    }
}
