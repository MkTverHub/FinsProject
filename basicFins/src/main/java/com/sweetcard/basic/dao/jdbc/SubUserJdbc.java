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
    private Integer intParentUserId;

    public Integer SubUserAction(SubUserForm subUserForm) {
        try{
            Integer intResult = 0;
            String strOperationType = subUserForm.getDBOperation();
            logger.info("SubUserJdbc.SubUserAction: " + strOperationType);

            switch(strOperationType){
                case "update" : {

                    jdbcTemplate.update("update app_user set email = ?, first_name = ?, last_name = ?, middle_name = ?, phone = ?, position = ? where id = ? and parent_id = ?",
                            subUserForm.getEmail(), subUserForm.getFirstName(), subUserForm.getLastName(), subUserForm.getMiddleName(),
                            subUserForm.getPhone(), subUserForm.getPosition(), subUserForm.getId(), subUserForm.getParentId());
                }break;
                case "insert" : {
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("INSERT INTO app_user (app_user_role,email,enabled,first_name,last_name,locked,parent_id,password,middle_name,phone,position) VALUES (?,?,?,?,?,?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                            statement.setString(1, subUserForm.getAppUserRole());
                            statement.setString(2, subUserForm.getEmail());
                            statement.setBoolean(3, subUserForm.getEnabled());
                            statement.setString(4, subUserForm.getFirstName());
                            statement.setString(5, subUserForm.getLastName());
                            statement.setBoolean(6, subUserForm.getLocked());
                            statement.setInt(7, subUserForm.getParentId());
                            statement.setString(8, subUserForm.getPassword());
                            statement.setString(9, subUserForm.getMiddleName());
                            statement.setString(10, subUserForm.getPhone());
                            statement.setString(11, subUserForm.getPosition());
                            return statement;
                        }
                    }, holder);
                    String strSubUserId = holder.getKeyList().get(0).get("id").toString();
                    intResult = Integer.parseInt(strSubUserId);
                }break;
                case "delete" : {
                    jdbcTemplate.update("delete from app_user where id = ? and parent_id = ?",
                            subUserForm.getId(), subUserForm.getParentId());
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

    public void SetParentUserId(Integer ParentId){intParentUserId = ParentId;}
}
