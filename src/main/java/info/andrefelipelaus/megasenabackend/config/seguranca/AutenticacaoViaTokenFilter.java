package info.andrefelipelaus.megasenabackend.config.seguranca;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import info.andrefelipelaus.megasenabackend.model.Usuario;
import info.andrefelipelaus.megasenabackend.model.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

//		HttpServletRequest req = (HttpServletRequest) request;
//	    HttpServletResponse res = (HttpServletResponse) response;

//	    res.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//	    res.setHeader("Access-Control-Allow-Credentials", "true");
//	    res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//	    res.setHeader("Access-Control-Max-Age", "3600");
//	    res.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
//		
		
		String token = recuperarToken(request);

		boolean valido = tokenService.isTokenValido(token);

		if (valido) {
			autenticarCliente(token);
		}
		

		filterChain.doFilter(request, response);

	}

	private void autenticarCliente(String token) {
		Long idUsuario = this.tokenService.getIdUsuario(token);
		Usuario usuario = this.usuarioRepository.findById(idUsuario).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null,
				usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}

		return token.substring(7, token.length());
	}

}
