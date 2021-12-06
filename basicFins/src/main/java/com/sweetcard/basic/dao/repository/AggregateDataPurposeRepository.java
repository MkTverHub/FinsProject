package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataPurposeRepository extends JpaRepository<AggrPurpose,Integer> {
    String strSpecSymbol = "\\:";
    @Query(value = "select\n" +
            " t.id,\n" +
            " t.name,\n" +
            " t.description,\n" +
            " ROUND(AVG(t.profit_probable)"+strSpecSymbol+strSpecSymbol+"numeric,2) as profit_probable,\n" +
            " ROUND(AVG(t.profit_real)"+strSpecSymbol+strSpecSymbol+"numeric,2) as profit_real,\n" +
            " ROUND(AVG(t.profit_total)"+strSpecSymbol+strSpecSymbol+"numeric,2) as profit_total,\n" +
            " ROUND(AVG(t.expense_probable)"+strSpecSymbol+strSpecSymbol+"numeric,2) as expense_probable,\n" +
            " ROUND(AVG(t.expense_real)"+strSpecSymbol+strSpecSymbol+"numeric,2) as expense_real,\n" +
            " ROUND(AVG(t.expense_total)"+strSpecSymbol+strSpecSymbol+"numeric,2) as expense_total,\n" +
            " ROUND(AVG(t.balance_probable)"+strSpecSymbol+strSpecSymbol+"numeric,2) as balance_probable,\n" +
            " ROUND(AVG(t.balance_real)"+strSpecSymbol+strSpecSymbol+"numeric,2) as balance_real,\n" +
            " ROUND(AVG(t.balance_total)"+strSpecSymbol+strSpecSymbol+"numeric,2) as balance_total,\n" +
            " ROUND(AVG(case when t.expense_probable=0 then 0 else ((t.profit_probable-t.expense_probable)/t.expense_probable)*100 end)"+strSpecSymbol+strSpecSymbol+"numeric,2) as percent_probable,\n" +
            " ROUND(AVG(case when t.expense_real=0 then 0 else ((t.profit_real-t.expense_real)/t.expense_real)*100 end)"+strSpecSymbol+strSpecSymbol+"numeric,2) as percent_real,\n" +
            " ROUND(AVG(case when t.expense_total=0 then 0 else ((t.profit_total-t.expense_total)/t.expense_total)*100 end)"+strSpecSymbol+strSpecSymbol+"numeric,2) as percent_total\n" +
            "from\n" +
            "(\n" +
            "select\n" +
            "        t_data.id,\n" +
            "        t_data.name,\n" +
            "        t_data.description,\n" +
            "        t_data.profit_probable,\n" +
            "        t_data.profit_real,\n" +
            "        (t_data.profit_real - t_data.profit_probable) as profit_total,\n" +
            "        t_data.expense_probable,\n" +
            "        t_data.expense_real,\n" +
            "        (t_data.expense_probable - t_data.expense_real) as expense_total,\n" +
            "        (t_data.profit_probable-t_data.expense_probable) as balance_probable,\n" +
            "        (t_data.profit_real-t_data.expense_real) as balance_real,\n" +
            "        (t_data.profit_real - t_data.profit_probable)-(t_data.profit_probable-t_data.expense_probable) as balance_total\n" +
            "    from\n" +
            "        ( select\n" +
            "            prp.id,\n" +
            "            prp.name,\n" +
            "            prp.description,\n" +
            "            case when prp.profit is null then 0 else prp.profit end as profit_probable,\n" +
            "            case when t_profit_real.amount is null then 0 else t_profit_real.amount end as profit_real,\n" +
            "            case when prp.expense is null then 0 else prp.expense end as expense_probable,\n" +
            "            case when t_expense_real.amount is null then 0 else t_expense_real.amount end as expense_real\n" +
            "        from\n" +
            "            purpose prp  \n" +
            "            left join (select sum(amount) as amount,purpose_id from financedata where fins_oper_type = 'profit' group by purpose_id) t_profit_real \n" +
            "                on prp.id = t_profit_real.purpose_id\n" +
            "            left join (select sum(amount) as amount,purpose_id from financedata where fins_oper_type = 'expense' group by purpose_id) t_expense_real \n" +
            "                on prp.id = t_expense_real.purpose_id\n" +
            "        where\n" +
            "            prp.par_row_id = 1 \n" +
            "        ) t_data \n" +
            "    group by\n" +
            "        t_data.id,\n" +
            "        t_data.name,\n" +
            "        t_data.description,\n" +
            "        t_data.profit_real,\n" +
            "        t_data.profit_probable,\n" +
            "        t_data.expense_probable,\n" +
            "        t_data.expense_real\n" +
            ") t\n" +
            "group by\n" +
            " t.id,\n" +
            " t.name,\n" +
            " t.description,\n" +
            " t.profit_real,\n" +
            " t.profit_probable,\n" +
            " t.expense_probable,\n" +
            " t.expense_real,\n" +
            " t.expense_total", nativeQuery = true)
    List<AggrPurpose> GetPurposeData(@Param("project_id") Integer project_id_in);
}
