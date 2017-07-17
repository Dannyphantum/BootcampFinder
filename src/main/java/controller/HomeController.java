package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

}
