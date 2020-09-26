package nospringquiz;

import entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    void save(Question question);
    Optional<Question> findById(long id);
    void delete(Question question);
    void update(long id, Question question);
    List<Question> findAll();
}