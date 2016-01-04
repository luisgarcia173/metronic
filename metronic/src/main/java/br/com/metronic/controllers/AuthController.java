package br.com.metronic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

	@RequestMapping(value = "/login", method = RequestMethod.GET, name="login")
	public ModelAndView login(@RequestParam(value = "to", required = false) String action) {

		ModelAndView modelAndView = new ModelAndView("auth/login");
		if (StringUtils.hasText(action)) {
			if (action.equalsIgnoreCase("error")) {
				modelAndView.addObject("error", "Invalid username and password!");
			} else {
				if (action.equalsIgnoreCase("logout")) {
					modelAndView.addObject("msg", "You've been logged out successfully.");
				}
			}
		}

		return modelAndView;
	}

}
