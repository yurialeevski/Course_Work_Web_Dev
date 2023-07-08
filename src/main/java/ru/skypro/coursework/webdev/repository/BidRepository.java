package ru.skypro.coursework.webdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.coursework.webdev.dto.FrequentNameDto;
import ru.skypro.coursework.webdev.model.Bid;
import ru.skypro.coursework.webdev.model.projections.BidProjection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> searchBidsByLot_IdOrderByBidDate(Long id);

    @Query(value = "select b.bidderName from Bid b where b.lot.id = :id group by b.bidderName order by count(b.bidderName) desc")
    List<String> findFrequentName(@Param("id") Long id);

    @Query(value = "select * from bid_new b where b.bidder_name=(select b.bidder_name from bid_new b where b.lot_id = :id\n" +
            "     group by b.bidder_name order by count(b.bidder_name) desc limit 1) group by b.id order by max(b.bid_date) desc", nativeQuery = true)
    List<Bid> getFrequentName(@Param("id") Long id);

    @Query(value = "select b.bidder_name from bid_new b where b.lot_id = 2 group by b.bidder_name order by count(b.bidder_name) desc;", nativeQuery = true)
    List<String> getInfo(Long id);



    //@Query(value = "SELECT COUNT(*) FROM bid WHERE lot_id = ?1", nativeQuery = true)
    //Long countBidsByLotId(Long id);
    Long countBidsByLot_Id(Long id);
    @Query(value = "select b.bidderName as bidderName, b.bidDate as bidDate from Bid b where b.lot.id = :id order by b.bidDate desc")
    List<BidProjection> getLastBidProjectionToLotId(@Param("id") Long id);

    //Bid findBidByLot_IdOrderByBidDateDesc(Long id);

    //@Query(value = "SELECT bidder_name AS bidderName, bid_date AS bidDate FROM bid WHERE lot_id = ?1 ORDER BY bid_date DESC LIMIT 1", nativeQuery = true)
    //BidderNameAndBidDate getInfoAboutLastBidDate(Long id);
}
