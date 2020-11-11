package com.sweetcard.basic.dao.jdbc;

import com.sweetcard.basic.dao.repository.FinsprojectRepository;
import com.sweetcard.basic.dao.repository.UserRepository;
import com.sweetcard.basic.model.Financedataform;
import com.sweetcard.basic.model.Finsprojectform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Admin on 09.03.2020.
 */
@Component
public class FinsprojectJdbc {
    private Logger logger = LoggerFactory.getLogger(FinancedataJdbc.class);
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserRepository userRepository;


    //Создание записи
    public Integer NewFinsProject(Finsprojectform finsprojectform) {
        try{
            //Создание записи
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("INSERT INTO finsproject (name , description) VALUES (?,?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, finsprojectform.getFinsprojectname());
                    statement.setString(2, finsprojectform.getFinsprojectdescription());
                    return statement;
                }
            }, holder);

            String strProjectId = holder.getKeyList().get(0).get("id").toString();//Получение Id созданной записи
            Integer intProjectId = Integer.parseInt(strProjectId);
            String strUserLogin = GetUserLogin();//Получить логин пользователя
            Integer intUserId = GetUserId(strUserLogin);//Получение Id пользователя

            //Добавить запись в таблицу пересечени
            jdbcTemplate.update("insert into it_proj_user (user_id, project_id) values (?,?)", intUserId ,intProjectId);

            logger.info("FinsprojectJdbc.NewFinsProject -> Project Id: " + strProjectId);
            return intProjectId;

        }catch (Exception exp_sql){
            logger.info("FinsprojectJdbc.NewFinsProject -> ERROR: " + exp_sql);
            return null;
        }
    }

    //Удаление проекта
    public void DeleteFinsProject(Finsprojectform finsprojectform){
        try{
            Integer intRecordId = Integer.parseInt(finsprojectform.getFinsprojectid());
            String strUserLogin = GetUserLogin();//Получить логин пользователя
            Integer intUserId = GetUserId(strUserLogin);//Получение Id пользователя
            if(intRecordId > 0) {
                jdbcTemplate.update("delete from finsproject where id = ? and id in (select project_id from it_proj_user where user_id = ?)", intRecordId, intUserId);
                logger.info("FinsprojectJdbc.DeleteFinsProject");
            }

        }catch (Exception exp_sql){
            logger.info("FinsprojectJdbc.DeleteFinsProject -> ERROR: " + exp_sql);
        }
    }

    //Обновить проект
    public void EditFinsProject(Finsprojectform finsprojectform){
        try{
            String strUserLogin = GetUserLogin();//Получить логин пользователя
            Integer intUserId = GetUserId(strUserLogin);//Получение Id пользователя
            Integer intRecordId = Integer.parseInt(finsprojectform.getFinsprojectid());
            String strFinsProjectName = finsprojectform.getFinsprojectname();
            String strFinsProjectDescription = finsprojectform.getFinsprojectdescription();
            if(intRecordId > 0) {
                //Создание записи
                jdbcTemplate.update("update finsproject set name = ?, description = ? where id = ? and id in (select project_id from it_proj_user where user_id = ?)",strFinsProjectName,strFinsProjectDescription,intRecordId,intUserId);
                logger.info("FinsprojectJdbc.EditFinsProject");
            }

        }catch (Exception exp_sql){
            logger.info("FinsprojectJdbc.EditFinsProject -> ERROR: " + exp_sql);
        }
    }

    //Получить логин пользователя
    private String GetUserLogin(){
        String strUserName = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            strUserName = ((UserDetails)principal).getUsername();
        } else {
            strUserName = principal.toString();
        }
        return strUserName;
    }

    //Получить Id пользователя по логину
    private Integer GetUserId(String UserLogin){
        return userRepository.GetUserIdbyLogin(UserLogin);
    }
}
