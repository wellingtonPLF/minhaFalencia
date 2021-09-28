package br.edu.ifpb.pweb2.agiota.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.pweb2.agiota.model.Usuario;
import br.edu.ifpb.pweb2.agiota.repository.UsuarioRepository;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getForm(ModelAndView modelAndView) {
		modelAndView.setViewName("login/login");
		modelAndView.addObject("usuario", new Usuario());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView valide(Usuario usuario, HttpSession session, ModelAndView modelAndView, RedirectAttributes redirectAttts) {
		if ((usuario = this.isValido(usuario)) != null) {
			session.setAttribute("usuario", usuario);
			modelAndView.setViewName("redirect:/home");			
		} else {
			redirectAttts.addFlashAttribute("mensagem", "Login e/ou senha inválidos!");
			modelAndView.setViewName("redirect:/login");
		}
		return modelAndView;
	}
	
	@RequestMapping("/out")
	public ModelAndView logout(ModelAndView mav, HttpSession session) {
		session.invalidate();
		mav.setViewName("redirect:/login");
		return mav;
	}
	
	private Usuario isValido(Usuario usuario) {
		Usuario usuarioBD = usuarioRepo.findByNome(usuario.getNome());
		boolean valido = false;
		if (usuarioBD != null) {
			valido = true;
		} 
		return valido ? usuarioBD : null;
	}
}
