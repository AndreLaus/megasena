package info.andrefelipelaus.megasenabackend.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import info.andrefelipelaus.megasenabackend.model.Aposta;
import info.andrefelipelaus.megasenabackend.model.ApostaKey;
import info.andrefelipelaus.megasenabackend.model.Usuario;

public interface ApostaRepository extends JpaRepository<Aposta, ApostaKey> {

	Page<Aposta> findByIdUsuario(Usuario usuario, Pageable pageable);
}
