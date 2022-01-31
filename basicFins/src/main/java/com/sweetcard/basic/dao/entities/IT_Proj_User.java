package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class IT_Proj_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") public Integer id;
    @Column(name = "user_id") public Integer user_id;
    @Column(name = "project_id") public Integer project_id;
}
