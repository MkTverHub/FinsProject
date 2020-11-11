package com.sweetcard.basic.dao.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Ivribin on 07.03.2020.
 */

@Data
@Entity
public class Lov {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") public Integer id;
    @Column(name = "par_row_id") private Integer par_row_id;
    @Column(name = "text_val") public String text_val;
    @Column(name = "description") public String description;
    @Column(name = "options") public String options;
    @Column(name = "type") public String type;

}
