package ru.skypro.coursework.webdev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.coursework.webdev.enums.LotStatus;
import ru.skypro.coursework.webdev.model.Lot;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    @Query(value = "select l from Lot l where l.title = :title")
    Page<Lot> getLotsByTitleByPageNumber(@Param("title") String title, Pageable pageable);

    @Query(value = "select l from Lot l where l.status = :status")
    //@Query(value = "SELECT * FROM lot WHERE lot.status = ?1", nativeQuery = true)
    Page<Lot> getLotsByStatusByPageNumber(@Param("status") LotStatus lotStatus, Pageable pageable);
}
