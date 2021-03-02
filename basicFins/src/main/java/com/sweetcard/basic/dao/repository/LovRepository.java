package com.sweetcard.basic.dao.repository;


import com.sweetcard.basic.dao.entities.Contragent;
import com.sweetcard.basic.dao.entities.Lov;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LovRepository extends JpaRepository<Lov, Integer> {
    //Выбрать все с групировкой по Id
    List<Lov> findAllByOrderByIdAsc();

    //По проекту
    @Query(value = "select * from lov where project_id = :fins_project_id", nativeQuery = true)
    List<Lov> GetAllByProject (@Param("fins_project_id") Integer finsprojectid);
}
