package mk.ukim.finki.brainboost.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class CourseAlreadyInFavouritesListException extends RuntimeException{
    public CourseAlreadyInFavouritesListException(Long id, String username) {
        super(String.format("Product with id: %d already exists in wish list for user with username %s", id, username));
    }
}
