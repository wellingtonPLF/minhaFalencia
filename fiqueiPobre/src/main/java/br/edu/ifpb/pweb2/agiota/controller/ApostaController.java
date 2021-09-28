package br.edu.ifpb.pweb2.agiota.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.agiota.model.Apostas;
import br.edu.ifpb.pweb2.agiota.repository.ApostaRepository;

@Controller
@RequestMapping("/apostas")
public class ApostaController {
	
   @Autowired
   private ApostaRepository apostaRepo;
  
   @RequestMapping("/minhaAposta")
   public ModelAndView getForm(ModelAndView modelAndView){
	modelAndView.addObject("aposta", new Apostas());
	modelAndView.setViewName("apostas/minhaAposta"); 
	return modelAndView;
   }

   @RequestMapping(value="/save", method = RequestMethod.POST)
   public String save(Apostas aposta, ModelAndView model, RedirectAttributes redirAtt) {
	apostaRepo.save(aposta);
	redirAtt.addFlashAttribute("mensagem", "Aposta cadastrada com sucesso!");
	model.addObject("apostas", apostaRepo.findAll());
	return "redirect:/home";
   }
   
   @RequestMapping(value="/list", method = RequestMethod.GET)
   public ModelAndView listAll(ModelAndView model) {
	model.addObject("apostas", apostaRepo.findAll());
	return model;
   }
	
   @RequestMapping("/{id}")
   public ModelAndView getClienteById(@PathVariable(value = "id") Integer id, ModelAndView model) {
	model.addObject("aposta", apostaRepo.findById(id));
	return model;
   }
   
   @RequestMapping("/{id}/delete")
   public ModelAndView deleteById(@PathVariable(value = "id") Integer id, 
		   ModelAndView model,  RedirectAttributes attr) {
	apostaRepo.deleteById(id);
	attr.addFlashAttribute("mensagem", "Aposta "+ id.toString() +" removido com sucesso!");
	model.setViewName("redirect:/home");
	return model;
   }
}
