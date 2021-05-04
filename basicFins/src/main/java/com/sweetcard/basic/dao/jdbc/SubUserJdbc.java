package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.Contragentform;
import com.sweetcard.basic.model.SubUserForm;
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
public class SubUserJdbc {
    private Logger logger = LoggerFactory.getLogger(SubUserJdbc.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer SubUserAction(SubUserForm subUserForm) {
        try{
            Integer intResult = 0;
            String strOperationType = subUserForm.getDBOperation();
            logger.info("SubUserJdbc.SubUserAction: " + strOperationType);

            switch(strOperationType){
                case "update" : {

                }break;
                case "insert" : {
                    //
                }break;
                case "delete" : {
                    //
                }break;
                    //
                default:{

                }
            }
            return intResult;

        }catch (Exception exp_sql){
            logger.info("ContragentJdbc.Contragentaction -> ERROR: " + exp_sql);
            return null;
        }
    }
}
