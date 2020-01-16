package info.andrefelipelaus.megasenabackend.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UsuarioBaseRepository<T, ID> extends JpaRepository<T, ID> {

	List<T> findByUsuarioId(Long usuarioId);
	
	Page<T> findByUsuarioId(Long usuarioId, Pageable pageable);
	
	Optional<T> findByIdAndUsuarioId(ID id, Long usuarioId);
}
