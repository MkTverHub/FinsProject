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
    private String expense_probable;
    private String expense_real;
    private String expense_total;
    private String profit_probable;
    private String profit_real;
    private String profit_total;
    private String balance_probable;
    private String balance_real;
    private String balance_total;

    private String total_probable;
    private String total_real;

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

    public String getExpense_probable() { return expense_probable; }
    public String getExpense_real() { return expense_real; }
    public String getExpense_total() { return expense_total; }
    public String getProfit_probable() { return profit_probable; }
    public String getProfit_real() { return profit_real; }
    public String getProfit_total() { return profit_total; }
    public String getBalance_probable() { return balance_probable; }
    public String getBalance_real() { return balance_real; }
    public String getBalance_total() { return balance_total; }
    public String getTotal_probable() { return total_probable; }
    public String getTotal_real() { return total_real; }

    public void setExpense_probable(String expense_probable) { this.expense_probable = expense_probable; }
    public void setExpense_real(String expense_real) { this.expense_real = expense_real; }
    public void setExpense_total(String expense_total) { this.expense_total = expense_total; }
    public void setProfit_probable(String profit_probable) { this.profit_probable = profit_probable; }
    public void setProfit_real(String profit_real) { this.profit_real = profit_real; }
    public void setProfit_total(String profit_total) { this.profit_total = profit_total; }
    public void setBalance_probable(String balance_probable) { this.balance_probable = balance_probable; }
    public void setBalance_real(String balance_real) { this.balance_real = balance_real; }
    public void setBalance_total(String balance_total) { this.balance_total = balance_total; }
    public void setTotal_probable(String total_probable) { this.total_probable = total_probable; }
    public void setTotal_real(String total_real) { this.total_real = total_real; }
}
