package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.LovForm;
import com.sweetcard.basic.model.Usercacheform;
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
public class UsercacheJdbc {
    private Logger logger = LoggerFactory.getLogger(UsercacheJdbc.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer UsercacheAction(Usercacheform usercacheform) {
        try{
            Integer intResult = 0;
            String strOperationType = usercacheform.getUsercacheAction();
            logger.info("UsercacheJdbc.UsercacheAction: " + strOperationType);

            switch(strOperationType){
                case "update" : {
                    logger.info("UsercacheJdbc.UsercacheAction.update -> ");
                    jdbcTemplate.update("update usercache set active_proj = ?, user_id = ? where login = ?",
                            usercacheform.getActiveProject(), usercacheform.getUserId(), usercacheform.getLogin());
                }break;
                case "insert" : {
                    //Создание записи
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("INSERT INTO usercache (login,active_proj,user_id) VALUES (?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                            statement.setString(1, usercacheform.getLogin());
                            statement.setInt(2, usercacheform.getActiveProject());
                            statement.setInt(3, usercacheform.getUserId());
                            return statement;
                        }
                    }, holder);

                    String strUserCacheId = holder.getKeyList().get(0).get("id").toString();
                    intResult = Integer.parseInt(strUserCacheId);
                }break;
                case "delete" : {
                    //
                }break;

                default:{
                    logger.info("UsercacheJdbc.UsercacheAction: Неизвестная операция" + strOperationType);
                }
            }
            return intResult;

        }catch (Exception exp_sql){
            logger.info("UsercacheJdbc.UsercacheAction -> ERROR: " + exp_sql);
            return null;
        }
    }
}
