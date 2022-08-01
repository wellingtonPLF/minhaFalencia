package br.edu.ifpb.pweb2.agiota.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.agiota.model.Apostas;
import br.edu.ifpb.pweb2.agiota.model.Favorito;
import br.edu.ifpb.pweb2.agiota.model.Sorteio;
import br.edu.ifpb.pweb2.agiota.model.Usuario;
import br.edu.ifpb.pweb2.agiota.repository.ApostaRepository;
import br.edu.ifpb.pweb2.agiota.repository.FavoritoRepository;
import br.edu.ifpb.pweb2.agiota.repository.SorteioRepository;

@Controller
@RequestMapping("/apostas")
public class ApostaController {
   
   @Autowired
   private ApostaRepository apostaRepo;
   @Autowired
   private SorteioRepository sorteioRepo;
   @Autowired
   private FavoritoRepository favoritoRepo;
 
   private Set<Integer> lista = new LinkedHashSet<Integer>(10);
   
   @RequestMapping("/minhaAposta")
   public ModelAndView getForm(ModelAndView modelAndView, HttpSession session){
	Usuario u = (Usuario) session.getAttribute("usuario");
	if (lista.size() != 60) {
		for (Integer i = 1; i <= 60; i++) {
			lista.add(i);
		}
	}
	
	Apostas aposta = new Apostas();
	modelAndView.addObject("aposta", aposta);
	modelAndView.addObject("favorito", new Favorito());
	modelAndView.addObject("favoritos", favoritoRepo.getFavoritoByIdUser(u.getId()));
	modelAndView.addObject("numbers", lista);
	modelAndView.setViewName("apostas/minhaAposta"); 
	return modelAndView;
   }
   
   @RequestMapping(value="/choose", method = RequestMethod.POST)
   public ModelAndView choose(Apostas aposta, Favorito favorito, ModelAndView model, HttpSession session) {
	this.regra(aposta);
	Usuario u = (Usuario) session.getAttribute("usuario");
	model.addObject("favoritos", favoritoRepo.getFavoritoByIdUser(u.getId()));
	if (favorito.getId() != null) {
		aposta.setAposta(favoritoRepo.findById(favorito.getId()).orElse(null).getNumeros());
	}
	model.addObject("aposta", aposta);
	model.addObject("numbers", lista);
	model.setViewName("apostas/minhaAposta"); 
	return model;
   }
   
   @RequestMapping(value="/save", method = RequestMethod.POST)
   public ModelAndView save(Apostas aposta, Favorito favorito, ModelAndView model, HttpSession session) {
	Usuario u = (Usuario) session.getAttribute("usuario");
	if (aposta.getAposta().size() >=6 && aposta.getAposta().size() <=10 ) {
		Sorteio s = sorteioRepo.getSorteioByDate();
		aposta.setCliente(u);
		aposta.setSorteio(s);
		apostaRepo.save(aposta);
		model.addObject("success", "Aposta concluida com sucesso!");
	}
	else {
		model.addObject("fail", "A aposta deve possuir de 6 a 10 dezenas.");
	}
	this.regra(aposta);
	model.addObject("favoritos", favoritoRepo.getFavoritoByIdUser(u.getId()));
	model.addObject("aposta", aposta);
	model.addObject("numbers", lista);
	model.setViewName("apostas/minhaAposta");
	return model;
   }
   
   @RequestMapping(value="/final", method = RequestMethod.GET)
   public ModelAndView sorteioApostaResult(ModelAndView model, HttpSession session) {
	Usuario u = (Usuario) session.getAttribute("usuario");
	ArrayList<Apostas> allApostas = apostaRepo.getApostasByIdUser(u.getId());
	List<Integer> numeros_Sorteio = sorteioRepo.getSorteioByDate().getResultado();
	Boolean fracasso = true;
	for (Integer i = 0; i < allApostas.size(); i ++) {
		fracasso = compare(numeros_Sorteio, allApostas.get(i).getAposta());
		if (fracasso  == false) {
			break;
		}
	}
	model.addObject("finalResult", fracasso);
	model.setViewName("apostas/resultAposta");
	return model;
   }
   
   @RequestMapping(value="/list", method = RequestMethod.GET)
   public ModelAndView listAll(ModelAndView model, HttpSession session) {
	Usuario u = (Usuario) session.getAttribute("usuario");
	model.addObject("apostas", apostaRepo.getApostasByIdUser(u.getId()));
	model.setViewName("apostas/historico");
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
   
   public Boolean compare(List<Integer> a, List<Integer> b ) {
	   List<Integer> result = new ArrayList<Integer>();
	   for (Integer i = 0; i < b.size(); i++) {
		   for (Integer j = 0; j < a.size(); j++) {
			   if(a.get(j) == b.get(i)) {
				   result.add(1);
			   }
		   }
	   }
	   return result.size() != 6;
   }
}
