package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.Cntragntreqform;
import com.sweetcard.basic.model.Contragentform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Admin on 22.03.2020.
 */
@Component
public class RequisitJdbc {
    private Logger logger = LoggerFactory.getLogger(FinancedataJdbc.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer Requisitaction(Cntragntreqform cntragntreqform) {
        try{
            String strOperationType = cntragntreqform.getReqaction();
            logger.info("RequisitJdbc.Requisitaction: " + strOperationType);
            Integer intResult = 0;

            switch(strOperationType){
                case "update" : {
                    logger.info("RequisitJdbc.Requisitaction (update): RecordId=" + cntragntreqform.getReqId() + " ContragentId=" + cntragntreqform.getContragentId());

                    //Вызов функции DB для обновления флага Main на реквезитах контрагента
                    jdbcTemplate.execute("select reset_main_requisit(" + cntragntreqform.getBlMainFlg() + "," + cntragntreqform.getContragentId() + "," +cntragntreqform.getReqId() + ");");

                    jdbcTemplate.update("update requisits set main_flg=?,name=?,description=?,inn=?,kpp=?,fins_acc=?,bik=?,bank_name=?,crsp_acc=?,addr_index=?,addr_city=?,addr_string=?,phone_num=?,email_addr=?,card_num=? where id=? and par_row_id=?",
                            cntragntreqform.getBlMainFlg(),cntragntreqform.getReqName(),cntragntreqform.getReqDescription(),cntragntreqform.getReqINN(),cntragntreqform.getReqKPP(),cntragntreqform.getReqFinsAcc(),cntragntreqform.getReqBIK(),
                            cntragntreqform.getReqBankName(),cntragntreqform.getReqCrspAcc(),cntragntreqform.getReqAddrIndex(),cntragntreqform.getReqAddrCity(),cntragntreqform.getReqAddrString(),
                            cntragntreqform.getReqPhoneNum(),cntragntreqform.getReqEmail(),cntragntreqform.getCardNumber(),cntragntreqform.getReqId(),cntragntreqform.getContragentId());
                }break;
                case "insert" : {
                    logger.info("RequisitJdbc.Requisitaction (new): " + " ContragentId=" + cntragntreqform.getContragentId());

                    //Вызов функции DB для флага Main на реквезитах контрагента
                    jdbcTemplate.execute("select reset_main_requisit(" + cntragntreqform.getBlMainFlg() + "," + cntragntreqform.getContragentId() + ",null);");

                    //Создание записи
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("insert into Requisits (par_row_id,main_flg,name,description,inn,kpp,fins_acc,bik,bank_name,crsp_acc,addr_index,addr_city,addr_string,phone_num,email_addr,card_num) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                            statement.setInt(1, cntragntreqform.getContragentId());
                            statement.setBoolean(2, cntragntreqform.getBlMainFlg());
                            statement.setString(3, cntragntreqform.getReqName());
                            statement.setString(4, cntragntreqform.getReqDescription());
                            statement.setString(5, cntragntreqform.getReqINN());
                            statement.setString(6, cntragntreqform.getReqKPP());
                            statement.setString(7, cntragntreqform.getReqFinsAcc());
                            statement.setString(8, cntragntreqform.getReqBIK());
                            statement.setString(9, cntragntreqform.getReqBankName());
                            statement.setString(10, cntragntreqform.getReqCrspAcc());
                            statement.setString(11, cntragntreqform.getReqAddrIndex());
                            statement.setString(12, cntragntreqform.getReqAddrCity());
                            statement.setString(13, cntragntreqform.getReqAddrString());
                            statement.setString(14, cntragntreqform.getReqPhoneNum());
                            statement.setString(15, cntragntreqform.getReqEmail());
                            statement.setString(16, cntragntreqform.getCardNumber());
                            return statement;
                        }
                    }, holder);

                    String strRequisit = holder.getKeyList().get(0).get("id").toString();
                    logger.info("RequisitJdbc.Requisitaction -> Requisit Id: " + strRequisit);
                    intResult = Integer.parseInt(strRequisit);
                }break;
                case "delete" : {
                    logger.info("RequisitJdbc.Requisitaction (delete):: RecordId=" + cntragntreqform.getReqId() + " ContragentId=" + cntragntreqform.getContragentId());
                    //Integer intRequisitId = Integer.parseInt(cntragntreqform.getReqId());
                    //Integer intContragentId = Integer.parseInt(cntragntreqform.getContragentId());
                    jdbcTemplate.update("delete from requisits where id=? and par_row_id=?", cntragntreqform.getReqId(), cntragntreqform.getContragentId());
                }break;
                default:{
                    logger.info("RequisitJdbc -> Error: Неизвестный тип операции: " + strOperationType);
                }
            }
            return intResult;

        }catch (Exception exp_sql){
            logger.info("RequisitJdbc.Requisitaction -> ERROR: " + exp_sql);
            return null;
        }
    }

}
