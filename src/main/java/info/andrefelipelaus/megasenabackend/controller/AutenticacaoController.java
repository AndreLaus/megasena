package info.andrefelipelaus.megasenabackend.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import info.andrefelipelaus.megasenabackend.config.seguranca.TokenService;
import info.andrefelipelaus.megasenabackend.controller.dto.TokenDto;
import info.andrefelipelaus.megasenabackend.controller.form.LoginForm;
import info.andrefelipelaus.megasenabackend.controller.form.UsuarioForm;
import info.andrefelipelaus.megasenabackend.model.Usuario;
import info.andrefelipelaus.megasenabackend.model.repository.UsuarioRepository;

@RestController
@RequestMapping("/user")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostMapping("/auth")
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.add("x-access-token", token);
			
			return ResponseEntity.ok()
					.headers(responseHeaders)
					.body(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
	
	@GetMapping("/exists/{email}")
	public ResponseEntity<Boolean> emailCadastrado(@PathVariable String email) {
		
		Optional<Usuario> optional = usuarioRepository.findByEmail(email);
		if (optional.isPresent()) {
			return ResponseEntity.ok(Boolean.TRUE);
		}
		
		return ResponseEntity.ok(Boolean.FALSE);
	}
	
	@PostMapping("/reg")
	@Transactional
	public ResponseEntity<?> registrar(@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder) {
		
		Optional<Usuario> optional = usuarioRepository.findByEmail(form.getEmail());
		if (!optional.isPresent()) {
			Usuario usuario = form.converter();
			usuarioRepository.save(usuario);
			URI uri = uriBuilder.path("/user/auth").build().toUri();
			return ResponseEntity.created(uri).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("e-mail já cadastrado");
		}
		
	}
	
	
}
