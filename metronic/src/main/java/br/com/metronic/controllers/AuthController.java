package br.com.metronic.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
			case "signup-ok":
				modelAndView.addObject("msg", "Please, make a try in your first sign in.");
				break;
			case "signup-err":
				modelAndView.addObject("error", "User already exists, did you forget your password?");
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
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET, name="logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return login("logout", null);
	}

	@RequestMapping(method=RequestMethod.POST, name="signup", value="/signup")
	public ModelAndView signup(User user) {
		if (userDAO.loadUserByUsername(user.getUsername()) != null) {
			return login("signup-err", user);	
		}
		
		//TODO intercept by aspect the user registration as ("New User Registered.", NEW)
		user.getRoles().add(new Role("ROLE_USER"));
		userDAO.saveUser(user);
		return login("signup-ok", user);
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
