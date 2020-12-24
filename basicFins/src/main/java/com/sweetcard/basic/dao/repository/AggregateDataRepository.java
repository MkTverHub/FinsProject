package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggregateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface AggregateDataRepository extends JpaRepository<AggregateData, Integer> {
    @Query(value = "select cnt.id, cnt.name, fd.amount from contragent cnt left join financedata fd on cnt.id = int4(fd.finscontragent)", nativeQuery = true)
    List<AggregateData> GetAll();
}
