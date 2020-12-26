package com.sweetcard.basic.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AggrContragent implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String phone_num;
    private String email_addr;
    private String type;
    private String amount_in;
    private String amount_out;
    private String balance;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getPhone_num() {return phone_num;}
    public void setPhone_num(String phone_num) {this.phone_num = phone_num;}
    public String getEmail_addr() {return email_addr;}
    public void setEmail_addr(String email_addr) {this.email_addr = email_addr;}
    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public String getAmount_in() {return amount_in;}
    public void setAmount_in(String amount_in) {this.amount_in = amount_in;}
    public String getAmount_out() {return amount_out;}
    public void setAmount_out(String amount_out) {this.amount_out = amount_out;}
    public String getBalance() {return balance;}
    public void setBalance(String balance) {this.balance = balance;}
}
