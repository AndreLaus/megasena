package info.andrefelipelaus.megasenabackend.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class JogoKey implements Serializable {
	
	
	private static final long serialVersionUID = 8604678514638577565L;
	
	private long cartaoId;
	private short posicao;
	
	public JogoKey() {}
	
	public JogoKey(long cartaoId, short posicao) {
		
		this.cartaoId = cartaoId;
		this.posicao = posicao;
	}
	
	
	
}