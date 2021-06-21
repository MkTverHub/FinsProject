package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrCompany;
import com.sweetcard.basic.dao.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataCompanyRepository extends JpaRepository<AggrCompany, Integer> {
    //Выбрать все для пользователя
    @Query(value = "select id,fins_acc,full_name,inn,kpp,name,owner_id,project_id from company where owner_id = :owner_id", nativeQuery = true)
    List<AggrCompany> GetAllByOwner(@Param("owner_id") String owner_company);
}
