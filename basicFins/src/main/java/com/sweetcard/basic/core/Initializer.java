package com.sweetcard.basic.core;

import com.sweetcard.basic.config.WebSecurityConfig;
import com.sweetcard.basic.web.ReqController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Admin on 21.05.2020.
 */
public class Initializer {
    private Logger logger = LoggerFactory.getLogger(Initializer.class);
    private String strUserLoginName = "";//Логин пользователя

    //---------------------Конструктор класса-----------------------
    public Initializer(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            strUserLoginName = auth.getName();
            logger.info("Initializer -> Класс создан. LOGIN: " + strUserLoginName);
        }catch (Exception init_ix){
            logger.info("AS_ERROR -> Ошибка в конструкторе Initializer: " + init_ix);
        }
    }

    //---------------------------------GETTERS-------------------------------------------
    public String GetUserLogin(){return strUserLoginName;}


}
