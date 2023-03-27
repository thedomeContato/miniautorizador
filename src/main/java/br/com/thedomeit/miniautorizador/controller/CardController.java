package br.com.thedomeit.miniautorizador.controller;

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
import br.com.thedomeit.miniautorizador.service.CardService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
	})
	public ResponseEntity<CardDto> createCard(@Valid @RequestBody CardDto cardDto){
		
		return cardService.createCard(cardDto);
    }

	@GetMapping("/{numeroCartao}")
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Consultar saldo")
	@ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Saldo obtido"),
            @ApiResponse(code = 404, message = "Card not found"),
      })
	public ResponseEntity<BigDecimal> getBalance(@PathVariable("numeroCartao") String numeroCartao){
		return cardService.getBalance(numeroCartao);
	}
}

