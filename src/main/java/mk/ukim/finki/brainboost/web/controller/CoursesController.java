package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.domain.Category;
import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.Enrollment;
import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.exceptions.CourseNotFoundException;
import mk.ukim.finki.brainboost.domain.exceptions.UserAlreadyEnrolledException;
import mk.ukim.finki.brainboost.domain.exceptions.UserNotFoundException;
import mk.ukim.finki.brainboost.repository.CourseRepository;
import mk.ukim.finki.brainboost.repository.EnrollmentRepository;
import mk.ukim.finki.brainboost.repository.QuizRepository;
import mk.ukim.finki.brainboost.repository.UserRepository;
import mk.ukim.finki.brainboost.service.CategoryService;
import mk.ukim.finki.brainboost.service.CourseService;
import mk.ukim.finki.brainboost.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/all_courses")
public class CoursesController {
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    private final LessonService lessonService;
    private final QuizRepository quizRepository;

    public CoursesController(CourseService courseService, CategoryService categoryService, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, UserRepository userRepository, LessonService lessonService, QuizRepository quizRepository) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.lessonService = lessonService;
        this.quizRepository = quizRepository;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error,
                                 @RequestParam(required = false) String categoryName,
                                 @RequestParam(required = false) String query,
                                 Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        else if(categoryName != null){
            model.addAttribute("courses", this.courseRepository.findByCategory_Name(categoryName));
        }
        else if(query != null){
            model.addAttribute("courses", this.courseRepository.findByNameContainingIgnoreCase(query));
        }
        else {
            model.addAttribute("courses", this.courseService.listAll());
        }
        return "all_courses";
    }
    @PostMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        this.courseService.deleteById(id);
        return "redirect:/all_courses";
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(Model model) {
        List<Category> categories = this.categoryService.listAll();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-course");
        return "add-course";
    }

    @PostMapping("/add")
    public String addCourse(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Long categoryId,
            @RequestParam String description,
            @RequestParam String teacher,
            @RequestParam String image,
            @RequestParam String time) {
        if(id!=null){
            this.courseService.edit(id, name, categoryId, description, teacher, image, time);
        }
        else{
            this.courseService.save(name,categoryId,description,teacher,image,time);
        }
        return "redirect:/all_courses";
    }

    @GetMapping("/edit-form/{id}")
    public String editCoursePage(@PathVariable Long id, Model model) {
        if (this.courseService.findById(id).isPresent()) {
            Course course = this.courseService.findById(id).get();
            List<Category> categories = this.categoryService.listAll();
            model.addAttribute("categories", categories);
            model.addAttribute("course", course);
            return "add-course";
        }
        return "redirect:/all_courses?error=ProductNotFound";
    }

    @GetMapping("/details/{id}")
    public String courseDetailsPage(@PathVariable Long id, Model model, Principal principal) {
        if (this.courseService.findById(id).isPresent()) {
            Course course = this.courseService.findById(id).get();

            String username = principal.getName();
            Optional<User> userOptional = userRepository.findByUsername(username);
            User user = userOptional.get();

            boolean isUserEnrolled = enrollmentRepository.findByUserAndCourse(user, course) != null;
            model.addAttribute("course", course);
            model.addAttribute("isUserEnrolled", isUserEnrolled);
            model.addAttribute("lessons", this.lessonService.findAllByCourse(id));
            model.addAttribute("quizzes", this.quizRepository.findQuizByCourse(course));
            return "course-details";
        }
        return "redirect:/all_courses?error=ProductNotFound";
    }

    @GetMapping("/enrolled")
    public String getEnrolledCourses(Principal principal, Model model) {
        // Get the current user's username from the Principal object
        String username = principal.getName();

        // Query the enrollment repository to find all enrollments for the current user
        List<Enrollment> enrollments = enrollmentRepository.findByUserUsername(username);

        // Extract the courses from the enrollments and return them as a list
        List<Course> courses = enrollments.stream()
                .map(Enrollment::getCourse)
                .collect(Collectors.toList());
        model.addAttribute("courses", this.courseService.listAll());
        model.addAttribute("enrollments", enrollments);
        return "course-enrollment";
    }
    @PostMapping("/{courseId}/enroll")
    public RedirectView enrollUserToCourse(@PathVariable Long courseId, Principal principal, RedirectAttributes redirectAttributes) {
        // Find the Course object corresponding to the given courseId
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new CourseNotFoundException(courseId);
        }
        Course course = courseOptional.get();

        // Find the User object corresponding to the given username
        String username = principal.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException(username);
        }
        User user = userOptional.get();

        // Check if the user is already enrolled in the course
        if (enrollmentRepository.findByUserAndCourse(user, course) != null) {
            throw new UserAlreadyEnrolledException(courseId, username);
        }

        Enrollment enrollment = new Enrollment(user,course);
        enrollmentRepository.save(enrollment);

        redirectAttributes.addFlashAttribute("courseId", courseId);
        return new RedirectView("/all_courses/details/{courseId}");
    }

    @PostMapping("/{courseId}/disenroll")
    public RedirectView disenrollUserFromCourse (@PathVariable Long courseId, Principal principal, RedirectAttributes redirectAttributes) {
        Course course = courseRepository.findById (courseId).get ();
        String username = principal.getName ();
        User user = userRepository.findByUsername (username).get ();


        Enrollment enrollment = enrollmentRepository.findByUserAndCourse (user, course);
        enrollmentRepository.delete (enrollment);

        redirectAttributes.addFlashAttribute ("courseId", courseId);
        return new RedirectView ("/all_courses/details/{courseId}");
    }

}


