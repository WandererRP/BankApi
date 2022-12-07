package com.roland.solva.repositories;

import com.roland.solva.models.MonthLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface MonthLimitRepository extends JpaRepository<MonthLimit, Long> {
    MonthLimit findTopByAccountIdAndDateTimeBeforeOrderByDateTimeDesc(long accountId, ZonedDateTime dateTime);


    List<MonthLimit> findByAccount_Id(long accountId);

}