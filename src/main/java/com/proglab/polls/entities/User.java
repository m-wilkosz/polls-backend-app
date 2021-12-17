package com.proglab.polls.entities;

import javax.persistence.*;
import org.joda.time.DateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column
    private String username;

    @Column
    private String emailAddress;

    @Column
    private DateTime joiningDate;

    @Column
    private DateTime lastActive;
}