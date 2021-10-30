package com.sweetcard.basic.dao.repository;


import com.sweetcard.basic.dao.entities.Lov;
import com.sweetcard.basic.dao.entities.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurposeRepository extends JpaRepository<Purpose, Integer> {
    //По проекту
    @Query(value = "select * from purpose where par_row_id = :fins_project_id", nativeQuery = true)
    List<Purpose> GetAllByProject (@Param("fins_project_id") Integer finsprojectid);
}
