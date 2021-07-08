package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.SubUserForm;
import com.sweetcard.basic.model.UserForm;
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
public class UserJdbc {
    private Logger logger = LoggerFactory.getLogger(UserJdbc.class);
    @Autowired
    JdbcTemplate jdbcTemplate;
    private Integer intParentUserId;

    public Integer UserAction(UserForm userForm) {
        try{
            Integer intResult = 0;
            String strOperationType = userForm.getUserAction();
            logger.info("UserJdbc.UserAction: " + strOperationType);
            switch(strOperationType){
                case "update" : {
                    jdbcTemplate.update("update app_user set first_name = ?, last_name = ?, middle_name = ?, phone = ? where email = ?",
                             userForm.getFirstName(), userForm.getLastName(), userForm.getMiddleName(), userForm.getPhone(), userForm.getEmail());
                }break;
                default:{

                }
            }
            return intResult;

        }catch (Exception exp_sql){
            logger.info("UserJdbc.UserAction -> ERROR: " + exp_sql);
            return null;
        }
    }

}
