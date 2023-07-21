package ru.skypro.coursework.webdev.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.coursework.webdev.dto.*;
import ru.skypro.coursework.webdev.enums.LotStatus;
import ru.skypro.coursework.webdev.model.Bid;
import ru.skypro.coursework.webdev.service.BidService;
import ru.skypro.coursework.webdev.service.LotService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/lot")
public class LotController {
    private final LotService lotService;
    private final BidService bidService;

    public LotController(LotService lotService, BidService bidService) {
        this.lotService = lotService;
        this.bidService = bidService;
    }

    @PostMapping("/")
    public LotDto createLot(@RequestBody LotDtoNew lotDtoNew) {
        return lotService.createLot(lotDtoNew);
    }

    @GetMapping("/{id}")
    public FullLotInfoDto getFullInfoAboutLotById(@PathVariable Long id) {
        return lotService.getFullInfoById(id);
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<String> turnLotStatusToStarted(@PathVariable Long id) {
        return lotService.startLotTrade(id);
    }

    @PostMapping("/{id}/stop")
    public ResponseEntity<String> turnLotStatusToStopped(@PathVariable Long id) {
        return lotService.stopLotTrade(id);
    }

    @PostMapping("/{id}/bid")
    public HttpStatus placeBidOnLot(@PathVariable Long id,
                                             @RequestBody String bidderName) {
        return bidService.createBid(id, bidderName);
    }
    @GetMapping("/{id}/first")
    public FirstBidderNameDto getFirstBidderToLotId(@PathVariable Long id) {
        return lotService.getFirstBidderToLot(id);
    }
    @GetMapping("{id}/frequent")
    public FrequentNameDto getFrequentBidderNameTradingLotId(@PathVariable Long id) {
        return lotService.getFrequentBidderName(id);
    }
    @GetMapping("/")
    public List<LotDto> getAllEmployeesByPageNumber(
            @RequestParam LotStatus lotStatus,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<LotDto> lotDtoList = lotService.getAllByPageNumberByStatus(lotStatus, pageNo, pageSize, sortBy);

        return lotDtoList;
    }
    @GetMapping("/export")
    public void exportAllLots(HttpServletResponse response) throws IOException{
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"lots.csv\"");
        lotService.exportLots(response);
    }

}
