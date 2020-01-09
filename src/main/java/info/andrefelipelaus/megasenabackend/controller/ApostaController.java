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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import info.andrefelipelaus.megasenabackend.controller.dto.ApostaDto;
import info.andrefelipelaus.megasenabackend.controller.form.ApostaForm;
import info.andrefelipelaus.megasenabackend.model.Aposta;
import info.andrefelipelaus.megasenabackend.model.ApostaKey;
import info.andrefelipelaus.megasenabackend.model.CartaoAposta;
import info.andrefelipelaus.megasenabackend.model.Concurso;
import info.andrefelipelaus.megasenabackend.model.Usuario;
import info.andrefelipelaus.megasenabackend.model.repository.ApostaRepository;
import info.andrefelipelaus.megasenabackend.model.repository.CartaoApostaRepository;
import info.andrefelipelaus.megasenabackend.model.repository.ConcursoRepository;

@RestController("/aposta")
public class ApostaController {

	@Autowired
	private ApostaRepository apostaRepository;
	
	@Autowired
	private CartaoApostaRepository cartaoRepository;
	
	private ConcursoRepository concursoRepository;
	
	@GetMapping
	public Page<ApostaDto> lista(@AuthenticationPrincipal Usuario usuario, @PageableDefault(page = 0, size = 10) Pageable paginacao) {
		
		Page<Aposta> apostas = apostaRepository.findByIdUsuario(usuario, paginacao);
		return ApostaDto.converter(apostas);
		
	}
	
	@GetMapping("/cartao/{cartaoId}/concurso/{concursoId}")
	public ResponseEntity<ApostaDto> detalhar(@PathVariable Long cartaoId, 
											  @PathVariable Integer concursoId, 
											  @AuthenticationPrincipal Usuario usuario) {
		
		Optional<CartaoAposta> cartao = cartaoRepository.findByIdAndUsuarioId(cartaoId, usuario.getId());
		Optional<Concurso> concurso = concursoRepository.findById(concursoId);
		
		if (cartao.isPresent() && concurso.isPresent()) {
			
			ApostaKey apostaKey = new ApostaKey(usuario, cartao.get(), concurso.get());
			Optional<Aposta> aposta = apostaRepository.findById(apostaKey);
			if (aposta.isPresent()) {
				return ResponseEntity.ok(new ApostaDto(aposta.get()));
			}
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<ApostaDto> cadastrar(@AuthenticationPrincipal Usuario usuario, 
											   @RequestBody @Valid ApostaForm form, 
											   UriComponentsBuilder uriBuilder) {
		
		Aposta aposta = form.converter(usuario, cartaoRepository, concursoRepository);
		apostaRepository.save(aposta);
		
		URI uri = uriBuilder.path("aposta/cartao/{cartaoId}/concurso/{concursoId}")
							.buildAndExpand(aposta.getId().getCartao().getId(), aposta.getId().getConcurso().getNumero())
							.toUri();
		return ResponseEntity.created(uri).body(new ApostaDto(aposta));
		
	}
	
	@DeleteMapping("/cartao/{cartaoId}/concurso/{concursoId}")
	public ResponseEntity<?> remover(@PathVariable Long cartaoId, 
											  @PathVariable Integer concursoId, 
											  @AuthenticationPrincipal Usuario usuario) {
		
		Optional<CartaoAposta> cartao = cartaoRepository.findByIdAndUsuarioId(cartaoId, usuario.getId());
		Optional<Concurso> concurso = concursoRepository.findById(concursoId);
		
		if (cartao.isPresent() && concurso.isPresent()) {
			
			ApostaKey apostaKey = new ApostaKey(usuario, cartao.get(), concurso.get());
			Optional<Aposta> aposta = apostaRepository.findById(apostaKey);
			if (aposta.isPresent()) {
				apostaRepository.delete(aposta.get());
				return ResponseEntity.ok().build();
			}
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	
}
