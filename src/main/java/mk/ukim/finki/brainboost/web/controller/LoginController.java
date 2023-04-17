package mk.ukim.finki.brainboost.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.domain.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.brainboost.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try {
            user = this.userService.login(request.getParameter("username"),
                                            request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/all_courses";
        }catch (InvalidUserCredentialsException exception){
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }
}
