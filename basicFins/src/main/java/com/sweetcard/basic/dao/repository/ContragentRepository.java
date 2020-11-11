package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.Contragent;
import com.sweetcard.basic.dao.entities.Finsproject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Admin on 19.03.2020.
 */
public interface ContragentRepository extends JpaRepository<Contragent, Integer> {
    //Последний контрагент
    @Query(value = "select max(id) from contragent", nativeQuery = true)
    Integer GetMaxId();

    //Выбрать все с групировкой по Id
    List<Contragent> findAllByOrderByIdAsc();
}
