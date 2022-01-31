package com.sweetcard.basic.model;

public class PurposeForm {
    private Integer intId;
    private Integer intParRowId;
    private String strName;
    private String strDescription;
    private String strExpense;
    private String strProfit;
    private String strPurposeAction= ""; //insert/update/delete

    public Integer getId() {return intId; }
    public Integer getParRowId() {return intParRowId;}
    public String getName() {return strName;}
    public String getDescription() {return strDescription;}
    public String getExpense() {return strExpense;}
    public String getProfit() {return strProfit;}
    public String getPurposeAction() {return strPurposeAction;}

    public void setId(Integer intId) {this.intId = intId;}
    public void setParRowId(Integer intParRowId) {this.intParRowId = intParRowId;}
    public void setName(String strName) {this.strName = strName; }
    public void setDescription(String strDescription) {this.strDescription = strDescription;}
    public void setExpense(String strExpense) {this.strExpense = strExpense;}
    public void setProfit(String strProfit) {this.strProfit = strProfit;}
    public void setPurposeAction(String strPurposeAction) {this.strPurposeAction = strPurposeAction;}
}
