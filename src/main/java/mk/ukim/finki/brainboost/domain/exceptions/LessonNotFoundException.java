package mk.ukim.finki.brainboost.domain.exceptions;

public class LessonNotFoundException extends RuntimeException {

    public LessonNotFoundException(){
        super("Lesson was not found");

    }
}
