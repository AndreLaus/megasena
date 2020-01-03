package info.andrefelipelaus.megasenabackend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

/**
 * 
 * @author André Felipe Laus
 *
 * Modelo que representa os resultados dos concursos
 *
 */

@Data
@Entity
public class Concurso {
	@Id
	private Integer numero;
	private LocalDate dataSorteio;
	@ElementCollection
	private List<Short> dezenas;
	private Double arrecadacaoTotal;
	private Integer numeroGanhadoresSena;
	
	@Lob
	private String cidades;
	private String estados;
	private Double rateioSena;
	private Integer numeroGanhadoresQuina;
	private Double rateioQuina;
	private Integer numeroGanhadoresQuadra;
	private Double rateioQuadra;
	private String acumulado;
	private Double valorAcumulado;
	private Double estimativaPremio;
	private Double acumuladoMegaDaVirada;

	public Concurso() {
		this.cidades = "";
		this.estados = "";
		this.dezenas = new ArrayList<Short>();
	}
	
	public void addDezena(Short dezena) {
		this.dezenas.add(dezena);
	}

	public void addCidade(String cidade) {
		if (this.cidades.isEmpty()) {
			this.cidades = cidade;
		} else {
			this.cidades += ", "+cidade;
		}
	}

	public void addEstado(String estado) {
		if (this.estados.isEmpty()) {
			this.estados = estado;
		} else {
			this.estados += ", "+estado;
		}
	}

}
