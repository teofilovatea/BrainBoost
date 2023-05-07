package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson findByCourseIdAndId(Long courseId, Long id);
}
