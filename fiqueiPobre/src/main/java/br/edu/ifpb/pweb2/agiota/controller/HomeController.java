package br.edu.ifpb.pweb2.agiota.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.agiota.model.Usuario;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping(method = RequestMethod.GET) 
	public String showHomepage() {
		return "home";
	}
	
	@RequestMapping("/home")
	   public ModelAndView getNegocio(ModelAndView modelAndView){
		modelAndView.setViewName("negocio/cliente"); 
		return modelAndView;
	}
}
