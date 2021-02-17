package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrFinsdata;
import com.sweetcard.basic.dao.entities.Customer;
import com.sweetcard.basic.dao.entities.Financedata;
import com.sweetcard.basic.dao.entities.Finsproject;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Admin on 21.02.2020.
 */
public interface FinancedataRepository extends JpaRepository<AggrFinsdata, Integer> {

    //Выбрать все селектом
    @Query(value = "SELECT * FROM financedata", nativeQuery = true)
    List<AggrFinsdata> GetAll();

    //Выбрать все селектом по проекту
    @Query(value = "SELECT \n" +
            "t1.id,t1.amount,t1.detail,t1.finscontragent,t1.fins_oper_type as finsopertype,t1.lock_flg as lockflg,\n" +
            "to_char(t1.oper_date, 'yyyy-mm-dd hh24:mi:ss') as operdate,t1.pay_acc_in as payaccin,t1.pay_acc_out as payaccout,\n" +
            "t1.project_id as projectid,t1.requisites,t1.fins_article as finsarticle,t2.name as contragent_name, t3.name as requisites_name, t4.text_val as article_name\n" +
            "FROM financedata t1\n" +
            "left join contragent t2 on t1.finscontragent = t2.id\n" +
            "left join requisits t3 on t1.requisites = t3.id\n" +
            "left join lov t4 on t1.fins_article = t4.id\n" +
            "\n" +
            "where t1.project_id = :fins_project_id", nativeQuery = true)
    List<AggrFinsdata> GetAllByProj(@Param("fins_project_id") Integer finsprojectid);

    //Получиь чистую прибыль по проекту
    @Query(value = "select (select sum(amount) as profit from financedata where project_id = :fins_project_id and fins_oper_type = 'profit') - (select sum(amount) as expense from financedata where project_id = :fins_project_id and fins_oper_type = 'expense')\n", nativeQuery = true)
    String GetProjectProfit(@Param("fins_project_id") Integer finsprojectid);

    //Получить приход по проекту
    @Query(value = "select sum(amount) as profit from financedata where project_id = :fins_project_id and fins_oper_type = 'profit'", nativeQuery = true)
    String GetProjectIncome (@Param("fins_project_id") Integer finsprojectid);

    //Получить расход по проекту
    @Query(value = "select sum(amount) as expense from financedata where project_id = :fins_project_id and fins_oper_type = 'expense'", nativeQuery = true)
    String GetProjectExpense (@Param("fins_project_id") Integer finsprojectid);

    //----Отчетность----------
    //Получить profit по годам

}
