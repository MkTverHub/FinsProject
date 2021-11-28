package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrFinsproject;
import com.sweetcard.basic.dao.entities.AggrPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataPurposeRepository extends JpaRepository<AggrPurpose,Integer> {
    String strSpecSymbol = "\\:";
    @Query(value = "select\n" +
            " t_data.id,\n"+
            " t_data.name,\n" +
            " t_data.description,\n" +
            " ROUND(AVG(t_data.profit_probable)"+strSpecSymbol+strSpecSymbol+"numeric,2) as profit_probable,\n" +
            " ROUND(AVG(t_data.profit_real)"+strSpecSymbol+strSpecSymbol+"numeric,2) as profit_real,\n" +
            " ROUND(AVG(t_data.profit_real - t_data.profit_probable)"+strSpecSymbol+strSpecSymbol+"numeric,2) as profit_total\n" +
            "from\n" +
            "(\n" +
            "select\n" +
            "prp.id,\n" +
            " prp.name,\n" +
            " prp.description,\n" +
            " case when prp.profit is null then 0 else prp.profit end as profit_probable,\n" +
            " case when t_profit_real.amount is null then 0 else t_profit_real.amount end as profit_real\n" +
            "from \n" +
            " purpose prp\n" +
            " left join (select sum(amount) as amount,purpose_id  from financedata where fins_oper_type = 'profit' group by purpose_id) t_profit_real on prp.id = t_profit_real.purpose_id\n" +
            "where\n" +
            " prp.id = :purpose_id and prp.par_row_id = :project_id\n" +
            ") t_data\n" +
            "group by \n" +
            "t_data.id,t_data.name,t_data.description,t_data.profit_real,t_data.profit_probable", nativeQuery = true)
    List<AggrPurpose> GetPurposeData(@Param("project_id") Integer project_id_in, @Param("purpose_id") Integer purpose_id_in);
}
