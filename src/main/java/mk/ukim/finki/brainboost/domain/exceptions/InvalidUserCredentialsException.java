package mk.ukim.finki.brainboost.domain.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException(){
        super("Invalid user credentials exception");
    }
}
