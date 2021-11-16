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
    private String operdate_user;
    private String oper_login_user;
    private Float amount;
    private String detail;
    private String finsopertype;
    private String payaccin;
    private String payaccin_name;
    private String payaccin_position;
    private String payaccout;
    private String payaccout_name;
    private String payaccout_position;
    private Integer finsarticle;
    private Integer projectid;
    private Integer finscontragent;
    private Integer requisites;
    private String requisites_name;
    private String contragent_name;
    private String article_name;
    private Integer purpose_id;
    private Integer purpose_name;

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
    public void setOperdate_user(String operdate_user) { this.operdate_user = operdate_user; }
    public void setOper_login_user(String oper_login_user) {this.oper_login_user = oper_login_user;}
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
    public void setPayaccin_name(String payaccin_name) {this.payaccin_name = payaccin_name;}
    public void setPayaccin_position(String payaccin_position) {this.payaccin_position = payaccin_position;}
    public void setPayaccout(String payaccout) {
        this.payaccout = payaccout;
    }
    public void setPayaccout_name(String payaccout_name) {this.payaccout_name = payaccout_name;}
    public void setPayaccout_position(String payaccout_position) {this.payaccout_position = payaccout_position;}
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
    public void setRequisites_name(String requisites_name) {
        this.requisites_name = requisites_name;
    }
    public void setContragent_name(String contragent_name) {
        this.contragent_name = contragent_name;
    }
    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }
    public void setPurpose_id(Integer purpose_id) { this.purpose_id = purpose_id; }
    public void setPurpose_name(Integer purpose_name) { this.purpose_name = purpose_name; }

    public boolean isLockflg() {
        return lockflg;
    }
    public String getOperdate() {
        return operdate;
    }
    public String getOperdate_user() { return operdate_user; }
    public String getOper_login_user() {return oper_login_user;}

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
    public String getPayaccin_name() {return payaccin_name;}
    public String getPayaccin_position() {return payaccin_position; }
    public String getPayaccout() {
        return payaccout;
    }
    public String getPayaccout_name() {return payaccout_name;}
    public String getPayaccout_position() { return payaccout_position; }
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
    public String getRequisites_name() {
        return requisites_name;
    }
    public String getContragent_name() {
        return contragent_name;
    }
    public String getArticle_name() {
        return article_name;
    }
    public Integer getPurpose_id() { return purpose_id; }
    public Integer getPurpose_name() { return purpose_name; }
}
