package com.sweetcard.basic.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String FirstName) {this.firstName = FirstName;}
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String LastName) {this.lastName = LastName;}
    public String getEmail() {return email;}
    public void setEmail(String Email) {this.email = Email;}
    public String getPassword() {return password;}
    public void setPassword(String Password) {this.password = Password;}
}
