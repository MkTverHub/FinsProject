package com.sweetcard.basic.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AggrPurpose {
    private Long id;
    private Integer par_row_id;
    private String name;
    private String description;
    private String expense;
    private String profit;
    private String actual;
    private String assumed_total;
    private String actual_total;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Integer getPar_row_id() { return par_row_id; }
    public void setPar_row_id(Integer par_row_id) { this.par_row_id = par_row_id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getExpense() { return expense; }
    public void setExpense(String expense) { this.expense = expense; }
    public String getProfit() { return profit; }
    public void setProfit(String profit) { this.profit = profit; }
    public String getActual() { return actual; }
    public void setActual(String actual) { this.actual = actual; }
    public String getAssumed_total() { return assumed_total; }
    public void setAssumed_total(String assumed_total) { this.assumed_total = assumed_total; }
    public String getActual_total() { return actual_total; }
    public void setActual_total(String actual_total) { this.actual_total = actual_total; }

}
