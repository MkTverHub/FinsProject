package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.Companyform;
import com.sweetcard.basic.model.Contactform;
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
public class ContactJdbc {
    private Logger logger = LoggerFactory.getLogger(ContactJdbc.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer ContactAction(Contactform contactform) {
        try{
            Integer intResult = 0;
            String strOperationType = contactform.getContactAction();
            logger.info("ContactJdbc.ContactAction: " + strOperationType);

            switch(strOperationType){
                case "update" : {
                    Integer intContactId = Integer.parseInt(contactform.getContactId());
                    logger.info("ContactJdbc.UpdateContact -> " + intContactId);
                    jdbcTemplate.update("update contact set first_name = ?, last_name = ?, fins_acc = ?, balance = ? where id = ?",
                            contactform.getContactFirstName() ,contactform.getContactLastName(), contactform.getContactFinsAcc(), contactform.getContactBalance(), intContactId);


                }break;
                case "insert" : {
                    //Создание записи
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("INSERT INTO contact (first_name , last_name, fins_acc, par_row_id, balance) VALUES (?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                            statement.setString(1, contactform.getContactFirstName());
                            statement.setString(2, contactform.getContactLastName());
                            statement.setString(3, contactform.getContactFinsAcc());
                            statement.setInt(4, contactform.getContactParRowId());
                            statement.setInt(5, contactform.getContactBalance());
                            return statement;
                        }
                    }, holder);

                    String strCompanytId = holder.getKeyList().get(0).get("id").toString();
                    logger.info("ContactJdbc.NewContact-> Contact Id: " + strCompanytId);
                    intResult = Integer.parseInt(strCompanytId);
                }break;
                case "delete" : {
                    Integer intContactId = Integer.parseInt(contactform.getContactId());
                    logger.info("ContactJdbc.DeleteContact -> " + intContactId);
                    jdbcTemplate.update("delete from contact where id = ?", intContactId);
                }break;

                default:{
                    logger.info("ContactJdbc.ContactAction: Неизвестная операция" + strOperationType);
                }
            }
            return intResult;

        }catch (Exception exp_sql){
            logger.info("ContactJdbc.ContactAction -> ERROR: " + exp_sql);
            return null;
        }
    }
}
