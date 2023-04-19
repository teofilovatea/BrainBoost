package mk.ukim.finki.brainboost.service;

import mk.ukim.finki.brainboost.domain.Category;
import mk.ukim.finki.brainboost.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> listAll();

    Optional<Course> save(String name, Long category, String description, String teacher, String image, String time);

    Optional<Course> edit(Long id, String name, Long category, String description,
                          String teacher, String image, String time);

    void deleteById(Long id);
}
