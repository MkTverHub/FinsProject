package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.IT_Proj_User;

import com.sweetcard.basic.dao.entities.Lov;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITblProjectUserRepository extends JpaRepository<IT_Proj_User, Integer> {
    //По пользователю + проекту

    @Query(value = "select * from it_proj_user where user_id = :user_id and project_id = :project_id", nativeQuery = true)
    List<IT_Proj_User> GetAllUserAndProject(@Param("user_id") Integer UserId, @Param("project_id") Integer ProjectId);


}
