package com.sweetcard.basic.model;

/**
 * Created by Admin on 19.03.2020.
 */
public class Contragentform {
    private String strContragentId = "";
    private String strContragentName = "";
    private String strContragentDescription = "";
    private String strContragentPhone = "";
    private String strContragentEmail = "";
    private String strContragentaction = ""; //Создать / обновить / удалить (insert/update/delete)

    /*----------------GET / SET Полей--------------*/
    /*Имя get/set должно начинать с get/set + имя атрибута в html (с у четом заглавных)*/
    public String getContragentid() {return strContragentId;}
    public void setContragentid(String Contragenid) {this.strContragentId = Contragenid;}//Имя переменной в атрибутах метода (с учетом заглавных) "Contragenid" это имя field в html

    public String getContragentname() {
        return strContragentName;
    }
    public void setContragentname(String Contragentname) {
        this.strContragentName = Contragentname;
    }
    public String getContragentdescription() {
        return strContragentDescription;
    }
    public void setContragentdescription(String Contragentdescription) {this.strContragentDescription = Contragentdescription;}
    public String getContragentphone() {
        return strContragentPhone;
    }
    public void setContragentphone(String Contragentphone) {
        this.strContragentPhone = Contragentphone;
    }
    public String getContragentemail() {return strContragentEmail;}
    public void setContragentemail(String Contragentemail) {this.strContragentEmail = Contragentemail;}

    public String getContragentaction() {return strContragentaction;}
    public void setContragentaction(String Contragentaction) {this.strContragentaction = Contragentaction;}
}
