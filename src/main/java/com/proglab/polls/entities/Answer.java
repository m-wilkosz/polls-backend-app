package com.proglab.polls.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column
    @NotNull
    private String text;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    @NotNull
    private Question question;

    public Answer() {

    }

    public Answer(String text) {
        this.text = text;
    }

    public void setId(Integer id) {this.id = id;}
    public Integer getId() {return id;}
    public void setText(String text) {this.text = text;}
    public String getText() {return text;}
    public void setQuestion(Question question) {this.question = question;}
    public Question getQuestion() {return question;}
}