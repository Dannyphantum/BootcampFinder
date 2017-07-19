package bootcampFinder.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bootcampFinder.models.Message;
import bootcampFinder.models.User;
import bootcampFinder.repositories.MessageRepository;
import bootcampFinder.repositories.UserRepository;

@Controller
public class EmailController {

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	
	@RequestMapping(value="/email", method= RequestMethod.GET)
	public String getEmail(Model model){
		model.addAttribute("message", new Message());
		model.addAttribute("user", new User());
		return "email";
	}
	
	@RequestMapping(value="/email", method= RequestMethod.POST)
	public String sendEmail(Model model, Principal principal, @RequestParam ("content") String content, 
			@RequestParam ("userName") String username, @RequestParam ("title") String title){
		model.addAttribute("user", new User());
		model.addAttribute("message", new Message());
		Message message = new Message();
		message.setSender(principal.getName());
		message.setContent(content);
		User reciever = userRepository.findOneByUserName(username);
		message.setRecieverId(reciever.getUserId());
		messageRepository.save(message);
		System.out.println(message.getContent());
		return "email";
		//Message message = messageRepository.findOneByRecieverId(long);
		
	}
}
