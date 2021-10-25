package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Purpose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") public Integer id;
    @Column(name = "par_row_id") private Integer par_row_id;
    @Column(name = "name") public String name;
    @Column(name = "description") public String description;
    @Column(name = "expense") public String expense;
    @Column(name = "profit") public String profit;
}
