package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("user_profile")
public class UserController {


    @GetMapping
    public String getRegisterPage (
            @RequestParam(required = false) String error,
            Model model) {
        if (error != null && !error.isEmpty ()) {
            model.addAttribute ("hasError", true);
            model.addAttribute ("error", error);
        }
        return "user-profile";
    }
}
