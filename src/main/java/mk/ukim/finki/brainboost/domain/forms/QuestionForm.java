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
public class QuestionForm {
    private String text;
    private List<String> answers;
    private int correctAnswer;
}
