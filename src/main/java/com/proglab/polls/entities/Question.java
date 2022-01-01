package com.proglab.polls.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.joda.time.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name = "question_generator", sequenceName = "question_seq", allocationSize = 50)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String text;

    @Column(length = 256, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime added;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "question", orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<Answer> answers = new ArrayList<>();

    public Question() {

    }

    public Question(String text, DateTime added) {
        this.text = text;
        this.added = added;
    }

    public void setId(Integer id) {this.id = id;}
    public Integer getId() {return id;}
    public void setText(String text) {this.text = text;}
    public String getText() {return text;}
    public void setAdded(DateTime added) {this.added = added;}
    public DateTime getAdded() {return added;}
    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}
    public void setAnswers(Collection<Answer> answers) {this.answers = answers;}
    public Collection<Answer> getAnswers() {return answers;}
}