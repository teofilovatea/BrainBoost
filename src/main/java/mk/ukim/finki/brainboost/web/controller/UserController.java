package mk.ukim.finki.brainboost.web.controller;

import mk.ukim.finki.brainboost.domain.User;
import mk.ukim.finki.brainboost.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user_profile")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping()
    public String getUserProfilePage(
            @RequestParam(required = false) String error,
            Model model, Authentication authentication) {
        if (error != null && !error.isEmpty ()) {
            model.addAttribute ("hasError", true);
            model.addAttribute ("error", error);
        }
        User client= (User) authentication.getPrincipal();
        model.addAttribute("user", client);
        return "user-profile";
    }

    @PostMapping()
    public String editUserProfilePage (
            @RequestParam(required = false) String error,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String username,
            @RequestParam String email,
            Authentication authentication) {
        User client= (User) authentication.getPrincipal();
        this.userService.edit(client.getId(), name, surname, email, username, authentication);
        return "redirect:/user_profile";
    }
}
