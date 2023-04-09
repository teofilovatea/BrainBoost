package mk.ukim.finki.brainboost.domain.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {
    public PasswordsDoNotMatchException(){
        super("Passwords do not match. Try again!");
    }
}
