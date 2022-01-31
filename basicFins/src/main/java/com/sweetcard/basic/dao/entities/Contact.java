package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private Integer id;
    @Column(name = "first_name") private String first_name;
    @Column(name = "last_name") private String last_name;
    @Column(name = "fins_acc") private String fins_acc;
    @Column(name = "par_row_id") private Integer par_row_id;
    @Column(name = "balance") private String balance;
    @Column(name = "description") private String description;

}
