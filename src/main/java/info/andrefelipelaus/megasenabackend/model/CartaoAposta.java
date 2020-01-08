package info.andrefelipelaus.megasenabackend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode
public class CartaoAposta {

	public static final int MIN_NUMERO_JOGADOS = 6;
	public static final int MAX_NUMERO_JOGADOS = 15;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	private Long id;
	
	@ManyToOne
	private Usuario usuario;
	
	private Integer quantidadeNumerosJogados;
	
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "cartao",
			orphanRemoval = true, 
			fetch = FetchType.EAGER)
	@OrderBy("posicao ASC")
	@Setter
	private List<Jogo> jogos;
	
	public CartaoAposta() {
		
		this.jogos = new ArrayList<Jogo>(3);
	}
	

	public CartaoAposta(Usuario usuario, Integer quantidadeNumerosJogados) {
		this();
		this.usuario = usuario;
		this.quantidadeNumerosJogados = quantidadeNumerosJogados;
	}
	
	
	
	public void setQuantidadeNumerosJogados(Integer quantidadeNumerosJogados) {
		
		if (quantidadeNumerosJogados < MIN_NUMERO_JOGADOS 
				|| quantidadeNumerosJogados > MAX_NUMERO_JOGADOS) {
			throw new IllegalArgumentException(String.format("Quantidade de números a serem jogados deve ser entre %d e %d"
														, MIN_NUMERO_JOGADOS
														, MAX_NUMERO_JOGADOS));
		}
		
		this.quantidadeNumerosJogados = quantidadeNumerosJogados;
	}

	public void addJogo(Jogo jogo) {
		jogo.setUsuario(this.usuario);
		this.jogos.add(jogo);
	}
	
}
