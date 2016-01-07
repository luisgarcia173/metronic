package br.com.metronic.controllers;

import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;

@Controller
@Transactional
public class UsersController {


	/*
	@RequestMapping(method=RequestMethod.POST, name="saveRole", value="/role")
	public ModelAndView saveRole(@Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
			return form(null, role);
		}
		userDAO.saveRole(role);
		redirectAttributes.addFlashAttribute("sucesso", "Role registered successfully"); //valid for this request
		return new ModelAndView("redirect:usuarios");
	}
	*/

}
