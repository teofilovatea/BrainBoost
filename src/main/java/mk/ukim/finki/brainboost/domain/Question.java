package mk.ukim.finki.brainboost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ElementCollection
    @CollectionTable(name = "question_answers", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "answer")
    private List<String> answers;

    private int correctAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    public Question(String text, List<String> answers, int correctAnswer, Quiz quiz) {
        this.text = text;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.quiz = quiz;
    }
}