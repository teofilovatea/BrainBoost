package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findQuizByCourse(Course course);

    @Query("SELECT COUNT(q) FROM Quiz q WHERE q.user.id = :studentId AND q.dateFinished IS NOT NULL")
    int getPassedQuizCountByStudentId(@Param("studentId") Long studentId);
}
