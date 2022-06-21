package com.sweetcard.basic.dao.entities;



import javax.persistence.*;
import java.io.Serializable;


@Entity
public class AggrSubUser implements Serializable {
    private Long id;
    private Long parent_id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String position;
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Long getParent_id() {return parent_id;}
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getMiddleName() { return middleName; }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public String getEmail() { return email; }

    public void setParent_id(Long parent_id) { this.parent_id = parent_id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPosition(String position) { this.position = position; }
    public void setEmail(String email) { this.email = email; }
}
