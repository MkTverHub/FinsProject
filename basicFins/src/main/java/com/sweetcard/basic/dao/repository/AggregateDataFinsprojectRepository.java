package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrFinsproject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataFinsprojectRepository extends JpaRepository<AggrFinsproject,Integer> {
    String strSpecSymbol = "\\:";
    //Выбрать все проекты пользователя
    @Query(value = "select t1.id,t1.name,t1.description,to_char(t1.created, 'yyyy-mm-dd') as created,\n" +
            "ROUND(AVG(case when t_profit.amount_in is null then 0 else t_profit.amount_in end)" + strSpecSymbol + strSpecSymbol + "numeric,2) amount_in,\n" +
            "ROUND(AVG(case when t_expense.amount_out is null then 0 else t_expense.amount_out end)" + strSpecSymbol + strSpecSymbol + "numeric,2) amount_out,\n" +
            "ROUND(AVG((case when t_profit.amount_in is null then 0 else t_profit.amount_in end) - (case when t_expense.amount_out is null then 0 else t_expense.amount_out end))" + strSpecSymbol + strSpecSymbol + "numeric,2) as balance\n" +
            "from finsproject t1\n" +
            "left join (select sum(amount) as amount_in, project_id from financedata where fins_oper_type = 'profit' group by project_id) t_profit on t1.id = t_profit.project_id\n" +
            "left join (select sum(amount) as amount_out, project_id from financedata where fins_oper_type = 'expense' group by project_id) t_expense on t1.id = t_expense.project_id\n" +
            "where\n" +
            "t1.id in (select project_id from it_proj_user where user_id = (select id from app_user where email = :user_login))\n" +
            "group by t1.id,t_expense.amount_out,t_profit.amount_in", nativeQuery = true)
    List<AggrFinsproject> GetAllUserProjects(@Param("user_login") String user_login_in);

}
