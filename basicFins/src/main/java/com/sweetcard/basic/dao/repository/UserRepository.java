package com.sweetcard.basic.dao.repository;


import com.sweetcard.basic.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    //Получить Id пользователя по логину
    @Query(value = "select id from t_user where username = :user_login", nativeQuery = true)
    Integer GetUserIdbyLogin(@Param("user_login") String user_login_in);
}
