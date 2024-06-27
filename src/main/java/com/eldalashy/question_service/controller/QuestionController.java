package com.eldalashy.question_service.controller;

import com.eldalashy.question_service.model.Question;
import com.eldalashy.question_service.model.QuestionWrapper;
import com.eldalashy.question_service.model.Response;
import com.eldalashy.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("getQuestion/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("addQuestion")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("updateQuestion")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("deleteQuestion/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("questionId") Integer questionId) {
        System.out.println(questionId);
        return new ResponseEntity<>(questionService.deleteQuestion(questionId), HttpStatus.NO_CONTENT);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String quizCategory, @RequestParam Integer noOfQuestions) {
        return questionService.getQuestionsForQuiz(quizCategory, noOfQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromIds(@RequestBody List<Integer>ids) {
        return questionService.getQuestionFromIds(ids);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response>responses) {
        return questionService.getScore(responses);
    }

}
