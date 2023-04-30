package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.domain.Category;
import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.repository.CourseRepository;
import mk.ukim.finki.brainboost.service.CategoryService;
import mk.ukim.finki.brainboost.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/all_courses")
public class CoursesController {
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final CourseRepository courseRepository;

    public CoursesController(CourseService courseService, CategoryService categoryService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.courseRepository = courseRepository;
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
    public String courseDetailsPage(@PathVariable Long id, Model model) {
        if (this.courseService.findById(id).isPresent()) {
            Course course = this.courseService.findById(id).get();
            model.addAttribute("course", course);
            return "course-details";
        }
        return "redirect:/all_courses?error=ProductNotFound";
    }

}
