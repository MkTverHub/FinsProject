package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.dao.repository.UserRepository;
import com.sweetcard.basic.model.Contactform;
import com.sweetcard.basic.model.Finsprojectform;
import com.sweetcard.basic.model.LovForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class LovJdbc {
    private Logger logger = LoggerFactory.getLogger(LovJdbc.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer LovAction(LovForm lovForm) {
        try{
            Integer intResult = 0;
            String strOperationType = lovForm.getLovAction();
            logger.info("LovJdbc.LovAction: " + strOperationType);

            switch(strOperationType){
                case "update" : {
                    Integer intLovId = Integer.parseInt(lovForm.getLovId());
                    logger.info("LovJdbc.UpdateLov -> " + intLovId);
                    jdbcTemplate.update("update lov set text_val = ?, description = ?, options = ?, type = ? where id = ?",
                            lovForm.getLovVal(), lovForm.getLovDescription(), lovForm.getLovOptions(), lovForm.getLovType(), intLovId);
                }break;
                case "insert" : {
                    //Создание записи
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("INSERT INTO lov (text_val,description,options,type) VALUES (?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                            statement.setString(1, lovForm.getLovVal());
                            statement.setString(2, lovForm.getLovDescription());
                            statement.setString(3, lovForm.getLovOptions());
                            statement.setString(4, lovForm.getLovType());
                            return statement;
                        }
                    }, holder);

                    String strLovtId = holder.getKeyList().get(0).get("id").toString();
                    logger.info("LovJdbc.NewLov-> Lov Id: " + strLovtId);
                    intResult = Integer.parseInt(strLovtId);
                }break;
                case "delete" : {
                    Integer intLovId = Integer.parseInt(lovForm.getLovId());
                    logger.info("LovJdbc.DeleteLov -> " + intLovId);
                    jdbcTemplate.update("delete from lov where id = ?", intLovId);
                }break;

                default:{
                    logger.info("LovJdbc.LovAction: Неизвестная операция" + strOperationType);
                }
            }
            return intResult;

        }catch (Exception exp_sql){
            logger.info("LovJdbc.LovAction -> ERROR: " + exp_sql);
            return null;
        }
    }
}
