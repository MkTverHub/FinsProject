package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Admin on 19.03.2020.
 */
@Data
@Entity
public class Contragent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "phone_num")
    private String phone_num;
    @Column(name = "email_addr")
    private String email_addr;

}
