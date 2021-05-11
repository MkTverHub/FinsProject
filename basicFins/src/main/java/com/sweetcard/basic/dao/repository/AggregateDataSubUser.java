package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.appuser.AppUser;
import com.sweetcard.basic.dao.entities.AggrSubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataSubUser extends JpaRepository<AggrSubUser, Integer> {
    @Query(value = "SELECT * FROM app_user where parent_id = :parent_id and app_user_role = 'SUB_USER'", nativeQuery = true)
    List<AggrSubUser> GetSubUserList (@Param("parent_id") Integer parent_id);

    @Query(value = "SELECT count(id) as sub_user_count FROM app_user where parent_id = :parent_id and app_user_role = 'SUB_USER' group by id", nativeQuery = true)
    Integer GetSubUserCount (@Param("parent_id") Integer parent_id);
}
