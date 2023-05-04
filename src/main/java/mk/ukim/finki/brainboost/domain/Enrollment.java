package mk.ukim.finki.brainboost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Course course;

    private LocalDate dateEnrolled;

    public Enrollment(User user, Course course) {
        this.user = user;
        this.course = course;
        this.dateEnrolled = LocalDate.now();
    }
}
