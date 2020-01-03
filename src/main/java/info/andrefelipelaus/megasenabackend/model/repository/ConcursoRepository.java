package info.andrefelipelaus.megasenabackend.model.repository;

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

}
