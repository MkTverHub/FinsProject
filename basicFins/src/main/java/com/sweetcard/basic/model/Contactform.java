package com.sweetcard.basic.model;

public class Contactform {
    private String strContactId = "";
    private String strContactFirstName = "";
    private String strContactLastName = "";
    private String strContactFinsAcc = "";
    private Integer intContactParRowId;
    private Integer intContactBalance;
    private String strContactDescription = "";
    private String strContactAction= ""; //insert/update/delete

    /*----------------GET / SET Полей--------------*/
    /*Имя get/set должно начинать с get/set + имя атрибута в html (с у четом заглавных)*/
    public String getContactId() {return strContactId;}
    public void setContactId(String ContactId) {this.strContactId = ContactId;}//Имя переменной в атрибутах метода (с учетом заглавных) "CompanyId" это имя field в html

    public String getContactFirstName() {return strContactFirstName;}
    public void setContactFirstName(String ContactFirstName) {this.strContactFirstName = ContactFirstName;}
    public String getContactLastName() {return strContactLastName;}
    public void setContactLastName(String ContactLastName) {this.strContactLastName = ContactLastName;}
    public String getContactFinsAcc() {return strContactFinsAcc;}
    public void setContactFinsAcc(String ContactFinsAcc) {this.strContactFinsAcc = ContactFinsAcc;}
    public Integer getContactParRowId() {return intContactParRowId;}
    public void setContactParRowId(Integer ContactParRowId) {this.intContactParRowId = ContactParRowId;}
    public Integer getContactBalance() {return intContactBalance;}
    public void setContactBalance(Integer ContactBalance) {this.intContactBalance = ContactBalance;}
    public String getContactAction() {return strContactAction;}
    public void setContactAction(String ContactAction) {this.strContactAction = ContactAction;}
    public String getContactDescription() {return strContactDescription;}
    public void setContactDescription(String strContactDescription) {this.strContactDescription = strContactDescription;}
}
