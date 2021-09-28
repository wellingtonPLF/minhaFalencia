package br.edu.ifpb.pweb2.agiota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import br.edu.ifpb.pweb2.agiota.filter.LoginFilter;

@SpringBootApplication
public class MuitoAzarApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuitoAzarApplication.class, args);
	}
	
	@Bean
	 public FilterRegistrationBean <LoginFilter> filterRegistrationBean() {
	  FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<LoginFilter>();
	  LoginFilter customURLFilter = new LoginFilter();

	  registrationBean.setFilter(customURLFilter);
	  //define quais URLs o filtro interceptará (se não informar, intercepta todas e, daí, fica em loop infinito)
	  registrationBean.addUrlPatterns("/usuarios/list");
	  registrationBean.addUrlPatterns("/apostas/*");
	  registrationBean.addUrlPatterns("/sorteio/*");
	  registrationBean.addUrlPatterns("/home");
	  //define a precedência do filtro (primeiro, caso aparece outro depois)
	  registrationBean.setOrder(1); 
	  return registrationBean;
	 }
}
