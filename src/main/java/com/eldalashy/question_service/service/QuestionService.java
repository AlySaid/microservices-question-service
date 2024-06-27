package com.eldalashy.question_service.service;

import com.eldalashy.question_service.dao.QuestionDao;
import com.eldalashy.question_service.model.Question;
import com.eldalashy.question_service.model.QuestionWrapper;
import com.eldalashy.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("New question added successfully ", HttpStatus.CREATED);
    }

    public String deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return "Question no " + id + " Deleted ";
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        {
            try {
                questionDao.save(question);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Question Updated successfully ", HttpStatus.CREATED);
        }
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String quizCategory, Integer noOfQuestions) {
        List<Integer> questionIds = questionDao.findRandomQuestionByCategory(noOfQuestions, quizCategory);
        return new ResponseEntity<>(questionIds, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromIds(List<Integer> ids) {

        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (Integer id : ids) {
            questions.add(questionDao.findById(id).get());
        }
        for (Question question : questions) {
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setId(question.getId());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            wrappers.add(questionWrapper);
        }
        return new ResponseEntity<>(wrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        Integer score = 0;
        for (Response response : responses) {
            Question question = questionDao.findById(response.getId()).get();
            if (question.getRightAnswer().equals(response.getSubmitAnswer()))
                score++;
        }

        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}