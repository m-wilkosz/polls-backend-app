package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import com.proglab.polls.entities.Answer;
import com.proglab.polls.repositories.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AnswerServiceImp implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Answer saveAnswer(Answer answer) {return answerRepository.save(answer);}

    @Override
    public void deleteAnswer(Integer id) {answerRepository.deleteById(id);}

    @Override
    public Iterable<Answer> getAllAnswers() {return answerRepository.findAll();}

    @Override
    public Optional<Answer> getByID(Integer id) {return answerRepository.findById(id);}

    @Override
    public List<Answer> getByTextContaining(String keyword) {return answerRepository.findByTextContaining(keyword);}

    @Override
    public Collection<Answer> getByQuestionId(Integer id) {return answerRepository.findByQuestionId(id);}
}