package info.andrefelipelaus.megasenabackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.andrefelipelaus.megasenabackend.controller.dto.DetalhesConcursoDto;
import info.andrefelipelaus.megasenabackend.controller.dto.ConcursoDto;
import info.andrefelipelaus.megasenabackend.model.Concurso;
import info.andrefelipelaus.megasenabackend.model.repository.ConcursoRepository;

/**
 * 
 * @author André Felipe Laus
 *
 * Este controller apenas possui endpoints de consulta pois os concurso 
 * são carregados por rotinas e não por cadastro
 *
 */
@RestController
@RequestMapping("/concurso")
public class ConcursoController {

	@Autowired
	private ConcursoRepository concursoRepository;
	
	
	@GetMapping
	public Page<ConcursoDto> list(@PageableDefault(sort="numero", direction = Direction.DESC, page=0, size=10) Pageable pageable) {
		Page<Concurso> concursos = concursoRepository.findAll(pageable);
		return ConcursoDto.convert(concursos);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<DetalhesConcursoDto> details(@PathVariable Integer id) {
		Optional<Concurso> optional = concursoRepository.findById(id);
		if (optional.isPresent()) {
			return ResponseEntity.ok(new DetalhesConcursoDto(optional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(path = "/ultimo")
	public ResponseEntity<ConcursoDto> ultimo() {
		Optional<Concurso> optional = concursoRepository.findFirst1ByOrderByNumeroDesc();
		if (optional.isPresent()) {
			return ResponseEntity.ok(new ConcursoDto(optional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(path = "/ultimos")
	public ResponseEntity<List<ConcursoDto>> ultimosCinco() {
		List<Concurso> concursos = concursoRepository.findFirst5ByOrderByNumeroDesc();
		if (concursos!=null && concursos.size()>0) {
			return ResponseEntity.ok(ConcursoDto.convert(concursos));
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
