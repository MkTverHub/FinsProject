package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class ReportType1 {
    @Column(name = "Value1")
    public String Value1;
    @Column(name = "Value2")
    public String Value2;
}
