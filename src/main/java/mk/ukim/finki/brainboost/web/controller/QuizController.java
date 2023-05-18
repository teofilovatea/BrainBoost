package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.domain.Course;
import mk.ukim.finki.brainboost.domain.Question;
import mk.ukim.finki.brainboost.domain.Quiz;
import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.forms.QuizForm;
import mk.ukim.finki.brainboost.domain.forms.QuestionForm;
import mk.ukim.finki.brainboost.repository.QuestionRepository;
import mk.ukim.finki.brainboost.repository.QuizRepository;
import mk.ukim.finki.brainboost.service.CourseService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class QuizController {
    private final CourseService courseService;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizController(CourseService courseService, QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.courseService = courseService;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/{quizId}")
    public String showQuizDetails(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(RuntimeException::new);
        List<Question> questions = questionRepository.findByQuiz(quiz);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);

        return "quiz-details";
    }

    @GetMapping("/add-form")
    public String showQuizForm(Model model, @RequestParam Long courseId) {
        model.addAttribute ("courseId", courseId);
        return "add-quiz";
    }

    @PostMapping("/new")
    public String createQuiz(@ModelAttribute("quizForm") QuizForm quizForm, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors if necessary
            return "create-quiz";
        }

        // Retrieve the course by ID from the database
        Course course = courseService.findById(quizForm.getCourseId()).orElse(null);

        // Create a new Quiz object with the form data
        Quiz quiz = new Quiz(quizForm.getName(), course);
        quiz.setUser((User) authentication.getPrincipal()); // Set the user for the quiz (assuming you have a method to get the current user)

        // Create and associate the questions with the quiz
        List<QuestionForm> questionForms = quizForm.getQuestions();
        List<Question> questions = new ArrayList<>();
        for (QuestionForm questionForm : questionForms) {
            List<String> answers = questionForm.getAnswers();
            int correctAnswer = questionForm.getCorrectAnswer();
            Question question = new Question(questionForm.getText(), answers, correctAnswer, quiz);
            questions.add(question);
        }
        quiz.setQuestions(questions);

        // Save the quiz to the database using the QuizRepository
        Quiz savedQuiz = quizRepository.save(quiz);

        return "redirect:/quizzes/" + savedQuiz.getId(); // Redirect to the newly created quiz's page
    }
    @GetMapping("/submit-quiz")
    public String showQuizDetails() {
        return "successfully-finished";
    }
}
