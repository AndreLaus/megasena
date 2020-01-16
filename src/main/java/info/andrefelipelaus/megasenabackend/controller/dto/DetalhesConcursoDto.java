package info.andrefelipelaus.megasenabackend.controller.dto;

import java.time.LocalDate;
import java.util.List;

import info.andrefelipelaus.megasenabackend.model.Concurso;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class DetalhesConcursoDto {

	private Integer numero;
	private LocalDate dataSorteio;
	private List<Short> dezenas;
	private Double arrecadacaoTotal;
	private Integer numeroGanhadoresSena;
	private String cidades;
	private String estados;
	private Double rateioSena;
	private Integer numeroGanhadoresQuina;
	private Double rateioQuina;
	private Integer numeroGanhadoresQuadra;
	private Double rateioQuadra;
	private Boolean acumulado;
	private Double valorAcumulado;
	private Double estimativaPremio;
	private Double acumuladoMegaDaVirada;
	
	public DetalhesConcursoDto(Concurso concurso) {
		this.numero = concurso.getNumero();
		this.dataSorteio = concurso.getDataSorteio();
		this.dezenas = concurso.getDezenas();
		this.arrecadacaoTotal = concurso.getArrecadacaoTotal();
		this.numeroGanhadoresSena = concurso.getNumeroGanhadoresSena();
		this.cidades = concurso.getCidades();
		this.estados = concurso.getEstados();
		this.rateioSena = concurso.getRateioSena();
		this.numeroGanhadoresQuina = concurso.getNumeroGanhadoresQuina();
		this.rateioQuina = concurso.getRateioQuina();
		this.numeroGanhadoresQuadra = concurso.getNumeroGanhadoresQuadra();
		this.rateioQuadra = concurso.getRateioQuadra();
		this.acumulado = concurso.getAcumulado();
		this.valorAcumulado = concurso.getValorAcumulado();
		this.estimativaPremio = concurso.getEstimativaPremio();
		this.acumuladoMegaDaVirada = concurso.getAcumuladoMegaDaVirada();
	}
	
	

}
