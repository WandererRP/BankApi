package com.roland.solva.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author Roland Pilpani 03.12.2022
 */
@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {
    @Id
    @Column(name = "id")
    private long id;

    @OneToMany(mappedBy = "account")
    private List<MonthLimit> monthLimit;

}
