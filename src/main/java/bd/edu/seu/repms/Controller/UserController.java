package bd.edu.seu.repms.Controller;

import bd.edu.seu.repms.Entity.User;
import bd.edu.seu.repms.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // Register Page দেখানোর জন্য
    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }


    // Register Form Submit করার জন্য
    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user) {

        userService.registerUser(user);

        return "redirect:/login";
    }


    // Login Page দেখানোর জন্য
    @GetMapping("/login")
    public String showLoginPage() {

        return "login";
    }
}