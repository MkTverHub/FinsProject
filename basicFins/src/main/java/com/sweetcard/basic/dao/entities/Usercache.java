package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Usercache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") public Integer id;
    @Column(name = "active_proj") private Integer active_proj;
    @Column(name = "login") public String login;
}
