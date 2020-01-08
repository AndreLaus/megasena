package info.andrefelipelaus.megasenabackend.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import info.andrefelipelaus.megasenabackend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
	
}
