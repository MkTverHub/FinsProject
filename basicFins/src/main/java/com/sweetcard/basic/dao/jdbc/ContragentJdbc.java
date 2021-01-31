package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.Contragentform;
import com.sweetcard.basic.model.Finsprojectform;
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
 * Created by Admin on 19.03.2020.
 */
@Component
public class ContragentJdbc {
    private Logger logger = LoggerFactory.getLogger(FinancedataJdbc.class);
    private Integer intActivProjectId = null;//Id текущего проекта
    @Autowired
    JdbcTemplate jdbcTemplate;
    //Создание записи
    public Integer Contragentaction(Contragentform contragentform) {
        try{
            Integer intResult = 0;
            String strOperationType = contragentform.getContragentaction();
            logger.info("ContragentJdbc.Contragentaction: " + strOperationType);

            switch(strOperationType){
                case "update" : {
                    Integer intContragentId = Integer.parseInt(contragentform.getContragentid());
                    logger.info("ContragentJdbc.Update: Id = " + intContragentId);
                    jdbcTemplate.update("update Contragent set project_id = ?, name = ?, description = ?, phone_num = ?, email_addr = ?, type = ? where id = ?",
                            intActivProjectId, contragentform.getContragentname() ,contragentform.getContragentdescription(), contragentform.getContragentphone(), contragentform.getContragentemail(), contragentform.getContragentType(), intContragentId);

                }break;
                case "insert" : {
                    //Создание записи
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("INSERT INTO contragent (project_id ,name , description, phone_num, email_addr,type) VALUES (?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                            statement.setInt(1, intActivProjectId);
                            statement.setString(2, contragentform.getContragentname());
                            statement.setString(3, contragentform.getContragentdescription());
                            statement.setString(4, contragentform.getContragentphone());
                            statement.setString(5, contragentform.getContragentemail());
                            statement.setString(6, contragentform.getContragentType());
                            return statement;
                        }
                    }, holder);

                    String strContragentId = holder.getKeyList().get(0).get("id").toString();
                    logger.info("ContragentJdbc.NewContragent -> Contragent Id: " + strContragentId);
                    intResult = Integer.parseInt(strContragentId);
                }break;
                case "delete" : {
                    Integer intContragentId = Integer.parseInt(contragentform.getContragentid());
                    logger.info("ContragentJdbc.Delete: Id = " + intContragentId + "/" + intActivProjectId);
                    jdbcTemplate.update("delete from contragent where project_id = ? and id = ?", intActivProjectId, intContragentId);
                }break;

                default:{

                }
            }
            return intResult;

        }catch (Exception exp_sql){
            logger.info("ContragentJdbc.Contragentaction -> ERROR: " + exp_sql);
            return null;
        }
    }

    //-----------------------------SETTERS------------------------------
    public void setActivProjectId(Integer ProjectId){
        intActivProjectId = ProjectId;
    }
}
