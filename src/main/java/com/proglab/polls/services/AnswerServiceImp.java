package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import com.proglab.polls.entities.Answer;
import org.springframework.stereotype.Service;
import com.proglab.polls.repositories.AnswerRepository;
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
    public Iterable<Answer> listAllAnswers() {return answerRepository.findAll();}

    @Override
    public Optional<Answer> getByID(Integer id) {return answerRepository.findById(id);}

    @Override
    public List<Answer> getByTextContaining(String keyword) {return answerRepository.findByTextContaining(keyword);}
}