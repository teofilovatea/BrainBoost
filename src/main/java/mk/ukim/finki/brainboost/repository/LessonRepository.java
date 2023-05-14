package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findByCourseIdAndId(Long courseId, Long id);
    List<Lesson> findAllByCourseId(Long courseId);
}
