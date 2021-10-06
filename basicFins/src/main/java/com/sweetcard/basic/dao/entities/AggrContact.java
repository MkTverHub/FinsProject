package com.sweetcard.basic.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AggrContact implements Serializable {
    private Long id;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)

    private String first_name;
    private String last_name;
    private String fins_acc;
    private Integer par_row_id;
    private String balance;
    private String description;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getFirst_name() {return first_name;}
    public String getLast_name() {return last_name;}
    public String getFins_acc() {return fins_acc;}
    public Integer getPar_row_id() {return par_row_id;}
    public String getBalance() {return balance;}
    public String getDescription() {return description;}

    public void setFirst_name(String first_name) {this.first_name = first_name;}
    public void setLast_name(String last_name) {this.last_name = last_name;}
    public void setFins_acc(String fins_acc) {this.fins_acc = fins_acc;}
    public void setPar_row_id(Integer par_row_id) {this.par_row_id = par_row_id;}
    public void setBalance(String balance) {this.balance = balance;}
    public void setDescription(String description) {this.description = description;}
}
