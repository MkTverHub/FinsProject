package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 21.02.2020.
 * Класс - модель таблицы БД в postgres, для работы интерфейса FinancedataRepository
 */
@Data
@Entity
public class Financedata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "lock_flg")
    private boolean lockflg;
    @Column(name = "oper_date")
    private Date operdate;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "detail")
    private String detail;
    @Column(name = "fins_oper_type")//Тип транзакции profit/loss/transfer
    private String finsopertype;
    @Column(name = "pay_acc_in")//Счет поступления
    private String payaccin;
    @Column(name = "pay_acc_out")//Счет списания
    private String payaccout;
    @Column(name = "fins_article")//Статья
    private String finsarticle;
    @Column(name = "project_id")
    private Integer projectid;
    @Column(name = "finscontragent")
    private String finscontragent;
    @Column(name = "requisites")
    private String requisites;
}
