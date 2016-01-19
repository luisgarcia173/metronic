package br.com.metronic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.metronic.daos.NotificationDAO;
import br.com.metronic.utils.SecurityUtil;

@Controller
public class HomeController {
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/dashboard";
	}
	
	@RequestMapping(value = "/notification", method = RequestMethod.GET, name="notification")
	public ModelAndView notifications() {
		ModelAndView modelAndView = new ModelAndView("notification/index");
		modelAndView.addObject("notifications", notificationDAO.list(securityUtil.userHasAdminRole()));
		return modelAndView;
	}
	
}
