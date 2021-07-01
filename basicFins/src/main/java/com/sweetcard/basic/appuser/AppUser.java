package com.sweetcard.basic.appuser;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Data
@Entity
public class AppUser implements UserDetails{
    @SequenceGenerator(
            name = "app_user_sequence",
            sequenceName = "app_user_sequence",
            allocationSize = 1
    )

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "app_user_sequence")
    */

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") public Integer id;


    //private Long id;
    @Transient
    private Date date = new Date();
    @Transient
    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.mm.yyyy hh:mm:ss");

    private Long parent_id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String position;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked = false;
    private Boolean enabled = false;
    private String access_dt = formatForDateNow.format(date);
    private String access_status = "demo";


    public AppUser(String firstName,
                   String lastName,
                   String email,
                   String password,
                   AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public  String getLastName(){
        return lastName;
    }
    public String getMiddleName() { return middleName; }
    public Long getParent_id() {return parent_id;}
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public AppUserRole getRole() { return appUserRole; }

    public void setParent_id(Long parent_id) {this.parent_id = parent_id;}

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getAccess_dt() {return access_dt; }
    public void setAccess_dt(String access_dt) {this.access_dt = access_dt; }

    public String getAccess_status() {return access_status;}
    public void setAccess_status(String access_status) {this.access_status = access_status;}
}
