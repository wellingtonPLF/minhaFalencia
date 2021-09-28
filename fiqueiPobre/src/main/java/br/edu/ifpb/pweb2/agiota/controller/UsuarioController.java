package br.edu.ifpb.pweb2.agiota.controller;

import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.agiota.model.Usuario;
import br.edu.ifpb.pweb2.agiota.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

   @Autowired
   private UsuarioRepository usuarioRepo;
	
   @RequestMapping("/form")
   public ModelAndView getForm(ModelAndView modelAndView){
	modelAndView.addObject("usuario", new Usuario());
	modelAndView.setViewName("usuarios/form"); 
	return modelAndView;
   }

   @RequestMapping(value="/save", method = RequestMethod.POST)
   public String save(@Valid Usuario usuario, BindingResult result,
		   ModelAndView model, RedirectAttributes redirAtt) {
	if(result.hasErrors()) {
		return "usuarios/form";
	}
	usuarioRepo.save(usuario);
	redirAtt.addFlashAttribute("mensagem", "Usuario cadastrado com sucesso!");
	model.addObject("usuarios", usuarioRepo.findAll());
	return "redirect:/";
   }
   
   @RequestMapping(value="/list", method = RequestMethod.GET)
   public ModelAndView listAll(ModelAndView model) {
	model.addObject("usuarios", usuarioRepo.findAll());
	model.setViewName("usuarios/list");
	return model;
   }
	
   @RequestMapping("/{id}")
   public ModelAndView getClienteById(@PathVariable(value = "id") Integer id, ModelAndView model) {
	model.addObject("usuario", usuarioRepo.findById(id));
	model.setViewName("usuarios/id");
	return model;
   }
   
   @RequestMapping("/{id}/delete")
   public ModelAndView deleteById(@PathVariable(value = "id") Integer id, 
		   ModelAndView model,  RedirectAttributes attr) {
	usuarioRepo.deleteById(id);
	attr.addFlashAttribute("mensagem", "Usuario "+ id.toString() +" removido com sucesso!");
	model.setViewName("redirect:/usuarios/list");
	return model;
   }
}


