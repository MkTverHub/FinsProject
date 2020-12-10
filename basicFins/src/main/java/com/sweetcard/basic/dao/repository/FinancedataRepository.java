package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.Financedata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Admin on 21.02.2020.
 */
public interface FinancedataRepository extends JpaRepository<Financedata, Integer> {
    //Выбрать все с групировкой по Id
    List<Financedata> findAllByOrderByIdAsc();

    //Выбрать все селектом
    @Query(value = "SELECT * FROM financedata", nativeQuery = true)
    List<Financedata> GetAll();

    //Выбрать все селектом по проекту
    @Query(value = "SELECT id,amount,detail,finscontragent,fins_oper_type,lock_flg,to_char(oper_date, 'yyyy-mm-dd hh24:mi:ss') oper_date,pay_acc_in,pay_acc_out,project_id,requisites,fins_article FROM financedata where project_id = :fins_project_id", nativeQuery = true)
    List<Financedata> GetAllByProj(@Param("fins_project_id") Integer finsprojectid);

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
