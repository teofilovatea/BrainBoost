package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
 void deleteByName(String name);
 List<Course> findByCategory_Name(String category);
 List<Course> findByNameContainingIgnoreCase(String name);
}
