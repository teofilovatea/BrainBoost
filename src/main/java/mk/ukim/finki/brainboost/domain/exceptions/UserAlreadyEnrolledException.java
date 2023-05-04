package mk.ukim.finki.brainboost.domain.exceptions;

public class UserAlreadyEnrolledException extends RuntimeException{
    public UserAlreadyEnrolledException(Long courseId, String username){
        super(String.format("User with username: %s already exists in course with id: %d!", username, courseId));
    }
}
