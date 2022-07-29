package br.edu.ifpb.pweb2.agiota.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
   private Set<Integer> lista = new LinkedHashSet<Integer>(10);
   
   @RequestMapping("/minhaAposta")
   public ModelAndView getForm(ModelAndView modelAndView){
	if (lista.size() != 60) {
		for (Integer i = 1; i <= 60; i++) {
			lista.add(i);
		}
	}
	   
	Apostas aposta = new Apostas();
	modelAndView.addObject("aposta", aposta);
	modelAndView.addObject("numbers", lista);
	modelAndView.setViewName("apostas/minhaAposta"); 
	return modelAndView;
   }
   
   @RequestMapping(value="/choose", method = RequestMethod.POST)
   public ModelAndView choose(Apostas aposta, ModelAndView model) {
	this.regra(aposta);
	model.addObject("aposta", aposta);
	model.addObject("numbers", lista);
	model.setViewName("apostas/minhaAposta"); 
	return model;
   }
   
   @RequestMapping(value="/save", method = RequestMethod.POST)
   public ModelAndView save(Apostas aposta, ModelAndView model) {
	if (aposta.getAposta().size() >=6 && aposta.getAposta().size() <=10 ) {
		//apostaRepo.save(aposta);
		model.addObject("success", "Aposta concluida com sucesso!");
	}
	else {
		model.addObject("fail", "A aposta deve possuir de 6 a 10 dezenas.");
	}
	this.regra(aposta);
	model.addObject("aposta", aposta);
	model.addObject("numbers", lista);
	model.setViewName("apostas/minhaAposta");
	return model;
   }
   
   @RequestMapping(value="/favorite", method = RequestMethod.POST)
   public ModelAndView favorite(Apostas aposta, ModelAndView model, RedirectAttributes attr) {
	if(aposta.getAposta().size() >=6 && aposta.getAposta().size() <=10) {
	   //apostaRepo.save(aposta);
	   attr.addFlashAttribute("favorite", "Adicionado aos favoritos!");
	}
	else {
	   attr.addFlashAttribute("fail", "A aposta deve possuir de 6 a 10 dezenas.");
	}
	model.setViewName("redirect:minhaAposta");
	return model;
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
   
   public void regra(Apostas aposta) {
	   if (aposta.getAposta().size() > 10) {
		  while(aposta.getAposta().size() > 10) {
		    aposta.getAposta().remove(aposta.getAposta().size() - 1);
		  }
	   }
   }
}
