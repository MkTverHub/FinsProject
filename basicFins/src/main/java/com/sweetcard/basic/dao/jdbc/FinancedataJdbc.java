package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.Financedataform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

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
            String strFinsOperDateUser = finsform.getFinsoperdateUser();
            if(strFinsOperDateUser == null || strFinsOperDateUser.compareTo("") == 0){
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                strFinsOperDateUser = sdfDate.format(now);
                logger.info("FinancedataJdbc.RecordOperation strFinsOperDateUser = " + strFinsOperDateUser);
            }
            Float flAmount = Float.parseFloat(finsform.getFinsamount());
            String strPaymentAccIn = finsform.getPaymentAccIn();
            String strPaymentAccOut = finsform.getPaymentAccOut();
            String strFinsArticle = finsform.getFinsArticle();
            String strDetail = finsform.getFinsdetail();
            Integer intContrAgent = 0;
            if(0 != finsform.getFinscontragent().compareTo("")){
                intContrAgent = Integer.parseInt(finsform.getFinscontragent());
            }
            Integer intRequisites = 0;
            if(0 != finsform.getRequisites().compareTo("")){
                intRequisites = Integer.parseInt(finsform.getRequisites());
            }

            Integer intRecordId = null;
            Integer intFinsArticle = null;
            if(strRecordId != null){if(0 == strRecordId.compareTo("")){intRecordId = null;}else{intRecordId = Integer.parseInt(strRecordId);}}

            try{intFinsArticle = Integer.parseInt(strFinsArticle);}catch (Exception ex_int){intFinsArticle = 0;}

            //Обновление записи
            if (0 == strEditType.compareTo("update")) {
                logger.info("FinancedataJdbc.RecordOperation (update): Record Id = " + intRecordId);
                String strSQLUpdate = "update financedata set oper_date = now()::timestamp, fins_oper_type = '" + strFinsOperType + "'," +
                        "oper_date_user = '" + strFinsOperDateUser + "',"+
                        "amount = " + flAmount + ", pay_acc_in = '" + strPaymentAccIn + "', pay_acc_out = '" + strPaymentAccOut + "'," +
                        "fins_article = " + intFinsArticle + ", detail = '" + strDetail + "'," +
                        "finscontragent = " + intContrAgent + ", requisites = " + intRequisites +
                        " where id = " + intRecordId + " and project_id = " + intActivProjectId;
                logger.info("FinancedataJdbc.RecordOperation (update): SQLUpdate: " + strSQLUpdate);
                jdbcTemplate.update(strSQLUpdate);
                //
                //jdbcTemplate.update("update financedata set oper_date = now()::timestamp, fins_oper_type = ?, amount = ?, pay_acc_in = ? , pay_acc_out = ? , fins_article = ?, project_id = ?, detail = ?,  finscontragent = ?, requisites = ? where id = ?",strFinsOperType ,intAmount, strPaymentAccIn, strPaymentAccOut, strFinsArticle, intActivProjectId, strDetail, strContrAgent, strRequisites, intRecordId);
            }

            //Создание записи
            if (0 == strEditType.compareTo("insert")) {
                logger.info("FinancedataJdbc.RecordOperation (new): ");
                String strSQLInsert = "insert into financedata (oper_date,fins_oper_type,oper_date_user,amount,pay_acc_in,pay_acc_out,fins_article,detail,finscontragent,requisites,project_id) " +
                        "values (now()::timestamp,'"+ strFinsOperType +"','" + strFinsOperDateUser + "',"+ flAmount + ",'" + strPaymentAccIn + "','" + strPaymentAccOut + "',"
                        + intFinsArticle + ",'" + strDetail + "'," + intContrAgent + "," + intRequisites + "," + intActivProjectId + ");";
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
