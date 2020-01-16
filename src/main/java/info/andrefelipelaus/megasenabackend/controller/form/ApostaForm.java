package info.andrefelipelaus.megasenabackend.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import info.andrefelipelaus.megasenabackend.model.Aposta;
import info.andrefelipelaus.megasenabackend.model.CartaoAposta;
import info.andrefelipelaus.megasenabackend.model.Concurso;
import info.andrefelipelaus.megasenabackend.model.Usuario;
import info.andrefelipelaus.megasenabackend.model.repository.CartaoApostaRepository;
import info.andrefelipelaus.megasenabackend.model.repository.ConcursoRepository;
import lombok.Data;

@Data
public class ApostaForm {

	@NotNull
	private Long cartaoId;
	
	@NotNull
	private Integer concursoId;
	
	
	public Aposta converter(Usuario usuario, 
							CartaoApostaRepository cartaoRepository,
							ConcursoRepository concursoRepository) {
		
		Optional<CartaoAposta> cartao = cartaoRepository.findByIdAndUsuarioId(cartaoId, usuario.getId());
		Optional<Concurso> concurso = concursoRepository.findById(concursoId);
		
		if (cartao.isPresent() && concurso.isPresent()) {
			return new Aposta(usuario, cartao.get(), concurso.get());
		}
		
		return null;
	}
	
}
