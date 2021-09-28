package br.edu.ifpb.pweb2.agiota.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import br.edu.ifpb.pweb2.agiota.model.Usuario;

@Component
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("LoginFilter------------------------------------");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpRequest.getSession(false);

		String uri = httpRequest.getRequestURI();

		if (httpSession != null) {
			Usuario loginUser = (Usuario) httpSession.getAttribute("usuario");
			if (loginUser == null) {
				redirectLogin(httpRequest, httpResponse, uri);
				return;
			} else {
				chain.doFilter(request, response);
			}
		} else {
			redirectLogin(httpRequest, httpResponse, uri);
		}

	}

	private void redirectLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String uri) throws IOException {
		String baseUrl = httpRequest.getContextPath();
		String paginaLogin = baseUrl + "/login";
		httpResponse.sendRedirect(httpResponse.encodeRedirectURL(paginaLogin));
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}