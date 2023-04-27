package mk.ukim.finki.brainboost.domain.exceptions;

public class FavouritesListNotFoundException extends RuntimeException{
    public FavouritesListNotFoundException(Integer id) {
        super(String.format("Favourite list with id: %d was not found", id));
    }
}
