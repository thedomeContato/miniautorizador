package br.com.thedomeit.miniautorizador.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.thedomeit.miniautorizador.domain.dto.TransactionDto;
import br.com.thedomeit.miniautorizador.domain.entities.Transaction;
import br.com.thedomeit.miniautorizador.domain.entities.VrCard;
import br.com.thedomeit.miniautorizador.domain.enumtype.TransactionStatusEnum;
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

    public ResponseEntity<TransactionStatusEnum> startTransaction(TransactionDto transaction) {
        log.info("Start Transaction...");

        Optional<VrCard> cardOptional = cardRepository.findByCardNumber(transaction.getNumeroCartao());

        VrCard card = cardOptional.orElse(new VrCard());	

        if (card.getCardNumber() == null) {
        	return new ResponseEntity<>(TransactionStatusEnum.CARTAO_INEXISTENTE, 
        			                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        if (!validatePassword(transaction.getSenhaCartao(), card.getPassword())) {
        	return new ResponseEntity<>(TransactionStatusEnum.SENHA_INVALIDA, 
        			                    HttpStatus.UNPROCESSABLE_ENTITY); 
        }

        if (validateBalance(card.getBalance(), transaction.getValor())) {
        	return new ResponseEntity<>(TransactionStatusEnum.SALDO_INSUFICIENTE, 
        			                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        finalizeTransaction(card, transaction);

        return new ResponseEntity<>(TransactionStatusEnum.OK, HttpStatus.OK);
    }

    private Boolean validatePassword(String senhaTransaction, String senhaVrCard) {
        return (senhaTransaction.equals(senhaVrCard));
    }

    private Boolean validateBalance(BigDecimal saldoVrCard, BigDecimal valorTransaction) {
        return ((saldoVrCard.subtract(valorTransaction)).signum() == -1);
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
