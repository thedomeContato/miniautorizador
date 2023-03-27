package br.com.thedomeit.miniautorizador.service;

import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.thedomeit.miniautorizador.domain.dto.CardDto;
import br.com.thedomeit.miniautorizador.domain.entities.VrCard;
import br.com.thedomeit.miniautorizador.exception.DuplicateCardException;
import br.com.thedomeit.miniautorizador.exception.InvalidCardException;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardBalanceException;
import br.com.thedomeit.miniautorizador.repository.CardRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class CardService {

    @Autowired private CardRepository cardRepository;

    public ResponseEntity<CardDto> createCard(CardDto card) {
        log.info("Create a card.");
        
        try {
            validateCardOnlyNumber(card.getNumeroCartao());

            validateDuplicateCard(card);

            VrCard vrcard = new VrCard();
            vrcard.setCardNumber(card.getNumeroCartao());
            vrcard.setPassword(card.getSenhaCartao());
            vrcard.setBalance(BigDecimal.valueOf(500.00));
            vrcard.setCreationDate(LocalDateTime.now());
            
            cardRepository.save(vrcard);

            log.info("Card number : {} created.", card.getNumeroCartao());
            return new ResponseEntity<>(card, HttpStatus.OK);

        } catch (DuplicateCardException e) {
            log.error("ERROR: Duplicate card.");
            return new ResponseEntity<>(card, HttpStatus.CONFLICT);

        } catch (InvalidCardException e) {
            log.error("ERROR: Invalid Card.");
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<BigDecimal> getBalance(String numeroCartao) {
        log.info("Get balance of card.");

		try {
			BigDecimal response = cardRepository.findByCardNumber(numeroCartao)
	                .map(VrCard::getBalance).orElseThrow(NonexistentCardBalanceException::new);
			return new ResponseEntity<>(response, OK);
			
	        } catch (NonexistentCardBalanceException e) {
	        	log.error("ERROR: Card Not Found.");
	        	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	        }
    }

    private void validateDuplicateCard(CardDto card) {
        cardRepository.findByCardNumber(card.getNumeroCartao().toString())
                .ifPresent(c -> {
                    log.error("Card number {} already exists in the database.", card.getNumeroCartao());
                    throw new DuplicateCardException(card.getNumeroCartao(), card.getSenhaCartao());
                });
    }

    private void validateCardOnlyNumber(String numberCard) {
        if(!numberCard.matches("^\\d+$"))
            throw new InvalidCardException();
    }
}
