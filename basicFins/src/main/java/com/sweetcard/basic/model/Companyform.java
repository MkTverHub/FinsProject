package com.sweetcard.basic.model;

/**
 * Created by Ivribin on 09.03.2020.
 */

public class Companyform {
    private String strCompanyId = "";
    private String strCompanyName = "";
    private String strCompanyFullName = "";
    private String strCompanyINN = "";
    private String strCompanyKPP = "";
    private String strCompanyFinsAcc = "";
    private String strCompanyOwner = "";
    private String strCompanyProjectId = "";
    private String strCompanyAction= ""; //insert/update/delete

    /*----------------GET / SET Полей--------------*/
    /*Имя get/set должно начинать с get/set + имя атрибута в html (с у четом заглавных)*/
    public String getCompanyId() {return strCompanyId;}
    public void setCompanyId(String CompanyId) {this.strCompanyId = CompanyId;}//Имя переменной в атрибутах метода (с учетом заглавных) "CompanyId" это имя field в html

    public String getCompanyAction() {return strCompanyAction;}
    public void setCompanyAction(String CompanyAction) {this.strCompanyAction = CompanyAction;}
    public String getCompanyName() {return strCompanyName;}
    public void setCompanyName(String CompanyName) {this.strCompanyName = CompanyName;}
    public String getCompanyFullName() {return strCompanyFullName;}
    public void setCompanyFullName(String CompanyFullName) {this.strCompanyFullName = CompanyFullName;}
    public String getCompanyINN() {return strCompanyINN;}
    public void setCompanyINN(String CompanyINN) {this.strCompanyINN = CompanyINN;}
    public String getCompanyKPP() {return strCompanyKPP;}
    public void setCompanyKPP(String CompanyKPP) {this.strCompanyKPP = CompanyKPP;}
    public String getCompanyFinsAcc() {return strCompanyFinsAcc;}
    public void setCompanyFinsAcc(String CompanyKPP) {this.strCompanyFinsAcc = CompanyKPP;}
    public String getCompanyOwner() {return strCompanyOwner;}
    public void setCompanyOwner(String CompanyOwner) {this.strCompanyOwner = CompanyOwner;}
    public void setCompanyProjectId(String CompanyProjectId) {this.strCompanyProjectId = CompanyProjectId;}
    public String getCompanyProjectId() {return strCompanyProjectId;}

}
