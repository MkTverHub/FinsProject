package com.sweetcard.basic.dao.repository;


import com.sweetcard.basic.dao.entities.Lov;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LovRepository extends JpaRepository<Lov, Integer> {
    //Выбрать все с групировкой по Id
    List<Lov> findAllByOrderByIdAsc();
}
