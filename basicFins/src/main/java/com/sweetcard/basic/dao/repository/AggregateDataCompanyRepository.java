package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrCompany;
import com.sweetcard.basic.dao.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataCompanyRepository extends JpaRepository<AggrCompany, Integer> {
    //Выбрать все для пользователя
    @Query(value = "select cmp.id,cmp.fins_acc,cmp.full_name,cmp.inn,cmp.kpp,cmp.name,cmp.owner_id,cmp.project_id,cnt.employee_count from company cmp left join (select count(id) employee_count, par_row_id from contact group by par_row_id) cnt on cmp.id = cnt.par_row_id where cmp.owner_id = :owner_id", nativeQuery = true)
    List<AggrCompany> GetAllByOwner(@Param("owner_id") String owner_company);
}
