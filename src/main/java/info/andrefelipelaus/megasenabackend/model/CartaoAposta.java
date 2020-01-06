package info.andrefelipelaus.megasenabackend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	
	private Integer quantidadeNumerosJogados;
	
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "cartao",
			orphanRemoval = true)
	@Setter
	private List<Jogo> jogos;
	
	public CartaoAposta() {
		
		this.jogos = new ArrayList<Jogo>(3);
	}
	

	public CartaoAposta(Integer quantidadeNumerosJogados) {
		this();
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
		this.jogos.add(jogo);
	}
	
}
