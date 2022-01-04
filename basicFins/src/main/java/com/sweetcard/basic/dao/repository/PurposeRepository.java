package com.sweetcard.basic.dao.repository;


import com.sweetcard.basic.dao.entities.Lov;
import com.sweetcard.basic.dao.entities.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurposeRepository extends JpaRepository<Purpose, Integer> {
    String strSpecSymbol = "\\:";
    //По проекту
    @Query(value = "select id,description,name,par_row_id,ROUND(AVG(expense)"+strSpecSymbol+strSpecSymbol+"numeric,2) as expense,ROUND(AVG(profit)::numeric,2) as profit from purpose where par_row_id = :fins_project_id group by id\n", nativeQuery = true)
    List<Purpose> GetAllByProject (@Param("fins_project_id") Integer finsprojectid);
}
