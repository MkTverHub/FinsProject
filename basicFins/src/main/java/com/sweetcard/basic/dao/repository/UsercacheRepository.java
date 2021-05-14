package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.Financedata;
import com.sweetcard.basic.dao.entities.Usercache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsercacheRepository extends JpaRepository<Usercache, Integer> {
    //Выбрать все селектом по проекту
    @Query(value = "SELECT id,active_proj,login,user_id,role FROM usercache where login = :user_login", nativeQuery = true)
    Usercache GetUsercache(@Param("user_login") String UserLogin);
}
