package com.sweetcard.basic.dao.entities;

import javax.persistence.*;
import java.io.Serializable;

@SqlResultSetMapping(
        name = "nativeSqlResult",
        entities = @EntityResult(entityClass = AggrFinsproject.class)
)

@Entity
public class AggrFinsproject{
    private Long id;
    private String name;
    private String description;
    private String created;
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
    public String getCreated() {return created;}
    public void setCreated(String created) {this.created = created;}
    public String getAmount_in() {return amount_in;}
    public void setAmount_in(String amount_in) {this.amount_in = amount_in;}
    public String getAmount_out() {return amount_out;}
    public void setAmount_out(String amount_out) {this.amount_out = amount_out;}
    public String getBalance() {return balance;}
    public void setBalance(String balance) {this.balance = balance;}
}
