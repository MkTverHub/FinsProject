package com.sweetcard.basic.model;

/**
 * Created by Admin on 22.03.2020.
 */
public class Cntragntreqform {
    private String strReqId = "";
    private String strContragentId = "";
    private String strReqName = "";
    private String strReqDescription = "";
    private String strReqINN = "";
    private String strReqKPP = "";
    private String strReqFinsAcc = "";
    private String strReqBIK = "";
    private String strReqBankName = "";
    private String strReqCrspAcc = "";
    private String strReqAddrIndex = "";
    private String strReqAddrCity = "";
    private String strReqAddrString = "";
    private String strReqPhoneNum = "";
    private String strReqEmail = "";
    private String strReqWebSite = "";
    private String strCardNumber = "";
    private String strReqAction = ""; //Создать / обновить / удалить (insert/update/delete)


    /*----------------GET / SET Полей--------------*/
    /*Имя get/set должно начинать с get/set + имя атрибута в html (с у четом заглавных)*/
    public String getReqId() {return strReqId;}
    public void setReqId(String ReqId) {this.strReqId = ReqId;}//Имя переменной в атрибутах метода (с учетом заглавных) "ReqId" это имя field в html
    public String getContragentId() {return strContragentId;}
    public void setContragentId(String ContragentId) {this.strContragentId = ContragentId;}
    public String getReqName () {return strReqName;}
    public void setReqName (String ReqName) {this.strReqName = ReqName;}
    public String getReqDescription () {return strReqDescription;}
    public void setReqDescription (String ReqDescription) {this.strReqDescription = ReqDescription ;}
    public String getReqINN () {return strReqINN;}
    public void setReqINN (String ReqINN) {this.strReqINN = ReqINN;}
    public String getReqKPP () {return strReqKPP;}
    public void setReqKPP (String ReqKPP) {this.strReqKPP = ReqKPP;}
    public String getReqFinsAcc () {return strReqFinsAcc;}
    public void setReqFinsAcc (String ReqFinsAcc) {this.strReqFinsAcc = ReqFinsAcc;}
    public String getReqBIK () {return strReqBIK;}
    public void setReqBIK (String ReqBIK) {this.strReqBIK = ReqBIK;}
    public String getReqBankName () {return strReqBankName;}
    public void setReqBankName (String ReqBankName) {this.strReqBankName = ReqBankName;}
    public String getReqCrspAcc () {return strReqCrspAcc;}
    public void setReqCrspAcc (String ReqCrspAcc) {this.strReqCrspAcc = ReqCrspAcc;}
    public String getReqAddrIndex () {return strReqAddrIndex;}
    public void setReqAddrIndex (String ReqAddrIndex) {this.strReqAddrIndex = ReqAddrIndex;}
    public String getReqAddrCity () {return strReqAddrCity;}
    public void setReqAddrCity (String ReqAddrCity) {this.strReqAddrCity = ReqAddrCity;}
    public String getReqAddrString () {return strReqAddrString;}
    public void setReqAddrString (String ReqAddrString) {this.strReqAddrString = ReqAddrString;}
    public String getReqPhoneNum () {return strReqPhoneNum;}
    public void setReqPhoneNum (String ReqPhoneNum) {this.strReqPhoneNum = ReqPhoneNum;}
    public String getReqEmail () {return strReqEmail;}
    public void setReqEmail (String ReqEmail) {this.strReqEmail = ReqEmail;}
    public String getReqWebSite () {return strReqWebSite;}
    public void setReqWebSite (String ReqWebSite) {this.strReqWebSite = ReqWebSite;}
    public String getCardNumber () {return strCardNumber;}
    public void setCardNumber (String CardNumber) {this.strCardNumber = CardNumber;}

    public String getReqaction() {return strReqAction;}
    public void setReqaction(String Reqaction) {this.strReqAction = Reqaction;}

}
