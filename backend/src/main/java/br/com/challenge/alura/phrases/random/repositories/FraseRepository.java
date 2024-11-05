package br.com.challenge.alura.phrases.random.repositories;

import br.com.challenge.alura.phrases.random.models.entities.Frase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FraseRepository extends JpaRepository<Frase, Long> {

	List<Frase> findByTituloContainingIgnoreCase(String title);

	@Query("SELECT f FROM Frase f ORDER BY function('RANDOM') LIMIT 1")
	Optional<Frase> findRandom();
}
