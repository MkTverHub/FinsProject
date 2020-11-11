package com.sweetcard.basic.model;

public class LovForm {
    private String strLovId = "";
    private String strLovParRowId = "";
    private String strLovValue = "";
    private String strLovDescription = "";
    private String strLovOptions = "";
    private String strLovType = "";
    private String strLovAction= ""; //insert/update/delete

    public String getLovId() {return strLovId;}
    public void setLovId(String LovId) {this.strLovId = LovId;}//Имя переменной в атрибутах метода (с учетом заглавных) "Lovid" это имя field в html
    public String getLovParRowId() {return strLovParRowId;}
    public void setLovParRowId(String LovParRowId) {this.strLovParRowId = LovParRowId;}
    public String getLovVal() {return strLovValue;}
    public void setLovVal(String LovValue) {this.strLovValue = LovValue;}
    public String getLovAction() {return strLovAction;}
    public void setLovAction(String LovAction) {this.strLovAction = LovAction;}
    public String getLovDescription() {return strLovDescription;}
    public void setLovDescription(String LovDescription) {this.strLovDescription = LovDescription;}
    public String getLovOptions() {return strLovOptions;}
    public void setLovOptions(String LovOptions) {this.strLovOptions = LovOptions;}
    public String getLovType() {return strLovType;}
    public void setLovType(String LovType) {this.strLovType = LovType;}
}
