package mk.ukim.finki.brainboost.service.impl;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.FavouritesList;
import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.enumerations.FavouriteListStatus;
import mk.ukim.finki.brainboost.domain.exceptions.CourseAlreadyInFavouritesListException;
import mk.ukim.finki.brainboost.domain.exceptions.CourseNotFoundException;
import mk.ukim.finki.brainboost.domain.exceptions.FavouritesListNotFoundException;
import mk.ukim.finki.brainboost.domain.exceptions.UserNotFoundException;
import mk.ukim.finki.brainboost.repository.CourseRepository;
import mk.ukim.finki.brainboost.repository.FavouritesListRepository;
import mk.ukim.finki.brainboost.repository.UserRepository;
import mk.ukim.finki.brainboost.service.CourseService;
import mk.ukim.finki.brainboost.service.FavouritesListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouritesListServiceImpl implements FavouritesListService {
    private final FavouritesListRepository favouritesListRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;

    public FavouritesListServiceImpl(FavouritesListRepository favouritesListRepository, UserRepository userRepository, CourseRepository courseRepository, CourseService courseService) {
        this.favouritesListRepository = favouritesListRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
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

    @Override
    public void addCourseToFavouritesList(String username, Long courseId) {
        FavouritesList favouritesList = this.getActiveFavouriteList(username);
        Course course = this.courseService.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));

        if (favouritesList.getCourses()
                .stream().filter(i -> i.getId().equals(courseId))
                .toList().size() > 0)
            throw new CourseAlreadyInFavouritesListException(courseId, username);
        favouritesList.getCourses().add(course);
        this.favouritesListRepository.save(favouritesList);
    }

    @Override
    public FavouritesList removeCourseFromWishList(String userId, Long courseId) {
        FavouritesList favouritesList=this.getActiveFavouriteList(userId);
        favouritesList.setCourses(favouritesList.getCourses()
                .stream().filter(course -> !course.getId().equals(courseId))
                .collect(Collectors.toList()));
        return this.favouritesListRepository.save(favouritesList);
    }
}
