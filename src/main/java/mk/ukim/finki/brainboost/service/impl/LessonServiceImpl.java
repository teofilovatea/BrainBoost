package mk.ukim.finki.brainboost.service.impl;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.Lesson;
import mk.ukim.finki.brainboost.repository.LessonRepository;
import mk.ukim.finki.brainboost.service.CourseService;
import mk.ukim.finki.brainboost.service.LessonService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {
    private LessonRepository lessonRepository;
    private CourseService courseService;

    public LessonServiceImpl (LessonRepository lessonRepository, CourseService courseService) {
        this.lessonRepository = lessonRepository;
        this.courseService = courseService;
    }

    @Override
    public void save (Long courseId, String name, byte[] content) {
        Course course = courseService.findById (courseId).orElseThrow (EntityNotFoundException::new);
        lessonRepository.save (new Lesson (name, course, content));
    }

    @Override
    public void edit(Long lessonId, Long courseId, String name, byte[] content) {
        Course course = courseService.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + lessonId));

        lesson.setName(name);
        lesson.setCourse(course);
        lesson.setContent(content);

        lessonRepository.save(lesson);
    }

    @Override
    public void delete (Long courseId, Long lessonId) {
        Lesson lesson = lessonRepository.findByCourseIdAndId (courseId, lessonId);
        lessonRepository.delete (lesson);
    }

    @Override
    public List<Lesson> findAllByCourse (Long courseId) {
        return lessonRepository.findAllByCourseId (courseId);
    }

    @Override
    public Optional<Lesson> findById (Long courseId) {
        return lessonRepository.findById (courseId);
    }
}
