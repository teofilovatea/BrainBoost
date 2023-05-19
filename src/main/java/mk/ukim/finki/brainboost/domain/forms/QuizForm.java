package mk.ukim.finki.brainboost.domain.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuizForm {
    private Long courseId;
    private String name;
    private List<QuestionForm> questions;
}
