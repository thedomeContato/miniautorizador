package br.com.thedomeit.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.thedomeit.miniautorizador.domain.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
