package mk.ukim.finki.brainboost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Category category;

    private String description;

    private String teacher;

    private String image;

    private String time;

    public Course(String name, Category category, String description, String teacher, String image, String time) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.teacher = teacher;
        this.image = image;
        this.time = time;
    }
}
