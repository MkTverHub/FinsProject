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
    String strSpecSymbol = "\\:";

    //Выбрать все селектом
    @Query(value = "SELECT * FROM financedata", nativeQuery = true)
    List<AggrFinsdata> GetAll();

    //Выбрать все селектом по проекту
    @Query(value = "SELECT\n" +
            "              t1.id,t1.amount,t1.detail,t1.finscontragent,t1.fins_oper_type as finsopertype,t1.lock_flg as lockflg,\n" +
            "              to_char(t1.oper_date, 'yyyy-mm-dd') as operdate,\n" +
            "              to_char(t1.oper_date_user, 'yyyy-mm-dd') as operdate_user,\n" +
            "              t1.oper_login_user as oper_login_user,\n" +
            "              case when t1.fins_oper_type = 'expense' then t3.fins_acc else case when t1.fins_oper_type = 'profit' then t1.pay_acc_in else t1.pay_acc_in end end payaccin,\n" +
            "              case when t1.fins_oper_type = 'expense' then t2.name else case when t1.fins_oper_type = 'profit' then cnt_in.first_name else cnt_in.first_name end end payaccin_name,\n" +
            "              case when t1.fins_oper_type = 'expense' then t2.id else case when t1.fins_oper_type = 'profit' then 0 else 0 end end payaccin_cnt_agnt_id,\n" +
            "              case when t1.fins_oper_type = 'expense' then 0 else case when t1.fins_oper_type = 'profit' then cnt_in.id else cnt_in.id end end payaccin_contact_id,\n" +
            "              case when t1.fins_oper_type = 'expense' then t2.description else case when t1.fins_oper_type = 'profit' then cnt_in.description else cnt_in.description end end payaccin_position,\n" +
            "              case when t1.fins_oper_type = 'expense' then t1.pay_acc_out else case when t1.fins_oper_type = 'profit' then t3.fins_acc else t1.pay_acc_out end end payaccout,\n" +
            "              case when t1.fins_oper_type = 'expense' then cnt_out.first_name else case when t1.fins_oper_type = 'profit' then t2.name else cnt_out.first_name end end payaccout_name,\n" +
            "              case when t1.fins_oper_type = 'expense' then 0 else case when t1.fins_oper_type = 'profit' then t2.id else 0 end end payaccout_cnt_agnt_id,\n" +
            "              case when t1.fins_oper_type = 'expense' then cnt_out.id else case when t1.fins_oper_type = 'profit' then 0 else cnt_out.id end end payaccout_contact_id,\n" +
            "              case when t1.fins_oper_type = 'expense' then cnt_out.description else case when t1.fins_oper_type = 'profit' then t2.description else cnt_out.description end end payaccout_position,\n" +
            "              t1.project_id as projectid,t1.requisites,t1.fins_article as finsarticle,t2.name as contragent_name, t3.name as requisites_name,t4.id as article_id ,t4.text_val as article_name,prp.id as purpose_id,prp.name as purpose_name\n" +
            "            FROM\n" +
            "              financedata t1\n" +
            "              left join contragent t2 on t1.finscontragent = t2.id\n" +
            "              left join requisits t3 on t1.requisites = t3.id\n" +
            "              left join lov t4 on t1.fins_article = t4.id\n" +
            "              left join contact cnt_in on t1.pay_acc_in = cnt_in.fins_acc\n" +
            "              left join contact cnt_out on t1.pay_acc_out = cnt_out.fins_acc\n" +
            "              left join purpose prp on t1.purpose_id = prp.id\n" +
            "            where\n" +
            "              t1.project_id = :fins_project_id limit :limit_value offset :offset_value", nativeQuery = true)
    List<AggrFinsdata> GetAllByProj(@Param("fins_project_id") Integer finsprojectid, @Param("limit_value") Integer limit_value, @Param("offset_value") Integer offset_value);

    //Получиь чистую прибыль по проекту
    @Query(value = "select ROUND(AVG((select  case when sum(amount) is null then 0 else sum(amount) end as profit from financedata where project_id = :fins_project_id and fins_oper_type = 'profit') - (select  case when sum(amount) is null then 0 else sum(amount) end as expense from financedata where project_id = :fins_project_id and fins_oper_type = 'expense'))" + strSpecSymbol + strSpecSymbol + "numeric,2)", nativeQuery = true)

    String GetProjectProfit(@Param("fins_project_id") Integer finsprojectid);

    //Получить приход по проекту
    @Query(value = "select  case when sum(amount) is null then 0 else sum(amount) end as profit from financedata where project_id = :fins_project_id and fins_oper_type = 'profit'", nativeQuery = true)
    String GetProjectIncome (@Param("fins_project_id") Integer finsprojectid);

    //Получить расход по проекту
    @Query(value = "select  case when sum(amount) is null then 0 else sum(amount) end as expense from financedata where project_id = :fins_project_id and fins_oper_type = 'expense'", nativeQuery = true)
    String GetProjectExpense (@Param("fins_project_id") Integer finsprojectid);



}
