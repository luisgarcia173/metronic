package br.com.metronic.controllers;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
public class AdminController {

	@RequestMapping(value = "/adminControl", method = RequestMethod.GET, name="adminControl")
	public ModelAndView adminControl() {
		ModelAndView modelAndView = new ModelAndView("admin/settings");
		return modelAndView;
	}

}
