package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrContragent;
import com.sweetcard.basic.dao.entities.AggrReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataReport extends JpaRepository<AggrReport, Integer> {
    @Query(value =
            "select row_number() OVER() as id, to_char(fd.oper_date,'yyyy') rep_lable, sum(fd.amount) rep_value from financedata fd where fd.fins_oper_type = 'profit' and fd.project_id = :project_id group by to_char(fd.oper_date,'yyyy')"
            , nativeQuery = true)
    List<AggrReport> GetYearProjectProfit(@Param("project_id") Integer project_id);


}
