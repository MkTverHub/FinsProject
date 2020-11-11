package com.sweetcard.basic.dao.repository;

import com.sweetcard.basic.dao.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByLastName(String lastName);

    List<Customer> findAllByOrderByIdAsc();

}
