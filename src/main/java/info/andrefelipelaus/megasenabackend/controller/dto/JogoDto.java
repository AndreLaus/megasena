package info.andrefelipelaus.megasenabackend.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import info.andrefelipelaus.megasenabackend.model.Jogo;
import lombok.Data;

@Data
public class JogoDto {

	private Long cartaoId;
	private short posicao;
	private List<Integer> dezenas;
	
	public JogoDto(Jogo jogo) {
		this.cartaoId = jogo.getId().getCartaoId();
		this.posicao = jogo.getId().getPosicao();
		this.dezenas = jogo.getDezenas();
	}
	
	public static List<JogoDto> converter(List<Jogo> jogos) {
		return jogos.stream().map(JogoDto::new).collect(Collectors.toList());
	}
}
