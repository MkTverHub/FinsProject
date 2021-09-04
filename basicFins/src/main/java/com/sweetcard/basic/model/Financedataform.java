package com.sweetcard.basic.model;

/**
 * Created by Admin on 26.02.2020.
 * Класс - модель формы, для работы метода submit формы, получения поле формы в Controller
 */
public class Financedataform {
    private String strFinsEditType = "update";//new/update/delete
    private String strFinsRecordId = "";
    private String strFinsblockflg = "";
    private String strFinsoperdate = "";
    private String strFinsoperdateUser = "";
    private String strFinsamount = "";
    private String strFinsdetail = "";
    private String strFinsOperType = "";//Тип транзакции profit/loss/transfer
    private String strPaymentAccIn = "";
    private String strPaymentAccOut = "";
    private String strFinsArticle = "";
    private String strProjectId = "";
    private String strRequisites = "";
    private String strFinsContrAgent = "";


    /*----------------GET / SET Полей--------------*/
    /*Имя get/set должно начинать с get/set + имя атрибута в html (с у четом заглавных)*/
    public String getFinsrecordid() {return strFinsRecordId;}
    public void setFinsrecordid(String FinsRecordId) {this.strFinsRecordId = FinsRecordId;}//Имя переменной в атрибутах метода (с учетом заглавных) "FinsRecordId" это имя field в html

    public String getFinsblockflg() {
        return strFinsblockflg;
    }
    public void setFinsblockflg(String Finsblockflg) {
        this.strFinsblockflg = Finsblockflg;
    }
    public String getFinsoperdate() {
        return strFinsoperdate;
    }
    public void setFinsoperdate(String Finsoperdate) {
        this.strFinsoperdate = Finsoperdate;
    }
    public String getFinsoperdateUser() {
        return strFinsoperdateUser;
    }
    public void setFinsoperdateUser(String FinsoperdateUser) {
        this.strFinsoperdateUser = FinsoperdateUser;
    }
    public String getFinsamount() {
        return strFinsamount;
    }
    public void setFinsamount(String Finsamount) {
        this.strFinsamount = Finsamount;
    }
    public String getFinsdetail() {
        return strFinsdetail;
    }
    public void setFinsdetail(String Finsdetail) {
        this.strFinsdetail = Finsdetail;
    }
    //Тип транзакции
    public void setFinsOperType(String FinsOperType) {
        this.strFinsOperType = FinsOperType;
    }
    public String getFinsOperType() {
        return strFinsOperType;
    }
    //Счет поступления
    public void setPaymentAccIn(String PaymentAccIn) {
        this.strPaymentAccIn = PaymentAccIn;
    }
    public String getPaymentAccIn() {return strPaymentAccIn;}
    //Счет списания
    public void setPaymentAccOut(String PaymentAccOut) {
        this.strPaymentAccOut = PaymentAccOut;
    }
    public String getPaymentAccOut() {
        return strPaymentAccOut;
    }
    //Статья
    public void setFinsArticle(String FinsArticle) {
        this.strFinsArticle = FinsArticle;
    }
    public String getFinsArticle() {
        return strFinsArticle;
    }
    //ID Пооекта
    public void setProjectId(String ProjectId) {
        this.strProjectId = ProjectId;
    }
    public String getProjectId() {
        return strProjectId;
    }

    public String getFinscontragent() {
        return strFinsContrAgent;
    }
    public void setFinscontragent(String Finscontragent) {
        this.strFinsContrAgent = Finscontragent;
    }
    //Реквизиты
    public String getRequisites() {
        return strRequisites;
    }
    public void setRequisites(String Requisites) {
        this.strRequisites = Requisites;
    }
    public String getFinsedittype() {
        return strFinsEditType;
    }
    public void setFinsedittype(String Finsedittype) {
        this.strFinsEditType = Finsedittype;
    }

    /*-------Другие методы----------*/
    //Чистим значения (при удалении записи)
    public void ClearFields(){
        strFinsEditType = "update";
        strFinsRecordId = "";
        strFinsamount = "";
        strFinsdetail = "";
        strFinsOperType = "";
        strPaymentAccIn = "";
        strPaymentAccOut = "";
        strFinsArticle = "";
        strProjectId = "";
        strRequisites = "";
        strFinsContrAgent = "";
    }
}
