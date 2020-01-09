package info.andrefelipelaus.megasenabackend.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.Data;

@Embeddable
@Data
public class ApostaKey implements Serializable {
	
	private static final long serialVersionUID = 54314251449693341L;

	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private CartaoAposta cartao;
	
	@ManyToOne
	private Concurso concurso;

	public ApostaKey(Usuario usuario, CartaoAposta cartao, Concurso concurso) {
		this.usuario = usuario;
		this.cartao = cartao;
		this.concurso = concurso;
	}
	
	
}
