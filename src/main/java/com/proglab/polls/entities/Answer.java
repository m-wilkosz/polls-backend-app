package com.proglab.polls.entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answer_seq")
    @Column
    private Integer id;

    @Column(nullable = false)
    private String text;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "question_id", nullable = false)
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