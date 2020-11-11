package com.sweetcard.basic.dao.repository;


import com.sweetcard.basic.dao.entities.Financedata;
import com.sweetcard.basic.dao.entities.Requisits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Admin on 22.03.2020.
 */
public interface RequisitRepository extends JpaRepository<Requisits, Integer> {

        //Выбрать все с групировкой по Id
        List<Requisits> findAllByOrderByIdAsc ();

        //Выбрать все селектом по контрагенту
        @Query(value = "SELECT * FROM requisits where par_row_id = :contragentid", nativeQuery = true)
        List<Requisits> GetAllByContragent (@Param("contragentid") Integer contragentid);

}
