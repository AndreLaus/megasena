package info.andrefelipelaus.megasenabackend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"cartao_id", "posicao"})
})

@Getter
@Setter
@EqualsAndHashCode
public class Jogo {

	@EmbeddedId
	private JogoKey id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("cartaoId")
	private CartaoAposta cartao;
	
	@ManyToOne
	private Usuario usuario;
	
	@ElementCollection
	private List<Integer> dezenas;
	
	public Jogo() {
		this.dezenas = new ArrayList<Integer>();
	}
	
	public Jogo(CartaoAposta cartaoAposta, short posicao, List<Integer> dezenas) {
		this();
		this.id = new JogoKey();
		this.id.setCartaoId(cartaoAposta.getId());
		this.id.setPosicao(posicao);
		this.cartao = cartaoAposta;
		this.usuario = cartaoAposta.getUsuario();
		this.dezenas.addAll(dezenas);
		
	}
	
	public void addDezenas(Integer dezena) {
		if (this.cartao.getQuantidadeNumerosJogados()==0) {
			throw new IllegalStateException("Antes de adicionar as dezenas deve ser informada a quantidade de números que serão jogados (quantidadeNumerosJogados)");
		}
		
		if (this.dezenas.size()==this.cartao.getQuantidadeNumerosJogados()) {
			throw new IllegalArgumentException("A quantidade de dezenas já chegou ao limite permitido");
		}
		
		this.dezenas.add(dezena);
	}

	@Override
	public String toString() {
		return "Jogo [id=" + id + ", dezenas=" + dezenas + "]";
	}
	
	

}
