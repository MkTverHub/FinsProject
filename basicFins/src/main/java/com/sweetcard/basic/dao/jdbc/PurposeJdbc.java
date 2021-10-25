package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.PurposeForm;
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

@Component
public class PurposeJdbc {
    private Logger logger = LoggerFactory.getLogger(LovJdbc.class);
    @Autowired
    JdbcTemplate jdbcTemplate;
    private Integer intActiveProjectId = null;//Id текущего проекта

    public Integer PurposeAction(PurposeForm purposeForm) {
        try{
            Integer intResult = 0;
            String strOperationType = purposeForm.getPurposeAction();
            logger.info("Purpose.PurposeAction: " + strOperationType);

            switch(strOperationType){
                case "update" : {
                    Integer intPurposeId = purposeForm.getId();
                    logger.info("PurposeJdbc.UpdatePurpose -> " + intPurposeId);
                    jdbcTemplate.update("update Purpose set par_row_id = ?, name = ?, description = ?, expense = ?, profit = ? where id = ?",
                            intActiveProjectId,purposeForm.getName(), purposeForm.getDescription(), purposeForm.getExpense(), purposeForm.getProfit(), intPurposeId);
                }break;
                case "insert" : {
                    //Создание записи
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("INSERT INTO Purpose (par_row_id,name,description,expense,profit) VALUES (?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                            statement.setInt(1, intActiveProjectId);
                            statement.setString(2, purposeForm.getName());
                            statement.setString(3, purposeForm.getDescription());
                            statement.setString(4,purposeForm.getExpense());
                            statement.setString(5, purposeForm.getProfit());
                            return statement;
                        }
                    }, holder);

                    String strPurposeId = holder.getKeyList().get(0).get("id").toString();
                    logger.info("PurposeJdbc.NewPurpose-> Lov Id: " + strPurposeId);
                    intResult = Integer.parseInt(strPurposeId);
                }break;
                case "delete" : {
                    Integer intPurposeId = purposeForm.getId();
                    logger.info("PurposeJdbc.DeletePurpose -> " + intPurposeId);
                    jdbcTemplate.update("delete from Purpose where id = ?", intPurposeId);
                }break;

                default:{
                    logger.info("PurposeJdbc.PurposeAction: Неизвестная операция" + strOperationType);
                }
            }
            return intResult;

        }catch (Exception exp_sql){
            logger.info("PurposeJdbc.PurposeAction -> ERROR: " + exp_sql);
            return null;
        }
    }


    //-----------------------------SETTERS------------------------------
    public void setActiveProjectId(Integer ProjectId){
        intActiveProjectId = ProjectId;
    }
}
