package mk.ukim.finki.brainboost.repository;

import mk.ukim.finki.brainboost.domain.FavouritesList;
import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.enumerations.FavouriteListStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FavouritesListRepository extends JpaRepository<FavouritesList, Integer> {
    Optional<FavouritesList> findByUserAndStatus(User user, FavouriteListStatus status);
}
