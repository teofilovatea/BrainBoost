package mk.ukim.finki.brainboost.service.impl;

import mk.ukim.finki.brainboost.domain.Category;
import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.exceptions.CategoryNotFoundException;
import mk.ukim.finki.brainboost.domain.exceptions.CourseNotFoundException;
import mk.ukim.finki.brainboost.repository.CategoryRepository;
import mk.ukim.finki.brainboost.repository.CourseRepository;
import mk.ukim.finki.brainboost.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Course> listAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return this.courseRepository.findById(id);
    }

    @Override
    public Optional<Course> save(String name, Long categoryId, String description, String teacher, String image, String time) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException(categoryId));

        return Optional.of(this.courseRepository.save(new Course(name, category, description, teacher, image, time)));
    }

    @Override
    public Optional<Course> edit(Long id, String name, Long categoryId, String description, String teacher, String image, String time) {
        Course course = this.courseRepository.findById(id).orElseThrow(()-> new CourseNotFoundException(id));

        course.setName(name);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException(categoryId));

        course.setCategory(category);
        course.setDescription(description);
        course.setTeacher(teacher);
        course.setImage(image);
        course.setTime(time);

        return Optional.of(this.courseRepository.save(course));
    }

    @Override
    public void deleteById(Long id) {
        this.courseRepository.deleteById(id);
    }
}
