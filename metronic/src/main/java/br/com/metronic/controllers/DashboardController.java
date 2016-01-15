package br.com.metronic.controllers;

import java.security.Principal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.metronic.daos.NotificationDAO;
import br.com.metronic.models.Role;
import br.com.metronic.models.User;

@Controller
@Transactional
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private NotificationDAO notificationDAO;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(Principal principal) {
		ModelAndView modelAndView = new ModelAndView("dashboard/index");
		modelAndView.addObject("notifications", notificationDAO.list(userHasAdminRole()));
		return modelAndView;
	}
	
	private boolean userHasAdminRole(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.getRoles().contains(new Role("ROLE_ADMIN"))) { return true; }
		return false;
	}
	
}
