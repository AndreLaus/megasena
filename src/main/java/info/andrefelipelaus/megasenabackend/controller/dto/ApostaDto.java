package info.andrefelipelaus.megasenabackend.controller.dto;

import org.springframework.data.domain.Page;

import info.andrefelipelaus.megasenabackend.model.Aposta;
import lombok.Data;

@Data
public class ApostaDto {

	private Long cartaoId;
	private ConcursoDto concurso;

	public ApostaDto(Aposta aposta) {
		this.cartaoId = aposta.getId().getCartao().getId();
		this.concurso = new ConcursoDto(aposta.getId().getConcurso());
	}

	public static Page<ApostaDto> converter(Page<Aposta> aposta) {
		return aposta.map(ApostaDto::new);
	}

}
