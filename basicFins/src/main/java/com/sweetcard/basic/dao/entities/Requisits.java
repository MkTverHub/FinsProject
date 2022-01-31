package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Admin on 22.03.2020.
 */
@Data
@Entity
public class Requisits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;
    @Column(name = "par_row_id")
    public Integer par_row_id;
    @Column(name = "name")
    public String name;
    @Column(name = "description")
    private String description;
    @Column(name = "inn")
    private String inn;
    @Column(name = "kpp")
    private String kpp;
    @Column(name = "fins_acc")
    private String fins_acc;
    @Column(name = "bik")
    private String bik;
    @Column(name = "bank_name")
    private String bank_name;
    @Column(name = "crsp_acc")
    private String crsp_acc;
    @Column(name = "addr_index")
    private String addr_index;
    @Column(name = "addr_city")
    private String addr_city;
    @Column(name = "addr_string")
    private String addr_string;
    @Column(name = "phone_num")
    private String phone_num;
    @Column(name = "email_addr")
    private String email_addr;
    @Column(name = "web_site")
    private String web_site;
    @Column(name = "card_num")
    private String card_num;
    @Column(name = "main_flg")
    private Boolean main_flg;
}
