package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrContragent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AggregateDataContragent extends JpaRepository<AggrContragent, Integer> {
    @Query(value =
            "select td.id,td.name,td.description,td.phone_num,td.email_addr,td.type,td.amount_out,td.amount_in,(td.amount_in - td.amount_out) balance, td.created, td.main_inn, td.main_cardnum\n" +
                    "from\n" +
                    "(select cnt.id,cnt.name,cnt.description,cnt.phone_num,cnt.email_addr,cnt.type,case when fins_out.amount is null then 0 else fins_out.amount end amount_out,case when fins_in.amount is null then 0 else fins_in.amount end amount_in, to_char(cnt.created, 'yyyy-mm-dd hh24:mi:ss') as created, rq.inn as main_inn, rq.card_num as main_cardnum \n" +
                    "from contragent cnt left join (select int4(fd.finscontragent) cnt_id,sum(fd.amount) amount from financedata fd where fd.fins_oper_type = 'profit' group by fd.finscontragent) fins_out on cnt.id = fins_out.cnt_id\n" +
                    "left join (select int4(fd.finscontragent) cnt_id, sum(fd.amount) amount from financedata fd where fd.fins_oper_type = 'expense' group by fd.finscontragent) fins_in on cnt.id = fins_in.cnt_id\n" +
                    "left join requisits rq on cnt.id = rq.par_row_id and main_flg = true) td"
            , nativeQuery = true)
    List<AggrContragent> GetAllContragent();
}
