package br.edu.ifpb.pweb2.agiota.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
import br.edu.ifpb.pweb2.agiota.model.Sorteio;
import br.edu.ifpb.pweb2.agiota.repository.ApostaRepository;
import br.edu.ifpb.pweb2.agiota.repository.SorteioRepository;

@Controller
@RequestMapping("/sorteio")
public class SorteioController {
	
	   @Autowired
	   private SorteioRepository sorteioRepo;
	   
	   @Autowired
	   private ApostaRepository apostaRepo;
		
	   @RequestMapping("/resultsorteio")
	   public ModelAndView getForm(ModelAndView modelAndView){
		modelAndView.setViewName("sorteio/resultsorteio");
		return modelAndView;
	   }
	   
	   // public String save(@Valid Sorteio sorteio, BindingResult result,
	   
	   @RequestMapping(value="/save", method = RequestMethod.POST)
	   public ModelAndView save(ModelAndView model) {
		Sorteio sorteio = sorteioRepo.getSorteioByDate();
		if(sorteio != null) {
			model.addObject("warning", "Um sorteio já foi cadastrado! Veja abaixo:");
			model.addObject("dataSorteio", sorteio.getHoraSorteio());
		}
		else {
			sorteio = sortear();
			sorteioRepo.save(sorteio);
			model.addObject("dataSorteio", LocalDate.now());
			model.addObject("mensagem", "Sorteio cadastrado com sucesso!");
		}
		model.addObject("sorteio", sorteio);
		model.setViewName("sorteio/resultsorteio");
		return model;
	   }
	   
	   @RequestMapping(value="/simulador", method = RequestMethod.GET)
	   public ModelAndView simular(ModelAndView model) {
		model.setViewName("sorteio/simularSorteio");
		return model;
	   }
	   
	   @RequestMapping(value="/simulation", method = RequestMethod.POST)
	   public ModelAndView simulation(ModelAndView model) {
	     Sorteio sorteio = sorteioRepo.getSorteioByDate();
		if(sorteio != null) {
		  model.addObject("warning", "Um sorteio já foi cadastrado! Veja abaixo:");
		  model.addObject("dataSorteio", sorteio.getHoraSorteio());
		}
		else {
		  List<Integer> lista = simulacion();
		  sorteio = new Sorteio(lista, new Date(), 1.00 );
		  sorteioRepo.save(sorteio);
		  model.addObject("dataSorteio", LocalDate.now());
		  model.addObject("mensagem", "Sorteio cadastrado com sucesso!");
		}
		model.addObject("sorteio", sorteio);
		model.setViewName("sorteio/simularSorteio");
		return model;
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
	   
	   @RequestMapping(value="/deleteLast")
	   public ModelAndView deleteLast(ModelAndView model, RedirectAttributes attr) {
		Sorteio sorteio = sorteioRepo.getSorteioByDate();
		ArrayList<Apostas> apostas = apostaRepo.getApostasBySorteio(sorteio.getId());
		if (apostas.size() > 0) {
			for(Integer i = 0; i < apostas.size(); i++) {
				if(apostas.get(i).getAposta().size() > 0) {
					apostas.get(i).setAposta(new ArrayList<Integer>());
					apostaRepo.save(apostas.get(i));
				}
				apostaRepo.deleteById(apostas.get(i).getId());
			}
		}
		sorteioRepo.deleteById(sorteio.getId());
		attr.addFlashAttribute("mensagem", "Sorteio removido com sucesso!");
		model.setViewName("redirect:/sorteio/simulador");
		return model;
	   }
	   
	   @RequestMapping("/{id}/delete")
	   public ModelAndView deleteById(@PathVariable(value = "id") Integer id, 
			   ModelAndView model,  RedirectAttributes attr) {
		sorteioRepo.deleteById(id);
		attr.addFlashAttribute("mensagem", "Sorteio removido com sucesso!");
		model.setViewName("redirect:/sorteio/simularSorteio");
		return model;
	   }
	   
	   public Sorteio sortear() {
		   Sorteio sorteio = new Sorteio();
		   List<Integer> numeroSorteado = new ArrayList<Integer>(6);
		   while(numeroSorteado.size() < 6) {
			   numeroSorteado.add(ThreadLocalRandom.current().nextInt(1,60));
		   }
		   sorteio.setResultado(numeroSorteado);
		   sorteio.setHoraSorteio(new Date());
		   sorteio.setPrecoPremio(125.45);
		   return sorteio;
	   }
	   
	   public List<Integer> simulacion() {
		   List<Integer> lista = new ArrayList<Integer>();
		   for (Integer i = 1; i <= 6; i++) {
			   lista.add(i);
		   }
		   return lista;
	   }
}
