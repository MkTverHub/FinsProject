package com.sweetcard.basic.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class AggregateData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    private String name;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}



}
