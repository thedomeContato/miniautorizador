package br.com.thedomeit.miniautorizador.controller;

import static org.springframework.http.HttpStatus.OK;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.thedomeit.miniautorizador.domain.dto.CardDto;
import br.com.thedomeit.miniautorizador.exception.DuplicateCardException;
import br.com.thedomeit.miniautorizador.exception.InvalidCardException;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardBalanceException;
import br.com.thedomeit.miniautorizador.service.CardService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@Tag(name = "Cartões", description = "Gerenciamento de cartões.")
@RequestMapping("/cartoes")
public class CardController {

	@Autowired private CardService cardService;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Criar Card")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Criação com sucesso!", response = CardDto.class),
			@ApiResponse(code = 422, message = "Unprocessable Entinty"),
			@ApiResponse(code = 500, message = "800000 - Server error")
	})
	public ResponseEntity<CardDto> createCard(@Valid @RequestBody CardDto cardDto){
        CardDto response;
        try {
            response = cardService.createCard(cardDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (DuplicateCardException e) {
            response = new CardDto(e.getPassword(), e.getCardNumber());
            log.error("ERROR: Duplicate card.");
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);

        } catch (InvalidCardException e) {
            log.error("ERROR: Invalid Card.");
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

	@GetMapping("/{numeroCartao}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Consultar saldo")
	@ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Saldo obtido"),
            @ApiResponse(code = 404, message = "Card not found"),
            @ApiResponse(code = 500, message = "800000 - Server error")
      })
	public ResponseEntity<BigDecimal> getBalance(@PathVariable("numeroCartao") String numeroCartao){
		try {
			BigDecimal response = cardService.getBalance(numeroCartao);
			return new ResponseEntity<>(response, OK);
	        } catch (NonexistentCardBalanceException e) {
	        	log.error("ERROR: Card Not Found.");
	        	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	        }
	}
}

