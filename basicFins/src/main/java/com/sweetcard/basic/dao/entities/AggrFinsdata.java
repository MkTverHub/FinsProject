package com.sweetcard.basic.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AggrFinsdata {
    private Long id;
    private boolean lockflg;
    private String operdate;
    private Float amount;
    private String detail;
    private String finsopertype;
    private String payaccin;
    private String payaccout;
    private Integer finsarticle;
    private Integer projectid;
    private Integer finscontragent;
    private Integer requisites;


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public void setLockflg(boolean lockflg) {
        this.lockflg = lockflg;
    }

    public void setOperdate(String operdate) {
        this.operdate = operdate;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setFinsopertype(String finsopertype) {
        this.finsopertype = finsopertype;
    }

    public void setPayaccin(String payaccin) {
        this.payaccin = payaccin;
    }

    public void setPayaccout(String payaccout) {
        this.payaccout = payaccout;
    }

    public void setFinsarticle(Integer finsarticle) {
        this.finsarticle = finsarticle;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public void setFinscontragent(Integer finscontragent) {
        this.finscontragent = finscontragent;
    }

    public void setRequisites(Integer requisites) {
        this.requisites = requisites;
    }

    public boolean isLockflg() {
        return lockflg;
    }

    public String getOperdate() {
        return operdate;
    }

    public Float getAmount() {
        return amount;
    }

    public String getDetail() {
        return detail;
    }

    public String getFinsopertype() {
        return finsopertype;
    }

    public String getPayaccin() {
        return payaccin;
    }

    public String getPayaccout() {
        return payaccout;
    }

    public Integer getFinsarticle() {
        return finsarticle;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public Integer getFinscontragent() {
        return finscontragent;
    }

    public Integer getRequisites() {
        return requisites;
    }
}
