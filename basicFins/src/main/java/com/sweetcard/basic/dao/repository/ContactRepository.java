package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.Company;
import com.sweetcard.basic.dao.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    //Выбрать все с групировкой по Id
    List<Contact> findAllByOrderByIdAsc();

    //Выбрать все для компании
    @Query(value = "SELECT * FROM contact where par_row_id = :company_id", nativeQuery = true)
    List<Contact> GetAllByCompany(@Param("company_id") Integer company_id);

    //Получить список счетов сотрудников проекта
    @Query(value = "SELECT * from  contact where par_row_id in (select id from company where project_id = :project_id)", nativeQuery = true)
    List<Contact> GetContactFinsAccProj(@Param("project_id") Integer project_id);
}
