package br.com.thedomeit.miniautorizador.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thedomeit.miniautorizador.domain.dto.TransactionDto;
import br.com.thedomeit.miniautorizador.domain.entities.Transaction;
import br.com.thedomeit.miniautorizador.domain.entities.VrCard;
import br.com.thedomeit.miniautorizador.exception.InsufficientFundsException;
import br.com.thedomeit.miniautorizador.exception.InvalidCardException;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardTransactionException;
import br.com.thedomeit.miniautorizador.repository.CardRepository;
import br.com.thedomeit.miniautorizador.repository.TransactionRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class TransactionService {

    @Autowired private CardRepository cardRepository;
    @Autowired private TransactionRepository transactionRepository;

    private final Object threadLock = new Object();

    public void startTransaction(TransactionDto transaction) {
        log.info("Start Transaction...");

        cardRepository.findByCardNumber(transaction.getNumeroCartao())
                .ifPresentOrElse(card -> {
                    log.info("Find Card {}.", card.getCardNumber());

                    validatePassword(transaction.getSenhaCartao(), card.getPassword());

                    validateBalance(card.getBalance(), transaction.getValor());

                    finalizeTransaction(card, transaction);

                }, () -> {
                    log.error("Invalid Card.");
                    throw new NonexistentCardTransactionException();
                });
    }

    private void validatePassword(String senhaTransaction, String senhaVrCard) {
        if (!senhaTransaction.equals(senhaVrCard))
            throw new InvalidCardException();
        log.info("Invalid Password.");
    }

    private void validateBalance(BigDecimal saldoVrCard, BigDecimal valorTransaction) {
        if ((saldoVrCard.subtract(valorTransaction)).signum() == -1)
            throw new InsufficientFundsException();
        log.info("Insuficient Funds.");
    }

    private void finalizeTransaction(VrCard card, TransactionDto transaction) {
        synchronized (threadLock) {
        	Transaction transactionBd = new Transaction();
        	
        	card.setBalance(card.getBalance().subtract(transaction.getValor()));
            cardRepository.save(card);
            
            transactionBd.setCardNumber(transaction.getNumeroCartao());
            transactionBd.setCreationDate(LocalDateTime.now());
            transactionBd.setTransaction(transaction.getValor());
 
            transactionRepository.save(transactionBd);
            
            log.info("Transaction finalize - Successful.");
        }
    }
}
