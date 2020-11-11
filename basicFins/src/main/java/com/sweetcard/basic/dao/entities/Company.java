package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "full_name")
    private String full_name;
    @Column(name = "inn")
    private String inn;
    @Column(name = "kpp")
    private String kpp;
    @Column(name = "fins_acc")
    private String fins_acc;
    @Column(name = "owner_id")
    private String owner_id;
    @Column(name = "project_id")
    private String project_id;
}
