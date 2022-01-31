package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrContact;
import com.sweetcard.basic.dao.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataContactRepository extends JpaRepository<AggrContact,Integer> {
    String strSpecSymbol = "\\:";
    //Выбрать все для компании
    @Query(value = "select cnt.id,cnt.first_name,cnt.last_name,cnt.fins_acc,cnt.par_row_id,cnt.balance,cnt.description,\n" +
            " ROUND(AVG(case when t_profit.amount_in is null then 0 else t_profit.amount_in end)"+strSpecSymbol+strSpecSymbol+"numeric,2) profit\n" +
            "from \n" +
            "contact cnt \n" +
            "left join (select sum(amount) as amount_in, project_id,pay_acc_in from financedata where fins_oper_type = 'profit' group by project_id,pay_acc_in) t_profit on cnt.par_row_id = t_profit.project_id and cnt.fins_acc = t_profit.pay_acc_in\n" +
            "where cnt.par_row_id = :fins_project_id group by cnt.id", nativeQuery = true)
    List<AggrContact> GetAllProject(@Param("fins_project_id") Integer finsprojectid);
}
