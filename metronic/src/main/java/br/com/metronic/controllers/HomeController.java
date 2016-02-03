package br.com.metronic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import br.com.metronic.daos.NotificationDAO;
import br.com.metronic.models.Notification;
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
		ModelAndView modelAndView = new ModelAndView("notification/feed");
		
		List<Notification> notifications = notificationDAO.list(securityUtil.userHasAdminRole());
		
		modelAndView.addObject("notifications", Lists.newArrayList(Iterables.limit(notifications, 5)));
		modelAndView.addObject("allNotifications", notifications);
		modelAndView.addObject("notificationQuantity", notifications.size());
		
		return modelAndView;
	}
	
}
