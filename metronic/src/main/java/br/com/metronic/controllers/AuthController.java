package br.com.metronic.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.metronic.daos.UserDAO;
import br.com.metronic.models.Role;
import br.com.metronic.models.User;

@Controller
@Transactional
public class AuthController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET, name="login")
	public ModelAndView login(@RequestParam(value = "to", required = false) String action, User user) {
		ModelAndView modelAndView = new ModelAndView("auth/login");
		if (StringUtils.hasText(action)) {
			switch (action) {
			case "error":
				modelAndView.addObject("error", "Please, try a valid username and password.");
				break;
			case "logout":
				modelAndView.addObject("msg", "You've been logged out successfully.");
				break;
			case "signup":
				modelAndView.addObject("msg", "Please, make a try in your first sign in.");
				break;
			case "forget-ok":
				modelAndView.addObject("msg", "You've been logged out successfully.");
				break;
			case "forget-err":
				modelAndView.addObject("error", "Sorry, the email informed was not found.");
				break;
			default:
				break;
			}
		}
		return modelAndView;
	}

	@RequestMapping(method=RequestMethod.POST, name="signup", value="/signup")
	public ModelAndView signup(User user) {
		user.getRoles().add(new Role("ROLE_USER"));
		userDAO.saveUser(user);
		return login("signup", user);
	}
	
	@RequestMapping(method=RequestMethod.POST, name="forget", value="/forget")
	public ModelAndView forget(User user) {
		String status = "forget-ok";
		try {
			userDAO.loadUserByUsername(user.getUsername());
			//TODO send email
		} catch (UsernameNotFoundException e) {
			status = "forget-err";
		}
		return login(status, user);
	}
	
}
