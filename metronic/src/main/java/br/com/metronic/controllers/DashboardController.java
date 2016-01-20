package br.com.metronic.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.metronic.daos.NotificationDAO;
import br.com.metronic.utils.SecurityUtil;

@Controller
@Transactional
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private NotificationDAO notificationDAO;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("dashboard/dash");
		modelAndView.addObject("notifications", notificationDAO.list(securityUtil.userHasAdminRole(), 5));
		modelAndView.addObject("notificationQuantity", notificationDAO.list(securityUtil.userHasAdminRole()).size());
		return modelAndView;
	}
	
}
