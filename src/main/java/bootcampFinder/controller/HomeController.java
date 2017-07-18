package bootcampFinder.controller;

import bootcampFinder.models.Testimonial;
import bootcampFinder.models.User;
import bootcampFinder.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private ApplicationRepository applicationRepository;
    private BootcampRepository bootcampRepository;
    private MessageRepository messageRepository;
    private RoleRepository roleRepository;
    private TestimonialRepository testimonialRepository;
    private UserRepository userRepository;

    @Autowired
    public HomeController(ApplicationRepository applicationRepository, BootcampRepository bootcampRepository, MessageRepository messageRepository
            , RoleRepository roleRepository, TestimonialRepository testimonialRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.bootcampRepository = bootcampRepository;
        this.messageRepository = messageRepository;
        this.roleRepository = roleRepository;
        this.testimonialRepository = testimonialRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/")//TODO: change this to return home
    public String index() {
        return "login";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping("/search")
    public String gosearch(){
        return "base";
    }

}
