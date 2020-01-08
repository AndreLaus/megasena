package info.andrefelipelaus.megasenabackend.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import info.andrefelipelaus.megasenabackend.controller.dto.CartaoApostaDto;
import info.andrefelipelaus.megasenabackend.controller.dto.JogoDto;
import info.andrefelipelaus.megasenabackend.controller.form.CartaoApostaForm;
import info.andrefelipelaus.megasenabackend.model.CartaoAposta;
import info.andrefelipelaus.megasenabackend.model.Jogo;
import info.andrefelipelaus.megasenabackend.model.JogoKey;
import info.andrefelipelaus.megasenabackend.model.Usuario;
import info.andrefelipelaus.megasenabackend.model.repository.CartaoApostaRepository;
import info.andrefelipelaus.megasenabackend.model.repository.JogoRepository;

/**
 * 
 * @author André Felipe Laus
 * 
 * Todo o tratamento de CRUD para o cartão de aposta e para os jogos deste cartão
 *
 */
@RestController
@RequestMapping("/cartaoAposta")
public class CartaoApostaController {

	@Autowired
	private CartaoApostaRepository cartaoApostaRepository;
	
	@Autowired
	private JogoRepository jogoRepository;
	
	@GetMapping
	public Page<CartaoApostaDto> lista(@AuthenticationPrincipal Usuario usuario, @PageableDefault(page=0, size=10) Pageable paginacao) {
	
		Page<CartaoAposta> cartoes = cartaoApostaRepository.findByUsuarioId(usuario.getId() ,paginacao);
		return CartaoApostaDto.converter(cartoes);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<CartaoApostaDto> detalhar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
		Optional<CartaoAposta> optional = cartaoApostaRepository.findByIdAndUsuarioId(id, usuario.getId());
		if (optional.isPresent()) {
			return ResponseEntity.ok(new CartaoApostaDto(optional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CartaoApostaDto> cadastrar(@AuthenticationPrincipal Usuario usuario, @RequestBody @Valid CartaoApostaForm form, UriComponentsBuilder uriBuilder) {
		CartaoAposta cartaoAposta = form.converter(usuario);
		cartaoApostaRepository.save(cartaoAposta);
		
		form.converterJogos(cartaoAposta);
		
		URI uri = uriBuilder.path("/cartaoAposta/{id}").buildAndExpand(cartaoAposta.getId()).toUri();
		return ResponseEntity.created(uri).body(new CartaoApostaDto(cartaoAposta));
		
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CartaoApostaDto> atualizar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario, @RequestBody @Valid CartaoApostaForm form) {
		Optional<CartaoAposta> optional = cartaoApostaRepository.findByIdAndUsuarioId(id,usuario.getId());
		if (optional.isPresent()) {
			CartaoAposta cartao = form.atualizar(id, cartaoApostaRepository, jogoRepository);
			return ResponseEntity.ok(new CartaoApostaDto(cartao));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
		Optional<CartaoAposta> optional = cartaoApostaRepository.findByIdAndUsuarioId(id, usuario.getId());
		if (optional.isPresent()) {
			cartaoApostaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
		
	}
	
	
	@DeleteMapping("/{cartaoId}/jogo/{posicao}")
	@Transactional
	public ResponseEntity<JogoDto> removerJogo(@PathVariable Long cartaoId, @PathVariable Short posicao, @AuthenticationPrincipal Usuario usuario ) {
		JogoKey jogoKey = new JogoKey(cartaoId, posicao);
		Optional<Jogo> optional = jogoRepository.findByIdAndUsuarioId(jogoKey, usuario.getId());
		if (optional.isPresent()) {
			jogoRepository.deleteById(jogoKey);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
}
