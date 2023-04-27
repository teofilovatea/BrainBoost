package mk.ukim.finki.brainboost.service.impl;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.exceptions.FavouritesListNotFoundException;
import mk.ukim.finki.brainboost.repository.FavouritesListRepository;
import mk.ukim.finki.brainboost.service.FavouritesListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouritesListServiceImpl implements FavouritesListService {
    private final FavouritesListRepository favouritesListRepository;

    public FavouritesListServiceImpl(FavouritesListRepository favouritesListRepository) {
        this.favouritesListRepository = favouritesListRepository;
    }

    @Override
    public List<Course> listAllCoursesInFavouritesList(Integer id) {
        if(!this.favouritesListRepository.findById(id).isPresent()){
            throw new FavouritesListNotFoundException(id);
        }
        return this.favouritesListRepository.findById(id).get().getCourses();
    }
}
