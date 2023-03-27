package br.com.thedomeit.miniautorizador.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.thedomeit.miniautorizador.domain.dto.CardDto;
import br.com.thedomeit.miniautorizador.domain.dto.TransactionDto;
import br.com.thedomeit.miniautorizador.domain.enumtype.TransactionStatusEnum;
import br.com.thedomeit.miniautorizador.service.TransactionService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/transacoes")
@Tag(name = "Transações", description = "Realizar transações com o cartão.")
public class TransactionController {

	@Autowired private TransactionService transactionService;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Realizar uma transação")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Transação efetuada com sucesso!", response = CardDto.class),
	})
	public ResponseEntity<TransactionStatusEnum> insertTransaction(@Valid @RequestBody TransactionDto transactionDto){
		return transactionService.startTransaction(transactionDto);
	}

}

