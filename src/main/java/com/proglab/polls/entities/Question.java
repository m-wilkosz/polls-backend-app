package com.proglab.polls.entities;

import javax.persistence.*;
import org.joda.time.DateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column
    private String text;

    @Column
    private DateTime added;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_ID")
    private User user;

    @Column
    private Integer creatorID;

    public Question() {

    }

    public Question(String text, DateTime added, Integer creatorID) {
        this.text = text;
        this.added = added;
        this.creatorID = creatorID;
    }

    public void setId(Integer id) {this.id = id;}
    public Integer getId() {return id;}
    public void setText(String text) {this.text = text;}
    public String getText() {return text;}
    public void setAdded(DateTime added) {this.added = added;}
    public DateTime getAdded() {return added;}
    public void setCreatorID(Integer creatorID) {this.creatorID = creatorID;}
    public Integer getCreatorID() {return creatorID;}
}