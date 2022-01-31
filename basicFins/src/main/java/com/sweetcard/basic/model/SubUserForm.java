package com.sweetcard.basic.model;

import com.sweetcard.basic.appuser.AppUserRole;


public class SubUserForm {
    private Integer intId;
    private Integer intParentId;
    private String strFirstName;
    private String strLastName;
    private String strMiddleName;
    private String strEmail;
    private String strPassword;
    private String strAppUserRole;
    private Boolean blLocked = false;
    private Boolean blEnabled = false;
    private String strPosition;
    private String strPhone;
    private String strDBOperation;

    public Integer getId() {return intId;}
    public Integer getParentId() {return intParentId;}
    public String getFirstName() {return strFirstName;}
    public String getLastName() {return strLastName;}
    public String getMiddleName() {return strMiddleName;}
    public String getEmail() {return strEmail;}
    public String getPassword() {return strPassword;}
    public String getAppUserRole() {return strAppUserRole;}
    public Boolean getLocked() {return blLocked;}
    public Boolean getEnabled() {return blEnabled;}
    public String getPosition() {return strPosition;}
    public String getPhone() {return strPhone;}
    public String getDBOperation() {return strDBOperation;}

    public void setId(Integer intId) {this.intId = intId;}
    public void setParentId(Integer intParentId) {this.intParentId = intParentId;}
    public void setFirstName(String strFirstName) {this.strFirstName = strFirstName;}
    public void setLastName(String strLastName) {this.strLastName = strLastName;}
    public void setMiddleName(String strMiddleName) {this.strMiddleName = strMiddleName;}
    public void setEmail(String strEmail) {this.strEmail = strEmail;}
    public void setPassword(String strPassword) {this.strPassword = strPassword;}
    public void setAppUserRole(String strAppUserRole) {this.strAppUserRole = strAppUserRole;}
    public void setLocked(Boolean blLocked) {this.blLocked = blLocked;}
    public void setEnabled(Boolean blEnabled) {this.blEnabled = blEnabled;}
    public void setPosition(String strPosition) {this.strPosition = strPosition;}
    public void setPhone(String strPhone) {this.strPhone = strPhone;}
    public void setDBOperation(String strDBOperation) {this.strDBOperation = strDBOperation;}
}
