package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/all_courses")
public class CoursesController {
    private final CourseService courseService;

    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getProductPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses = this.courseService.listAll();
        model.addAttribute("courses", courses);
        return "all_courses";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        this.courseService.deleteById(id);
        return "redirect:/all_courses";
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(Model model) {
        model.addAttribute("bodyContent", "add-course");
        return "add-course";
    }

    @PostMapping("/add")
    public String addCourse(
            @RequestParam String name,
            @RequestParam Long categoryId,
            @RequestParam String description,
            @RequestParam String teacher,
            @RequestParam String image,
            @RequestParam String time) {

        this.courseService.save(name,categoryId,description,teacher,image,time);

        return "redirect:/all_courses";
    }




}
