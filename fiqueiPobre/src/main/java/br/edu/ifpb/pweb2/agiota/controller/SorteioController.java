package br.edu.ifpb.pweb2.agiota.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.agiota.model.Sorteio;
import br.edu.ifpb.pweb2.agiota.repository.SorteioRepository;

@Controller
@RequestMapping("/sorteio")
public class SorteioController {
	
	 @Autowired
	   private SorteioRepository sorteioRepo;
		
	   @RequestMapping("/resultsorteio")
	   public ModelAndView getForm(ModelAndView modelAndView){
		modelAndView.addObject("sorteio", new Sorteio());
		modelAndView.setViewName("sorteio/resultsorteio"); 
		return modelAndView;
	   }

	   @RequestMapping(value="/save", method = RequestMethod.POST)
	   public String save(@Valid Sorteio sorteio, BindingResult result,
			   ModelAndView model, RedirectAttributes redirAtt) {
		if(result.hasErrors()) {
			return "sorteio/resultsorteio";
		}
		sorteioRepo.save(sorteio);
		redirAtt.addFlashAttribute("mensagem", "Sorteio cadastrado com sucesso!");
		model.addObject("sorteios", sorteioRepo.findAll());
		return "redirect:/home";
	   }
	   
	   @RequestMapping(value="/list", method = RequestMethod.GET)
	   public ModelAndView listAll(ModelAndView model) {
		model.addObject("sorteios", sorteioRepo.findAll());
		return model;
	   }
		
	   @RequestMapping("/{id}")
	   public ModelAndView getClienteById(@PathVariable(value = "id") Integer id, ModelAndView model) {
		model.addObject("sorteio", sorteioRepo.findById(id));
		return model;
	   }
	   
	   @RequestMapping("/{id}/delete")
	   public ModelAndView deleteById(@PathVariable(value = "id") Integer id, 
			   ModelAndView model,  RedirectAttributes attr) {
		sorteioRepo.deleteById(id);
		attr.addFlashAttribute("mensagem", "Sorteio "+ id.toString() +" removido com sucesso!");
		model.setViewName("redirect:/home");
		return model;
	   }
}
