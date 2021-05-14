package com.sweetcard.basic.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository
        extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    //Получить Id пользователя по логину
    @Query(value = "select id from app_user where email = :user_login", nativeQuery = true)
    Integer GetUserIdbyEmail(@Param("user_login") String user_login_in);

    @Query(value = "SELECT * FROM app_user where email = :user_login", nativeQuery = true)
    AppUser GetUserByEmail(@Param("user_login") String user_login_in);

    @Query(value = "SELECT * FROM app_user where parent_id = :parent_id and app_user_role = 'SUB_USER'", nativeQuery = true)
    List<AppUser> GetSubUserList (@Param("parent_id") Integer parent_id);

    @Query(value = "SELECT * FROM app_user where id = :id", nativeQuery = true)
    AppUser GetMainUser (@Param("id") Integer id);
}
