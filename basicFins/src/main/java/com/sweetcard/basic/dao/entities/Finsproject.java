package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Ivribin on 07.03.2020.
 */
@Data
@Entity
public class Finsproject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;
    @Column(name = "name")
    public String name;
    @Column(name = "description")
    public String description;
    @Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT NOW()")
    public String created;
    //@Column(name = "clc_1")//Пример калькулируемого поля
   // private String clc_1;

}
