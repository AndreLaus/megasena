package info.andrefelipelaus.megasenabackend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import info.andrefelipelaus.megasenabackend.model.Jogo;
import info.andrefelipelaus.megasenabackend.model.JogoKey;

public interface JogoRepository extends JpaRepository<Jogo, JogoKey>{
	
}
