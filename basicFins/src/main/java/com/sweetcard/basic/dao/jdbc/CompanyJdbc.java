package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.model.Companyform;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class CompanyJdbc {
    private Logger logger = LoggerFactory.getLogger(CompanyJdbc.class);
    private Integer intActivProjectId = null;//Id текущего проекта
    @Autowired JdbcTemplate jdbcTemplate;

    //Создание записи
    public Integer CompanyAction(Companyform companyform) {
        try{
            Integer intResult = 0;
            String strOperationType = companyform.getCompanyAction();
            logger.info("CompanyJdbc.CompanyAction: " + strOperationType);

            switch(strOperationType){
                case "update" : {
                    logger.info("CompanyJdbc.UpdateCompany");
                    Integer intCompanyId = Integer.parseInt(companyform.getCompanyId());
                    logger.info("CompanyJdbc.Update: Id = " + intCompanyId);
                    jdbcTemplate.update("update company set name = ?, full_name = ?, inn = ?, kpp = ?, fins_acc = ?, project_id = ? where id = ?",
                            companyform.getCompanyName() ,companyform.getCompanyFullName(), companyform.getCompanyINN(), companyform.getCompanyKPP(), companyform.getCompanyFinsAcc(), intActivProjectId, intCompanyId);

                }break;
                case "insert" : {
                    //Создание записи
                    GeneratedKeyHolder holder = new GeneratedKeyHolder();
                    jdbcTemplate.update(new PreparedStatementCreator() {
                        @Override
                        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement statement = con.prepareStatement("INSERT INTO company (name , full_name, inn, kpp, fins_acc, owner_id, project_id) VALUES (?,?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
                            statement.setString(1, companyform.getCompanyName());
                            statement.setString(2, companyform.getCompanyFullName());
                            statement.setString(3, companyform.getCompanyINN());
                            statement.setString(4, companyform.getCompanyKPP());
                            statement.setString(5, companyform.getCompanyFinsAcc());
                            statement.setString(6, companyform.getCompanyOwner());
                            statement.setInt(7, intActivProjectId);
                            return statement;
                        }
                    }, holder);

                    String strCompanytId = holder.getKeyList().get(0).get("id").toString();
                    logger.info("CompanyJdbc.NewCompany -> Company Id: " + strCompanytId);
                    intResult = Integer.parseInt(strCompanytId);
                }break;
                case "delete" : {
                    Integer intCompanyId = Integer.parseInt(companyform.getCompanyId());
                    logger.info("CompanyJdbc.DeleteCompany -> " + intCompanyId);
                    jdbcTemplate.update("delete from company where id = ?", intCompanyId);
                }break;
                case "set_project" :{
                    Integer intCompanyId = Integer.parseInt(companyform.getCompanyId());
                    Integer intProjectId = Integer.parseInt(companyform.getCompanyProjectId());
                    logger.info("CompanyJdbc.set_project -> " + intCompanyId);
                    jdbcTemplate.update("update company set project_id = ? where id = ?", intProjectId, intCompanyId);
                }break;

                default:{
                    logger.info("CompanyJdbc.CompanyAction: Неизвестная операция" + strOperationType);
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
