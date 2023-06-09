package mk.ukim.finki.brainboost.service;

import mk.ukim.finki.brainboost.domain.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    Optional<Lesson> save(Long courseId, String name, byte[] content);
    Optional<Lesson> edit(Long lessonId, Long courseId, String name, byte[] content);
    void delete(Long courseId, Long lessonId);

    List<Lesson> findAllByCourse(Long courseId);

    Optional<Lesson> findById(Long courseId);
}
