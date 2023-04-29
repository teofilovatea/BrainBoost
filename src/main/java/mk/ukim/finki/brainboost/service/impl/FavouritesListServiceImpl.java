package mk.ukim.finki.brainboost.service.impl;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.FavouritesList;
import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.enumerations.FavouriteListStatus;
import mk.ukim.finki.brainboost.domain.exceptions.FavouritesListNotFoundException;
import mk.ukim.finki.brainboost.domain.exceptions.UserNotFoundException;
import mk.ukim.finki.brainboost.repository.FavouritesListRepository;
import mk.ukim.finki.brainboost.repository.UserRepository;
import mk.ukim.finki.brainboost.service.FavouritesListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouritesListServiceImpl implements FavouritesListService {
    private final FavouritesListRepository favouritesListRepository;
    private final UserRepository userRepository;

    public FavouritesListServiceImpl(FavouritesListRepository favouritesListRepository, UserRepository userRepository) {
        this.favouritesListRepository = favouritesListRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Course> listAllCoursesInFavouritesList(Integer id) {
        if(!this.favouritesListRepository.findById(id).isPresent()){
            throw new FavouritesListNotFoundException(id);
        }
        return this.favouritesListRepository.findById(id).get().getCourses();
    }

    @Override
    public FavouritesList getActiveFavouriteList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.favouritesListRepository.findByUserAndStatus(user, FavouriteListStatus.CREATED)
                .orElseGet(()->{
                    FavouritesList favouritesList = new FavouritesList(user);
                    return this.favouritesListRepository.save(favouritesList);
                });
    }
}
