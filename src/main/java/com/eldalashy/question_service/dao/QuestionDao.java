package com.eldalashy.question_service.dao;

import com.eldalashy.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "Select q.id from question q where q.category = :cat  Order by Random() LIMIT :numQ " , nativeQuery = true)
    List<Integer> findRandomQuestionByCategory(Integer numQ, String cat);

}
