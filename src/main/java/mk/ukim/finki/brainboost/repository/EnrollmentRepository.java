package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.Enrollment;
import mk.ukim.finki.brainboost.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserUsername(String username);

    Enrollment findByUserAndCourse(User user, Course course);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.user.id = :studentId")
    int getEnrollmentCountByUser(@Param("studentId") Long studentId);
}
