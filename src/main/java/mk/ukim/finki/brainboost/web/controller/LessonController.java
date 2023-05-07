package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.service.LessonService;
import org.springframework.stereotype.Controller;
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
    public void addLesson (@PathVariable("courseId") Long courseId,
                           @RequestParam("name") String name,
                           @RequestParam("file") MultipartFile file) {
        try {
            lessonService.save (courseId, name, file.getBytes ());
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    @DeleteMapping("/courses/{courseId}/lessons/{lessonId}")
    public void deleteCourseLesson(@PathVariable("courseId") Long courseId,
                                   @PathVariable("lessonId") Long lessonId){
        lessonService.delete(courseId, lessonId);
    }
}
