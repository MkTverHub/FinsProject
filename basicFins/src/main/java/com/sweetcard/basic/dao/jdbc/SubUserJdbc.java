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
import java.text.SimpleDateFormat;
import java.util.Date;

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

                            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date now = new Date();
                            String strDateUser = sdfDate.format(now);

                            PreparedStatement statement = con.prepareStatement("INSERT INTO app_user (access_dt,access_status,app_user_role,email,enabled,first_name,last_name,locked,parent_id,password,middle_name,phone,position) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                            statement.setTimestamp(1, java.sql.Timestamp.valueOf(strDateUser));
                            statement.setString(2, "demo");
                            statement.setString(3, subUserForm.getAppUserRole());
                            statement.setString(4, subUserForm.getEmail());
                            statement.setBoolean(5, subUserForm.getEnabled());
                            statement.setString(6, subUserForm.getFirstName());
                            statement.setString(7, subUserForm.getLastName());
                            statement.setBoolean(8, subUserForm.getLocked());
                            statement.setInt(9, subUserForm.getParentId());
                            statement.setString(10, subUserForm.getPassword());
                            statement.setString(11, subUserForm.getMiddleName());
                            statement.setString(12, subUserForm.getPhone());
                            statement.setString(13, subUserForm.getPosition());
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
            logger.info("SubUserJdbc.SubUserAction -> ERROR: " + exp_sql);
            return null;
        }
    }

    public void SetParentUserId(Integer ParentId){intParentUserId = ParentId;}
}
