package br.edu.ifpb.pweb2.agiota.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.agiota.model.Apostas;
import br.edu.ifpb.pweb2.agiota.model.Favorito;
import br.edu.ifpb.pweb2.agiota.model.Usuario;
import br.edu.ifpb.pweb2.agiota.repository.FavoritoRepository;

@Controller
@RequestMapping("/favoritos")
public class FavoritoController {
	@Autowired
	private FavoritoRepository favoritoRepo;
	
	@RequestMapping(value="/favorite", method = RequestMethod.POST)
	  public ModelAndView favorite(Apostas aposta, ModelAndView model, RedirectAttributes attr, HttpSession session ) {
	  if(aposta.getAposta().size() >=6 && aposta.getAposta().size() <=10) {
 		 Usuario u = (Usuario) session.getAttribute("usuario");
	     favoritoRepo.save(new Favorito(u, aposta.getAposta()));
	     attr.addFlashAttribute("favorite", "Adicionado aos favoritos!");
	  }
	  else {
	     attr.addFlashAttribute("fail", "A aposta deve possuir de 6 a 10 dezenas.");
	  }
	  model.setViewName("redirect:/apostas/minhaAposta");
	  return model;
   }
	
   @RequestMapping(value="/list", method = RequestMethod.GET)
	 public ModelAndView listAll(ModelAndView model, HttpSession session) {
	 Usuario u = (Usuario) session.getAttribute("usuario");
	 model.addObject("favoritos", favoritoRepo.getFavoritoByIdUser(u.getId()));
	 model.setViewName("apostas/favorite");
	 return model;
   }
}
