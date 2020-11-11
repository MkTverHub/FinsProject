package com.sweetcard.basic.model;

/**
 * Created by Ivribin on 09.03.2020.
 */
public class Finsprojectform {
    private String strFinsProjectId = "";
    private String strFinsProjectName = "";
    private String strFinsProjectDescription = "";

    /*----------------GET / SET Полей--------------*/
    /*Имя get/set должно начинать с get/set + имя атрибута в html (с у четом заглавных)*/
    public String getFinsprojectid() {return strFinsProjectId;}
    public void setFinsprojectid(String Finsprojectid) {this.strFinsProjectId = Finsprojectid;}//Имя переменной в атрибутах метода (с учетом заглавных) "Finsprojectid" это имя field в html

    public String getFinsprojectname() {
        return strFinsProjectName;
    }
    public void setFinsprojectname(String Finsprojectname) {
        this.strFinsProjectName = Finsprojectname;
    }
    public String getFinsprojectdescription() {
        return strFinsProjectDescription;
    }
    public void setFinsprojectdescription(String Finsprojectdescription) {
        this.strFinsProjectDescription = Finsprojectdescription;
    }
}
