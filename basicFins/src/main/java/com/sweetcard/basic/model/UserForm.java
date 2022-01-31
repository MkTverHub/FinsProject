package com.sweetcard.basic.model;

public class UserForm {
    private Integer Id;
    private Integer parent_id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String position;
    private String email;
    private String password;
    private Boolean locked = false;
    private Boolean enabled = false;
    private String access_dt;
    private String access_status;
    private String UserAction = ""; //(insert/update/delete)

    public String getUserAction() { return UserAction; }
    public Integer getId() { return Id; }
    public Integer getParent_id() { return parent_id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMiddleName() { return middleName; }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Boolean getLocked() { return locked; }
    public Boolean getEnabled() { return enabled; }
    public String getAccess_dt() { return access_dt; }
    public String getAccess_status() { return access_status; }

    public void setUserAction(String UserAction) { this.UserAction = UserAction; }
    public void setId(Integer Id) { this.Id = Id; }
    public void setParent_id(Integer parent_id) { this.parent_id = parent_id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPosition(String position) { this.position = position; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setLocked(Boolean locked) { this.locked = locked; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public void setAccess_dt(String access_dt) { this.access_dt = access_dt; }
    public void setAccess_status(String access_status) { this.access_status = access_status; }

}
