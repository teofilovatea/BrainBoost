package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.domain.Lesson;
import mk.ukim.finki.brainboost.domain.exceptions.LessonNotFoundException;
import mk.ukim.finki.brainboost.repository.LessonRepository;
import mk.ukim.finki.brainboost.service.CourseService;
import mk.ukim.finki.brainboost.service.LessonService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    private final CourseService courseService;
    private final LessonRepository lessonRepository;

    public LessonController (LessonService lessonService, CourseService courseService, LessonRepository lessonRepository) {
        this.lessonService = lessonService;
        this.courseService = courseService;
        this.lessonRepository = lessonRepository;
    }

    @PostMapping("/courses/{courseId}")
    public String addLesson (@PathVariable("courseId") Long courseId,
                             @PathVariable(value = "lessonId", required = false) Long lessonId,
                             @RequestParam("name") String name,
                             @RequestParam("file") MultipartFile file) {
        try {
            if(lessonId != null){
                lessonService.edit(lessonId,courseId,name,file.getBytes());
            }else {
                lessonService.save (courseId, name, file.getBytes());
            }
            return "redirect:/all_courses/details/"+courseId;
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    @GetMapping("/{lessonId}/edit")
    public String editLessonPage(Model model, @PathVariable("lessonId") Long lessonId) {
        Optional<Lesson> lesson = lessonService.findById(lessonId);

        if (lesson.isPresent()){
            Lesson lessonData = lesson.get();
            model.addAttribute("lessons", lessonData);
            model.addAttribute("courseId", lessonData.getCourse().getId());
            model.addAttribute("content", new String(lessonData.getContent()));
            return "add-lesson";
        }
        return "redirect:/all_courses/details/?error=LessonNotFound";
    }

    @PostMapping("/courses/{courseId}/lessons/{lessonId}")
    public String deleteCourseLesson (@PathVariable("courseId") Long courseId,
                                      @PathVariable("lessonId") Long lessonId) {
        this.lessonService.delete(courseId, lessonId);
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
                    .orElseThrow(LessonNotFoundException::new);
            model.addAttribute("bodyContent", "add-lesson");
            model.addAttribute("lesson", lesson);
            model.addAttribute("lessonId", lessonId);

            return "lessons-details";
        }else{
            return "redirect:/all_courses?error=ProductNotFound";
        }
    }
    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> openPdf(@PathVariable Long id) throws IOException {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(LessonNotFoundException::new);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + lesson.getName() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(lesson.getPdf());
    }
}
