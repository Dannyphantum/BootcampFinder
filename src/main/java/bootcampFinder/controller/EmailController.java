package bootcampFinder.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

import bootcampFinder.repositories.BootcampRepository;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import bootcampFinder.models.Message;
import bootcampFinder.models.User;
import bootcampFinder.repositories.MessageRepository;
import bootcampFinder.repositories.UserRepository;

import javax.mail.internet.InternetAddress;

@Controller
public class EmailController {

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	BootcampRepository bootcampRepository;

	@Autowired
	public EmailService emailService;

	/*@RequestMapping(value="/email", method= RequestMethod.GET)
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
		
	}*/

	@RequestMapping("/email/{id}")
	public String emailAnId(Model model, @PathVariable("id") long id, Principal principal) {
		Message msg = new Message();
		msg.setSender(bootcampRepository.findOne(id).getBootcampDirector());
		model.addAttribute("message", msg);
		return "email";
	}

	@RequestMapping("/emailstudent/{id}")
	public String emailAStudent(Model model, @PathVariable("id") long id, Principal principal) {
		Message msg = new Message();
		msg.setSender(userRepository.findOne(id).getUserName());
		model.addAttribute("message", msg);
		return "email";
	}

	@RequestMapping("/email")
	public String sendEmail(@ModelAttribute Message message, Principal principal) {
		String topic = message.getTitle();
		String content = message.getContent();
		String emailAddress = userRepository.findOneByUserName(message.getSender()).getEmail();
		String sender = principal.getName();
		try {
			final Email email = DefaultEmail.builder()
                    .from(new InternetAddress("hidden@email.net", sender))
                    .to(Lists.newArrayList(new InternetAddress(emailAddress, "bootcamp finder user")))
                    .subject(topic)
                    .body(content)
                    .encoding("UTF-8").build();
			emailService.send(email);


		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}


	public void sendEmailWithoutTemplating(){

	}



}
