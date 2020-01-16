package info.andrefelipelaus.megasenabackend.controller.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import info.andrefelipelaus.megasenabackend.model.CartaoAposta;
import lombok.Data;

@Data
public class CartaoApostaDto {

	private Long id;
	private Integer quantidadeNumerosJogados;
	private List<JogoDto> jogos;

	public CartaoApostaDto(CartaoAposta cartaoAposta) {
		this.id = cartaoAposta.getId();
		this.quantidadeNumerosJogados = cartaoAposta.getQuantidadeNumerosJogados();
		this.jogos = JogoDto.converter(cartaoAposta.getJogos());
	}
	
	public static Page<CartaoApostaDto> converter(Page<CartaoAposta> cartao) {
		return cartao.map(CartaoApostaDto::new);
	}
}
