package info.andrefelipelaus.megasenabackend.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import info.andrefelipelaus.megasenabackend.model.Concurso;

/**
 * 
 * @author André Felipe Laus
 *
 *
 * Padrão Spring para facilitar a manipulação dos objetos JPA do tipo {@link Concurso}
 *
 */
@Repository
public interface ConcursoRepository extends JpaRepository<Concurso, Integer> {

	Optional<Concurso> findFirst1ByOrderByNumeroDesc();
	List<Concurso> findFirst5ByOrderByNumeroDesc();
}
