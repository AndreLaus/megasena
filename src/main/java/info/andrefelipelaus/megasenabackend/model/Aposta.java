package info.andrefelipelaus.megasenabackend.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class Aposta {

	@EmbeddedId
	private ApostaKey id;

	public Aposta(ApostaKey id) {
		this.id = id;
	}

	public Aposta(Usuario usuario, CartaoAposta cartao, Concurso concurso) {
		this(new ApostaKey(usuario, cartao, concurso));
	}

}
