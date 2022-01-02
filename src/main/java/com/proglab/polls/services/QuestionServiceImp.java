package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.Question;
import com.proglab.polls.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class QuestionServiceImp implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question saveQuestion(Question question) {return questionRepository.save(question);}

    @Override
    public void deleteQuestion(Integer id) {questionRepository.deleteById(id);}

    @Override
    public Iterable<Question> getAllQuestions() {return questionRepository.findAll();}

    @Override
    public Optional<Question> getById(Integer id) {return questionRepository.findById(id);}

    @Override
    public Collection<Question> getByUserId(Integer id) {return questionRepository.findByUserId(id);}

    @Override
    public List<Question> getByTextContaining(String keyword) {return questionRepository.findByTextContaining(keyword);}

    @Override
    public List<Question> getByAdded(DateTime date1, DateTime date2) {return questionRepository.findByAdded(date1, date2);}

    @Override
    public List<Question> getByAddedAfter(DateTime date) {return questionRepository.findByAddedAfter(date);}
}