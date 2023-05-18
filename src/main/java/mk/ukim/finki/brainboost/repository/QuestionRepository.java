package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.Question;
import mk.ukim.finki.brainboost.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByQuiz(Quiz quiz);
}
