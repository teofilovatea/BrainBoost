package mk.ukim.finki.brainboost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Course course;

    private byte[] content;

    public Lesson (String name, Course course, byte[] content) {
        this.name = name;
        this.course = course;
        this.content = content;
    }
}
