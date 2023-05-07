package mk.ukim.finki.brainboost.service;

public interface LessonService {
    void save(Long courseId, String name, byte[] content);
    void delete(Long courseId, Long lessonId);
}
