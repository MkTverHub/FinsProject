package com.sweetcard.basic.dao.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AggrCompany {
    private Long id;
    private String name;
    private String full_name;
    private String inn;
    private String kpp;
    private String fins_acc;
    private String owner_id;
    private String project_id;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getFull_name() {return full_name;}
    public void setFull_name(String full_name) {this.full_name = full_name;}

    public String getInn() {return inn;}
    public void setInn(String inn) {this.inn = inn;}

    public String getKpp() {return kpp;}
    public void setKpp(String kpp) {this.kpp = kpp;}

    public String getFins_acc() {return fins_acc;}
    public void setFins_acc(String fins_acc) {this.fins_acc = fins_acc;}

    public String getOwner_id() {return owner_id;}
    public void setOwner_id(String owner_id) {this.owner_id = owner_id;}

    public String getProject_id() {return project_id;}
    public void setProject_id(String project_id) {this.project_id = project_id; }

}
