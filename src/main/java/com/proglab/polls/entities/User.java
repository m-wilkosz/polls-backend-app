package com.proglab.polls.entities;

import java.util.ArrayList;
import javax.persistence.*;
import java.util.Collection;
import org.joda.time.DateTime;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String emailAddress;

    @Column(length = 256)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private DateTime joiningDate;

    @Column(length = 256)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private DateTime lastActive;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Collection<Question> questions = new ArrayList<>();

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
    public void setQuestions(Collection<Question> questions) {this.questions = questions;}
    public Collection<Question> getQuestions() {return questions;}
}