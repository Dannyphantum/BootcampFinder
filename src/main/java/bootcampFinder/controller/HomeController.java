package bootcampFinder.controller;

import bootcampFinder.configurations.UserService;
import bootcampFinder.configurations.UserValidator;
import bootcampFinder.models.App;
import bootcampFinder.models.Bootcamp;
import bootcampFinder.models.User;
import bootcampFinder.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    private AppRepository appRepository;                    private BootcampRepository bootcampRepository;
    private MessageRepository messageRepository;            private RoleRepository roleRepository;
    private TestimonialRepository testimonialRepository;    private UserRepository userRepository;
    private UserValidator userValidator;                    private UserService userService;

    @Autowired
    public HomeController(AppRepository appRepository, BootcampRepository bootcampRepository
            , MessageRepository messageRepository, UserService userService, RoleRepository roleRepository
            , TestimonialRepository testimonialRepository, UserRepository userRepository, UserValidator userValidator) {
        this.appRepository = appRepository;                 this.bootcampRepository = bootcampRepository;
        this.messageRepository = messageRepository;         this.roleRepository = roleRepository;
        this.testimonialRepository = testimonialRepository; this.userRepository = userRepository;
        this.userValidator = userValidator;                 this.userService = userService;
    }

    @RequestMapping("/")
    public String home(Model model, Principal principal) {
        User user = userRepository.findOneByUserName(principal.getName());
        model.addAttribute("user", user);

        System.out.println(user.getUserName());
        System.out.println(user.getPassword());

        if (user.getRole().equals("student")) {
            if (appRepository.existsByUserId(user.getUserId()))
                model.addAttribute("app", appRepository.findOneByUserId(user.getUserId()));
            else model.addAttribute("app", new App());
        } else if (user.getRole().equals("director")) {
            if (bootcampRepository.existsByBootcampDirector(user.getUserName()))
                model.addAttribute("camp", bootcampRepository.findOneByBootcampDirector(user.getUserName()));
            else model.addAttribute("camp", new Bootcamp());
        }

        model.addAttribute("messages", messageRepository.findAllByRecieverId(user.getUserId()));
        return "home";
    }
/*
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getlogin(Model model) {
        model.addAttribute("action", "login");
        return "login";
    }
*/
    @RequestMapping("/login")
    public String login(Model model) {
    model.addAttribute("action", "login");
    return "login";
}

    @RequestMapping("/search")
    public String gosearch(){
        return "base";
    }

    @RequestMapping("/saveApp")
    public String saveApp(App app, Principal principal) {
        User user = userRepository.findOneByUserName(principal.getName());

        if (appRepository.existsByUserId(user.getUserId())) {
            long id = appRepository.findOneByUserId(user.getUserId()).getAppId();
            app.setAppId(id);
        }

        appRepository.save(app);
        return "redirect:/";
    }

    @RequestMapping("/saveCamp")
    public String saveCamp(Bootcamp bootcamp, Principal principal) {
        User user = userRepository.findOneByUserName(principal.getName());

        if (bootcampRepository.existsByBootcampDirector(user.getUserName())) {
            long id = bootcampRepository.findOneByBootcampDirector(user.getUserName()).getBootcampId();
            bootcamp.setBootcampId(id);
        }

        bootcampRepository.save(bootcamp);
        return "redirect:/";
    }



    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user", user);
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "registration";
        } else if (user.getRole().equals("student")) {
            userService.saveStudent(user);
            model.addAttribute("message", "Student Account Successfully Created");
        } else if (user.getRole().equals("director")) {
            userService.saveDirector(user);
            model.addAttribute("message", "Director Account Successfully Created");
        }
        return "login";
    }



    private User getUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findOneByUserName(userDetails.getUsername());
    }

}
