package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    public LessonController (LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/courses/{courseId}")
    public String addLesson (@PathVariable("courseId") Long courseId,
                             @RequestParam("name") String name,
                             @RequestParam("file") MultipartFile file) {
        try {
            lessonService.save (courseId, name, file.getBytes ());
            return "redirect:/all_courses/details/" + courseId;
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    @DeleteMapping("/courses/{courseId}/lessons/{lessonId}")
    public String deleteCourseLesson (@PathVariable("courseId") Long courseId,
                                      @PathVariable("lessonId") Long lessonId) {
        lessonService.delete (courseId, lessonId);
        return "redirect:/all_courses/details/" + courseId;
    }

    @GetMapping("/add-form")
    public String addLessonPage (Model model) {
        model.addAttribute ("bodyContent", "add-lesson");
        return "add-lesson";
    }
}
