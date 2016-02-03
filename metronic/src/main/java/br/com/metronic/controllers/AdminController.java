package br.com.metronic.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.metronic.daos.UserDAO;

@Controller
@Transactional
public class AdminController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/adminControl", method = RequestMethod.GET, name="adminControl")
	public ModelAndView adminControl() {
		ModelAndView modelAndView = new ModelAndView("admin/settings");
		modelAndView.addObject("users",userDAO.listUsers());
		return modelAndView;
	}

}
