package com.sweetcard.basic.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AggrReport {
    private Long id;
    private String rep_lable;
    private String rep_value;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getRep_lable() {return rep_lable;}
    public void setRep_lable(String rep_lable) {this.rep_lable = rep_lable;}
    public String getRep_value() {return rep_value;}
    public void setRep_value(String rep_value) {this.rep_value = rep_value;}
}
