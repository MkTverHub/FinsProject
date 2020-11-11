package com.sweetcard.basic.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerJdbc {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void updateCustomer(Integer id)
    {
        jdbcTemplate.update("update customer set last_name = last_name || '1' where id = ?", id);
    }


}
