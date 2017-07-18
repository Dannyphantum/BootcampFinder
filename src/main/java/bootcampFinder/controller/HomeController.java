package bootcampFinder.controller;

import bootcampFinder.configurations.UserService;
import bootcampFinder.configurations.UserValidator;
import bootcampFinder.models.App;
import bootcampFinder.models.Bootcamp;
import bootcampFinder.models.User;
import bootcampFinder.repositories.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private AppRepository appRepository;
    private MessageRepository messageRepository;            private RoleRepository roleRepository;
    private TestimonialRepository testimonialRepository;    private UserRepository userRepository;
    private UserValidator userValidator;                    private UserService userService;


    @Autowired
    private BootcampRepository bootcampRepository;

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
    public String home(Model model, Authentication authentication) {
        User user = getUser(authentication);
        model.addAttribute("user", user);
        if (appRepository.existsByUserId(user.getUserId()))
            model.addAttribute("app", appRepository.findOneByUserId(user.getUserId()));
        else model.addAttribute("app", new App());
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
@RequestMapping(value = "/search", method = RequestMethod.GET)
public String newSearch(Model model){
        model.addAttribute("bootcamp",new Bootcamp());
        return "search";
}

@RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchPost(@RequestParam("zipCode") Long zipcode, @ModelAttribute Bootcamp bootcamp, Model model){
long   min=zipcode -100;
long   max = zipcode+100;
      /*  long min = bootcamp.getZipCode() - 1;
        long max = bootcamp.getZipCode() + 1;*/
        List<Bootcamp> zipList = new ArrayList<>();

        for(long i=min;i<=max;i++){
            List<Bootcamp> custList = bootcampRepository.findByZipCode(i);
            zipList.addAll(custList);
        }
        model.addAttribute("NewZipList",zipList);
        return "searchdisplay";
    }

    @RequestMapping(value = "/bootcamp/{id}", method = RequestMethod.GET)
    public String bootcamps(@PathVariable("id") long id, Model model, Bootcamp bootcamp){
        model.addAttribute("customer", new Bootcamp());
        Iterable<Bootcamp> custList = bootcampRepository.findByBootcampId(bootcamp.getBootcampId());
        model.addAttribute("bootlist",custList);
        return "displaybootcamp";
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
