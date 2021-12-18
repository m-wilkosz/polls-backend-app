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

    public User() {

    }

    public User(String username, String emailAddress, DateTime joiningDate, DateTime lastActive) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.joiningDate = joiningDate;
        this.lastActive = lastActive;
    }

    public void setId(Integer id) {this.id = id;}
    public Integer getId() {return id;}
    public void setUsername(String username) {this.username = username;}
    public String getUsername() {return username;}
    public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}
    public String getEmailAddress() {return emailAddress;}
    public void setJoiningDate(DateTime joiningDate) {this.joiningDate = joiningDate;}
    public DateTime getJoiningDate() {return joiningDate;}
    public void setLastActive(DateTime lastActive) {this.lastActive = lastActive;}
    public DateTime getLastActive() {return lastActive;}
}