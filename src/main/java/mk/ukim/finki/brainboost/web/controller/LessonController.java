package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.domain.Lesson;
import mk.ukim.finki.brainboost.domain.exceptions.LessonNotFoundException;
import mk.ukim.finki.brainboost.service.CourseService;
import mk.ukim.finki.brainboost.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    private final CourseService courseService;

    public LessonController (LessonService lessonService, CourseService courseService) {
        this.lessonService = lessonService;
        this.courseService = courseService;
    }

    @PostMapping("/courses/{courseId}")
    public String addLesson (@PathVariable("courseId") Long courseId,
                             @RequestParam("name") String name,
                             @RequestParam("file") MultipartFile file) {
        try {
            lessonService.save (courseId, name, file.getBytes());
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
    public String addLessonPage (Model model,@RequestParam Long coursesId) {
        model.addAttribute ("bodyContent", "add-lesson");
        model.addAttribute ("courseId", coursesId);

        return "add-lesson";
    }

    @GetMapping("/lessons-details-page/{coursesId}/{lessonId}")
    public String lessonsDetailsPage (Model model, @RequestParam Long lessonId,@RequestParam Long coursesId, Principal principal) {
        if (this.courseService.findById(coursesId).isPresent() && this.lessonService.findById(lessonId).isPresent()) {
            Lesson lesson = this.lessonService.findById(lessonId)
                    .orElseThrow(() -> new LessonNotFoundException());
            model.addAttribute("bodyContent", "add-lesson");
            model.addAttribute("lesson", lesson);
            model.addAttribute("lessonId", lessonId);

            return "lessons-details";
        }else{
            return "redirect:/all_courses?error=ProductNotFound";
        }
    }
}