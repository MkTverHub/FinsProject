package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    //Выбрать все с групировкой по Id
    List<Company> findAllByOrderByIdAsc();
    //Выбрать все для пользователя
    @Query(value = "SELECT * FROM company where owner_id = :owner_id", nativeQuery = true)
    List<Company> GetAllByOwner(@Param("owner_id") String owner_company);
}
