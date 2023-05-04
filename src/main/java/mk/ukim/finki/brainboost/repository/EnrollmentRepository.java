package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.Enrollment;
import mk.ukim.finki.brainboost.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserUsername(String username);

    Enrollment findByUserAndCourse(User user, Course course);
}
