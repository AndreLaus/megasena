package info.andrefelipelaus.megasenabackend.controller.form;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import info.andrefelipelaus.megasenabackend.model.CartaoAposta;
import info.andrefelipelaus.megasenabackend.model.Jogo;
import info.andrefelipelaus.megasenabackend.model.Usuario;
import info.andrefelipelaus.megasenabackend.model.repository.CartaoApostaRepository;
import info.andrefelipelaus.megasenabackend.model.repository.JogoRepository;
import lombok.Data;

@Data
public class CartaoApostaForm {

	@NotNull
	@Range(min = CartaoAposta.MIN_NUMERO_JOGADOS, 
			max = CartaoAposta.MAX_NUMERO_JOGADOS)
	private Integer quantidadeNumerosJogados;
	
	@NotNull @NotEmpty 
	@Size(min = 1, max = 3)
	private List<JogoForm> jogos;

	public CartaoAposta converter(Usuario usuario) {
		return new CartaoAposta(usuario, this.quantidadeNumerosJogados);
	}

	public List<Jogo> converterJogos(CartaoAposta cartaoAposta) {
		List<Jogo> jogosList = jogos.stream().map(j -> j.converte(cartaoAposta)).collect(Collectors.toList());
		
		cartaoAposta.setJogos(jogosList);
		
		return jogosList;
	}
	
	public CartaoAposta atualizar(Long id, CartaoApostaRepository cartaoApostaRepository, JogoRepository jogoRepository) {
		
		CartaoAposta cartaoAposta = cartaoApostaRepository.getOne(id);
		
		cartaoAposta.setQuantidadeNumerosJogados(this.quantidadeNumerosJogados);
		
		jogos.forEach(j -> j.atualizar(cartaoAposta, jogoRepository));
		
		return cartaoAposta;
	}
}
