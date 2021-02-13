package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.Financedata;
import com.sweetcard.basic.dao.entities.Finsproject;
import com.sweetcard.basic.web.ReqController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Admin on 07.03.2020.
 */
public interface FinsprojectRepository extends JpaRepository<Finsproject, Integer> {

    //Выбрать все с групировкой по Id
    List<Finsproject> findAllByOrderByIdAsc();

    //Последний проект
    @Query(value = "select max(id) from finsproject", nativeQuery = true)
    String GetMaxId();

    //Калькулируемые поля
    //Выбрать все селектом
    @Query(value = "select t1.id, t1.name, t1.description, t1.name || ' ' || t1.id as clc_1 from finsproject t1", nativeQuery = true)
    List<Finsproject> GetAllClc();

    //Выбрать все проекты пользователя
    @Query(value = "select t1.id, t1.name, t1.description from finsproject t1 where t1.id in (select project_id from it_proj_user where user_id = (select id from app_user where email = :user_login))", nativeQuery = true)
    List<Finsproject> GetAllUserProjects(@Param("user_login") String user_login_in);

    //Обновить проект

}
