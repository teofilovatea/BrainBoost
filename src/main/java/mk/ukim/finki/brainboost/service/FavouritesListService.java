package mk.ukim.finki.brainboost.service;

import mk.ukim.finki.brainboost.domain.Course;

import java.util.List;
public interface FavouritesListService {
        List<Course> listAllCoursesInFavouritesList(Integer id);
}
