package bootcampFinder.controller;

import bootcampFinder.configurations.UserService;
import bootcampFinder.configurations.UserValidator;
import bootcampFinder.models.App;
import bootcampFinder.models.Bootcamp;
import bootcampFinder.models.Testimonial;
import bootcampFinder.models.User;
import bootcampFinder.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        //System.out.println(user.getUserName());
        //System.out.println(user.getPassword());

        if (user.getRole().equals("student")) {
            if (appRepository.existsByUserName(user.getUserName()))
                model.addAttribute("app", appRepository.findOneByUserName(user.getUserName()));
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

    /*@RequestMapping("/search")
    public String gosearch(){
        return "base";
    }*/
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String newSearch(Model model){
        model.addAttribute("bootcamp",new Bootcamp());
        return "search";
}



    @RequestMapping("/search")
    private String search(Model model) {
        model.addAttribute("bootcamp", new Bootcamp());
        model.addAttribute("camps", new ArrayList<Bootcamp>());
        return "search";
    }
    @RequestMapping("/searchTerm")
    private String searchTerm(Model model, Bootcamp search) {
        model.addAttribute("bootcamp", new Bootcamp());
        List<Bootcamp> camps =  new ArrayList<>();
        camps.addAll(bootcampRepository.findAllByDescriptionContaining(search.getBootcampName()));
        for (Bootcamp camp : bootcampRepository.findAllByTopicsContaining(search.getBootcampName()))
            for(Bootcamp boot : camps)
                if (!camp.getBootcampDirector().equals(boot.getBootcampDirector()))
                    camps.add(camp);
        model.addAttribute("camps", new ArrayList<Bootcamp>());
        return "search";
    }


/*
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchPost(@RequestParam("zipCode") Long zipcode, @ModelAttribute Bootcamp bootcamp, Model model){
    long   min = zipcode - 100;
    long   max = zipcode + 100;
      /*  long min = bootcamp.getZipCode() - 1;
        long max = bootcamp.getZipCode() + 1;*/ /*
        List<Bootcamp> zipList = new ArrayList<>();

        for(long i = min; i <= max; i++){
            List<Bootcamp> custList = bootcampRepository.findByZipCode(i);
            zipList.addAll(custList);
        }
        model.addAttribute("NewZipList",zipList);
        return "searchdisplay";
    }

    @RequestMapping(value = "/bootcamp/{id}", method = RequestMethod.GET)
    public String bootcamps(@PathVariable("id") long id, Model model, Bootcamp bootcamp){

        model.addAttribute("customer", new Bootcamp());
        model.addAttribute("user", new User());
        model.addAttribute("testimonal", new Testimonial());

        List<Testimonial> testimonials = new ArrayList<>();
        testimonials.addAll(Arrays.asList(testimonialRepository.findAllByBootcampId(id)));
        model.addAttribute("testimonals", testimonials);

        bootcamp = bootcampRepository.findByBootcampId(bootcamp.getBootcampId());
        model.addAttribute("bootlisting",bootcamp);
        return "displaybootcamp";
    }
*/
    @RequestMapping("/saveApp")
    public String saveApp(@ModelAttribute App app, Principal principal) {
        User user = userRepository.findOneByUserName(principal.getName());

        if (appRepository.existsByUserName(user.getUserName())) {
            long id = appRepository.findOneByUserName(user.getUserName()).getAppId();
            app.setAppId(id);
        }

        appRepository.save(app);
        return "redirect:/";
    }

    @RequestMapping("/saveCamp")
    public String saveCamp(@ModelAttribute Bootcamp bootcamp, Principal principal) {

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
            App app = new App();
            app.makeNew(user.getUserName());
            appRepository.save(app);
            model.addAttribute("message", "Student Account Successfully Created");
        } else if (user.getRole().equals("director")) {
            userService.saveDirector(user);
            Bootcamp camp = new Bootcamp();
            camp.makeNew(user.getUserName());
            bootcampRepository.save(camp);
            model.addAttribute("message", "Director Account Successfully Created");
        }
        return "login";
    }


    private User getUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findOneByUserName(userDetails.getUsername());
    }

}
