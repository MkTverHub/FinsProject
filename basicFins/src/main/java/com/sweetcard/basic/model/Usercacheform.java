package com.sweetcard.basic.model;

public class Usercacheform {
    private Integer intId;
    private String strLogin = "";
    private Integer intActiveProject;
    private Integer intUserId;
    private String strUsercacheAction= ""; //insert/update/delete
    private String strRole = "";

    public Integer getId() {return intId;}
    public void setId(Integer Id) {this.intId = Id;}//Имя переменной в атрибутах метода (с учетом заглавных) "Id" это имя field в html
    public String getLogin() {return strLogin;}
    public void setLogin(String Login) {this.strLogin = Login;}
    public Integer getActiveProject() {return intActiveProject;}
    public void setActiveProject(Integer ActiveProject) {this.intActiveProject = ActiveProject;}
    public String getUsercacheAction() {return strUsercacheAction;}
    public void setUsercacheAction(String UsercacheAction) {this.strUsercacheAction = UsercacheAction;}
    public Integer getUserId() {return intUserId;}
    public void setUserId(Integer UserId) {this.intUserId = UserId;}
    public String getRole() {return strRole;}
    public void setRole(String Role) {this.strRole = Role;}
}
