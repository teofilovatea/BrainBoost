package mk.ukim.finki.brainboost.service;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.FavouritesList;

import java.util.List;
public interface FavouritesListService {
        List<Course> listAllCoursesInFavouritesList(Integer id);
        FavouritesList getActiveFavouriteList(String username);
        void addCourseToFavouritesList(String username, Long courseId);
        FavouritesList removeCourseFromWishList(String userId, Integer productId);
}
