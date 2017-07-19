package bootcampFinder.controller;

import bootcampFinder.configurations.UserService;
import bootcampFinder.configurations.UserValidator;
import bootcampFinder.models.*;
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
    private RequestRepository requestRepository;

    @Autowired
    public HomeController(AppRepository appRepository, BootcampRepository bootcampRepository
            , MessageRepository messageRepository, UserService userService, RoleRepository roleRepository
            , TestimonialRepository testimonialRepository, UserRepository userRepository
            , UserValidator userValidator, RequestRepository requestRepository) {
        this.appRepository = appRepository;                 this.bootcampRepository = bootcampRepository;
        this.messageRepository = messageRepository;         this.roleRepository = roleRepository;
        this.testimonialRepository = testimonialRepository; this.userRepository = userRepository;
        this.userValidator = userValidator;                 this.userService = userService;
        this.requestRepository = requestRepository;
    }

    @RequestMapping("/viewMessage/{id}")
    public String viewmessage(Model model, Principal principal, @PathVariable("id") long id) {
        User user = userRepository.findOneByUserName(principal.getName());
        if (messageRepository.findOne(id).getRecieverId() == user.getUserId()) {
            model.addAttribute("message", messageRepository.findOne(id));
            return "viewmessage";
        }
        return "redirect:/";
    }

    @RequestMapping("/deletetest/{id}")
    public String delTest(@PathVariable("id") long id, Principal principal) {
        Testimonial test = testimonialRepository.findOne(id);
        User user = userRepository.findOneByUserName(principal.getName());
        Bootcamp camp = bootcampRepository.findOneByBootcampDirector(user.getUserName());
        if (test.getBootcampId() == camp.getBootcampId())
            testimonialRepository.delete(test);
        return "redirect:/";
    }

    @RequestMapping("/")
    public String home(Model model, Principal principal) {
        User user = userRepository.findOneByUserName(principal.getName());
        model.addAttribute("user", user);

        if (user.getRole().equals("student")) {
            if (appRepository.existsByUserName(user.getUserName()))
                model.addAttribute("app", appRepository.findOneByUserName(user.getUserName()));
        } else if (user.getRole().equals("director")) {
            if (bootcampRepository.existsByBootcampDirector(user.getUserName())) {
                Bootcamp boot = bootcampRepository.findOneByBootcampDirector(user.getUserName());
                model.addAttribute("camp", boot);
                model.addAttribute("testimonials", testimonialRepository.findAllByBootcampId(boot.getBootcampId()));
            }
        } else if (user.getRole().equals("admin")) {
            model.addAttribute("apps", appRepository.findAll());
            model.addAttribute("camps", bootcampRepository.findAll());
            model.addAttribute("requests", requestRepository.findAll());
            return "adminhome";
        }
        model.addAttribute("messages", messageRepository.findAllByRecieverId(user.getUserId()));
        return "home";
    }

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
    @RequestMapping(value="/testimonial/{id}", method = RequestMethod.POST)
    public String viewBootCampPost(Model model, @PathVariable("id") long id
            , @RequestParam ("message") String message, Principal principal) {
        ArrayList<Testimonial> userTests = testimonialRepository.findAllByUserName(principal.getName());

        boolean hasTest = false;
        for (Testimonial test : userTests)
            if (test.getBootcampId() == id)
                hasTest = true;

        if (!hasTest && userRepository.findOneByUserName(principal.getName()).getRole().equals("student")) {
            model.addAttribute("bootcamp", bootcampRepository.findOne(id));
            Testimonial testimonial = new Testimonial();
            testimonial.setMessage(message);
            testimonial.setBootcampId(id);
            testimonial.setUserName(principal.getName());
            testimonialRepository.save(testimonial);
        }

        return "redirect:/";
    }

    @RequestMapping("/reject/{userName}")
    public String reject(@PathVariable("userName") String userName, Principal principal) {
        User director = userRepository.findOneByUserName(principal.getName());
        for (Request req : requestRepository.findAllByStudent(userName))
            if (req.getCamp().equals(director.getUserName())) {
                req.setStatus("rejected");
                requestRepository.save(req);
                Message message = new Message();
                message.setContent(director.getUserName() + " rejected your application");
                message.setSender(director.getUserName());
                message.setTitle("app rejected!");
                message.setRecieverId(userRepository.findByUserName(userName).getUserId());
                messageRepository.save(message);
                for (Message msg : messageRepository.findAllByRecieverId(director.getUserId()))
                    if (msg.getSender().equals(userName))
                        messageRepository.delete(msg);
            }
        return "redirect:/";
    }

    @RequestMapping("/accept/{userName}")
    public String accept(@PathVariable("userName") String userName, Principal principal) {
        User director = userRepository.findOneByUserName(principal.getName());
        for (Request req : requestRepository.findAllByStudent(userName))
            if (req.getCamp().equals(director.getUserName())) {
                req.setStatus("accepted");
                requestRepository.save(req);
                Message message = new Message();
                message.setContent(director.getUserName() + " accepted your application");
                message.setSender(director.getUserName());
                message.setTitle("app accepted!");
                message.setRecieverId(userRepository.findByUserName(userName).getUserId());
                messageRepository.save(message);

                for (Message msg : messageRepository.findAllByRecieverId(director.getUserId()))
                    if (msg.getSender().equals(userName))
                        messageRepository.delete(msg);

            }
        return "redirect:/";
    }

    @RequestMapping("/city")
    public String city(Model model, Principal principal) {
        if (userRepository.findOneByUserName(principal.getName()).getRole().equals("student")) {

            ArrayList<Bootcamp> camps = new ArrayList<>();

            for (Bootcamp boot : bootcampRepository.findAllByCity(userRepository.findOneByUserName(principal.getName()).getCity()))
                if (boot.getEnabled().equals("enabled"))
                    camps.add(boot);

            model.addAttribute("camps", camps);
            model.addAttribute("bootcamp", new Bootcamp());
            return "search";
        }
        return "redirect:/search";
    }

    @RequestMapping("/bootcamp/{id}")
    public String viewBootcamp(Model model, @PathVariable("id") long id) {
        model.addAttribute("bootcamp", bootcampRepository.findOne(id));
        model.addAttribute("testimonials", testimonialRepository.findAllByBootcampId(id));
        model.addAttribute("testimonial", new Testimonial());
        return "displaybootcamp";
    }

    @RequestMapping("/search")
    public String search(Model model) {
        model.addAttribute("bootcamp", new Bootcamp());
        model.addAttribute("camps", new ArrayList<Bootcamp>());
        return "search";
    }

    @RequestMapping("/searchTerm")
    public String searchTerm(Model model,@ModelAttribute Bootcamp search) {
        model.addAttribute("bootcamp", new Bootcamp());
        ArrayList<Bootcamp> camps =  new ArrayList<>();
        for (Bootcamp camp : bootcampRepository.findAllByDescriptionContaining(search.getBootcampName()))
            if (camp.getEnabled().equals("enabled"))
                camps.add(camp);
        for (Bootcamp camp : bootcampRepository.findAllByTopicsContaining(search.getBootcampName()))
            if (camp.getEnabled().equals("enabled"))
                camps.add(camp);
        //remove duplicates
        for (int i = camps.size() -1; i > -1; i--)
            for (int j = camps.size() -1; j > -1; j--)
                if (camps.get(j).getBootcampId() == camps.get(i).getBootcampId() && i != j) {
                    camps.remove(i);
                    j = -1;
                }
        model.addAttribute("camps", camps);
        return "search";
    }

    @RequestMapping("/apply/{name}")
    public String apply(Principal principal, @PathVariable("name") String name) {
        User user = userRepository.findOneByUserName(principal.getName());
        boolean hasReq = false;
        for (Request req : requestRepository.findAllByStudent(user.getUserName()))
            if (req.getCamp().equals(name))
                hasReq = true;
        if (!hasReq && user.getRole().equals("student")) {//so user cant apply twice
            Request request = new Request();
            Message message = new Message();
            request.setCamp(name);
            request.setStatus("pending");
            request.setStudent(user.getUserName());
            message.setContent(user.getUserName() + " applied to your bootcamp");
            message.setSender(user.getUserName());
            message.setTitle("student applied");
            message.setRecieverId(userRepository.findByUserName(name).getUserId());
            requestRepository.save(request);
            messageRepository.save(message);
        }
        return "redirect:/";
    }


    @RequestMapping("/disable/{id}")
    public String disable(Principal principal, @PathVariable("id") long id) {
        User user = userRepository.findOneByUserName(principal.getName());
        if (user.getRole().equals("admin"))
            bootcampRepository.save(bootcampRepository.findOne(id).disable());
        return "redirect:/";
    }

    @RequestMapping("/enable/{id}")
    public String enable(Principal principal, @PathVariable("id") long id) {
        User user = userRepository.findOneByUserName(principal.getName());
        if (user.getRole().equals("admin"))
            bootcampRepository.save(bootcampRepository.findOne(id).enable());
        return "redirect:/";
    }

    @RequestMapping("/view/{userName}")
    public String viewStudent(Model model, @PathVariable("userName") String userName) {
        model.addAttribute("user", userRepository.findOneByUserName(userName));
        model.addAttribute("app", appRepository.findOneByUserName(userName));
        return "viewstudent";
    }

    @RequestMapping("/saveApp")
    public String saveApp(@ModelAttribute App app, Principal principal) {
        User user = userRepository.findOneByUserName(principal.getName());
        if (appRepository.existsByUserName(user.getUserName())) {
            long id = appRepository.findOneByUserName(user.getUserName()).getAppId();
            app.setAppId(id);
            appRepository.save(app);
        }
        return "redirect:/";
    }

    @RequestMapping("/saveCamp")
    public String saveCamp(@ModelAttribute Bootcamp bootcamp, Principal principal) {
        User user = userRepository.findOneByUserName(principal.getName());
        if (bootcampRepository.existsByBootcampDirector(user.getUserName())) {
            Bootcamp temp = bootcampRepository.findOneByBootcampDirector(user.getUserName());
            bootcamp.setBootcampId(temp.getBootcampId());
            bootcamp.setEnabled(temp.getEnabled());
            bootcampRepository.save(bootcamp);
        }
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

}
