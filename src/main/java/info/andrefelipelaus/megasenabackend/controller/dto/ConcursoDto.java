package info.andrefelipelaus.megasenabackend.controller.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import info.andrefelipelaus.megasenabackend.model.Concurso;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ConcursoDto {

	private Integer numero;
	private LocalDate dataSorteio;
	private List<Short> dezenas;
	
	public ConcursoDto(Concurso concurso) {
		
		this.numero = concurso.getNumero();
		this.dataSorteio = concurso.getDataSorteio();
		this.dezenas = concurso.getDezenas();
	}
	
	public static Page<ConcursoDto> convert(Page<Concurso> concurso) {
		return concurso.map(ConcursoDto::new);
	}
}
