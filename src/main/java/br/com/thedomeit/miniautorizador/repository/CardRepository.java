package br.com.thedomeit.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.thedomeit.miniautorizador.domain.entities.VrCard;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<VrCard, Long>{

	public Optional<VrCard> findByCardNumber(Long id);
}
