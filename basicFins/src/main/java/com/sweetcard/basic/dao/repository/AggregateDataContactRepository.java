package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.AggrContact;
import com.sweetcard.basic.dao.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AggregateDataContactRepository extends JpaRepository<AggrContact,Integer> {

    //Выбрать все для компании
    @Query(value = "SELECT * FROM contact", nativeQuery = true)
    List<AggrContact> GetAll();
}
