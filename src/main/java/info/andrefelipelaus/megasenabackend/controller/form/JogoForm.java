package info.andrefelipelaus.megasenabackend.controller.form;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import info.andrefelipelaus.megasenabackend.model.CartaoAposta;
import info.andrefelipelaus.megasenabackend.model.Jogo;
import info.andrefelipelaus.megasenabackend.model.JogoKey;
import info.andrefelipelaus.megasenabackend.model.repository.JogoRepository;
import lombok.Data;

@Data
public class JogoForm {

	@NotNull
	@Size(min = CartaoAposta.MIN_NUMERO_JOGADOS, max = CartaoAposta.MAX_NUMERO_JOGADOS)
	private List<Integer> dezenas;

	@NotNull
	private Short posicao;

	public Jogo converte(CartaoAposta cartao) {
		return new Jogo(cartao, this.posicao, this.dezenas);
	}

	public Jogo atualizar(CartaoAposta cartaoAposta, JogoRepository jogoRepository) {

		JogoKey id = new JogoKey(cartaoAposta.getId(), posicao);
		Optional<Jogo> optional = jogoRepository.findById(id);
		Jogo jogo = null;
		if (optional.isPresent()) {
			jogo = optional.get();
			jogo.setDezenas(this.dezenas);
		} else {
			jogo = new Jogo(cartaoAposta, posicao, dezenas);
			cartaoAposta.addJogo(jogo);
		}

		return jogo;
	}

}
