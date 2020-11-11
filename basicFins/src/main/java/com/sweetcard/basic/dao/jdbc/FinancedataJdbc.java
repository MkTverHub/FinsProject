package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.Financedataform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 21.02.2020.
 */
@Component
public class FinancedataJdbc {
    private Logger logger = LoggerFactory.getLogger(FinancedataJdbc.class);
    private Integer intActivProjectId = null;//Id текущего проекта
    @Autowired
    JdbcTemplate jdbcTemplate;

    //Апдейт записи
    public void RecordOperation(Financedataform finsform)
    {
        try {
            String strEditType = finsform.getFinsedittype();//insert/update/delete
            String strRecordId = finsform.getFinsrecordid();
            String strFinsOperType = finsform.getFinsOperType();//Тип транзакции profit/expense/transfer
            String strAmount = finsform.getFinsamount();
            String strPaymentAccIn = finsform.getPaymentAccIn();
            String strPaymentAccOut = finsform.getPaymentAccOut();
            String strFinsArticle = finsform.getFinsArticle();
            //String strProjectId = finsform.getProjectId();
            String strProjectId = intActivProjectId.toString();
            String strDetail = finsform.getFinsdetail();
            String strContrAgent = finsform.getFinscontragent();
            String strRequisites = finsform.getRequisites();

            Integer intRecordId = null;
            Integer intAmount = null;
            Integer intFinsArticle = null;
            if(strRecordId != null){intRecordId = Integer.parseInt(strRecordId);}
            if(strAmount != null){intAmount = Integer.parseInt(strAmount);}
            try{intFinsArticle = Integer.parseInt(strFinsArticle);}catch (Exception ex_int){intFinsArticle = 0;}

            //Обновление записи
            if (0 == strEditType.compareTo("update")) {
                logger.info("FinancedataJdbc.RecordOperation (update): Record Id = " + intRecordId);
                String strSQLUpdate = "update financedata set oper_date = now()::timestamp, fins_oper_type = '" + strFinsOperType + "'," +
                        "amount = " + intAmount + ", pay_acc_in = '" + strPaymentAccIn + "', pay_acc_out = '" + strPaymentAccOut + "'," +
                        "fins_article = " + intFinsArticle + ", detail = '" + strDetail + "'," +
                        "finscontragent = '" + strContrAgent + "', requisites = '" + strRequisites + "'" +
                        " where id = " + intRecordId + " and project_id = " + intActivProjectId;
                logger.info("FinancedataJdbc.RecordOperation (update): SQLUpdate " + strSQLUpdate);
                jdbcTemplate.update(strSQLUpdate);
                //jdbcTemplate.update("update financedata set oper_date = now()::timestamp, fins_oper_type = ?, amount = ?, pay_acc_in = ? , pay_acc_out = ? , fins_article = ?, project_id = ?, detail = ?,  finscontragent = ?, requisites = ? where id = ?",strFinsOperType ,intAmount, strPaymentAccIn, strPaymentAccOut, strFinsArticle, intActivProjectId, strDetail, strContrAgent, strRequisites, intRecordId);
            }

            //Создание записи
            if (0 == strEditType.compareTo("insert")) {
                logger.info("FinancedataJdbc.RecordOperation (new): ");
                String strSQLInsert = "insert into financedata (oper_date,fins_oper_type,amount,pay_acc_in,pay_acc_out,fins_article,detail,finscontragent,requisites,project_id) " +
                        "values (now()::timestamp,'"+ strFinsOperType +"'," + intAmount + ",'" + strPaymentAccIn + "','" + strPaymentAccOut + "',"
                        + intFinsArticle + ",'" + strDetail + "','" + strContrAgent + "','" + strRequisites + "'," + intActivProjectId + ");";
                logger.info("FinancedataJdbc.RecordOperation (insert): SQLInsert " + strSQLInsert);
                jdbcTemplate.update(strSQLInsert);
                //jdbcTemplate.update("insert into financedata (oper_date, fins_oper_type, amount, pay_acc_in, pay_acc_out, fins_article, project_id, detail, finscontragent, requisites) values (now()::timestamp, ?, ?, ?, ?, ?, ?, ?, ?, ?)", strFinsOperType ,intAmount, strPaymentAccIn, strPaymentAccOut, strFinsArticle, intActivProjectId, strDetail, strContrAgent, strRequisites);
                finsform.ClearFields();
            }

            //Удаление записи
            if (0 == strEditType.compareTo("delete")) {
                logger.info("FinancedataJdbc.RecordOperation (delete): ");
                String strSQLDelete = "delete from financedata where id = " + intRecordId + " and project_id = " + intActivProjectId + ";";
                //jdbcTemplate.update("delete from financedata where id = ? and project_id = ?", intRecordId, intActivProjectId);
                logger.info("FinancedataJdbc.RecordOperation (delete): SQLDelete " + strSQLDelete);
                jdbcTemplate.update(strSQLDelete);
                finsform.ClearFields();
            }

        }catch (Exception ex){
            logger.info("FinancedataJdbc.RecordOperation ERROR: " + ex);
        }
    }

    //-----------------------------SETTERS------------------------------
    public void setActivProjectId(Integer ProjectId){
        intActivProjectId = ProjectId;
    }

}
