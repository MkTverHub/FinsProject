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
                    Integer intRequisitId = Integer.parseInt(cntragntreqform.getReqId());
                    Integer intContragentId = Integer.parseInt(cntragntreqform.getContragentId());
                    jdbcTemplate.update("update requisits set name=?,description=?,inn=?,kpp=?,fins_acc=?,bik=?,bank_name=?,crsp_acc=?,addr_index=?,addr_city=?,addr_string=?,phone_num=?,email_addr=? where id=? and par_row_id=?",
                            cntragntreqform.getReqName(),cntragntreqform.getReqDescription(),cntragntreqform.getReqINN(),cntragntreqform.getReqKPP(),cntragntreqform.getReqFinsAcc(),cntragntreqform.getReqBIK(),
                            cntragntreqform.getReqBankName(),cntragntreqform.getReqCrspAcc(),cntragntreqform.getReqAddrIndex(),cntragntreqform.getReqAddrCity(),cntragntreqform.getReqAddrString(),
                            cntragntreqform.getReqPhoneNum(),cntragntreqform.getReqEmail(),intRequisitId,intContragentId);
                }break;
                case "insert" : {
                    logger.info("RequisitJdbc.Requisitaction (new): " + " ContragentId=" + cntragntreqform.getContragentId());
                    Integer intContragentId = Integer.parseInt(cntragntreqform.getContragentId());
                    //Создание записи
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("insert into Requisits (par_row_id,name,description,inn,kpp,fins_acc,bik,bank_name,crsp_acc,addr_index,addr_city,addr_string,phone_num,email_addr) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                            statement.setInt(1, intContragentId);
                            statement.setString(2, cntragntreqform.getReqName());
                            statement.setString(3, cntragntreqform.getReqDescription());
                            statement.setString(4, cntragntreqform.getReqINN());
                            statement.setString(5, cntragntreqform.getReqKPP());
                            statement.setString(6, cntragntreqform.getReqFinsAcc());
                            statement.setString(7, cntragntreqform.getReqBIK());
                            statement.setString(8, cntragntreqform.getReqBankName());
                            statement.setString(9, cntragntreqform.getReqCrspAcc());
                            statement.setString(10, cntragntreqform.getReqAddrIndex());
                            statement.setString(11, cntragntreqform.getReqAddrCity());
                            statement.setString(12, cntragntreqform.getReqAddrString());
                            statement.setString(13, cntragntreqform.getReqPhoneNum());
                            statement.setString(14, cntragntreqform.getReqEmail());
                            return statement;
                        }
                    }, holder);

                    String strRequisit = holder.getKeyList().get(0).get("id").toString();
                    logger.info("RequisitJdbc.Requisitaction -> Requisit Id: " + strRequisit);
                    intResult = Integer.parseInt(strRequisit);
                }break;
                case "delete" : {
                    logger.info("RequisitJdbc.Requisitaction (delete):: RecordId=" + cntragntreqform.getReqId() + " ContragentId=" + cntragntreqform.getContragentId());
                    Integer intRequisitId = Integer.parseInt(cntragntreqform.getReqId());
                    Integer intContragentId = Integer.parseInt(cntragntreqform.getContragentId());
                    jdbcTemplate.update("delete from requisits where id=? and par_row_id=?", intRequisitId, intContragentId);
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
