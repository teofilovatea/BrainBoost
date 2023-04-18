package mk.ukim.finki.brainboost.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/all_courses")
public class CoursesController {
    @GetMapping
    public String getHomePage() {
        return "all_courses";
    }
}
