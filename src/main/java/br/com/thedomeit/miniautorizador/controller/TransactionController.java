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
import br.com.thedomeit.miniautorizador.exception.InsufficientFundsException;
import br.com.thedomeit.miniautorizador.exception.InvalidPasswordException;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardTransactionException;
import br.com.thedomeit.miniautorizador.service.TransactionService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/transacoes")
@Tag(name = "Transações", description = "Realizar transações com o cartão.")
public class TransactionController {

	@Autowired private TransactionService transactionService;
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Realizar uma transação")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Transação efetuada com sucesso!", response = CardDto.class),
			@ApiResponse(code = 400, message = "Bad request is received"),
			@ApiResponse(code = 500, message = "800000 - Server error")
	})
	public ResponseEntity<TransactionStatusEnum> insertTransaction(@Valid @RequestBody TransactionDto transactionDto){
		try {
			transactionService.startTransaction(transactionDto);
			return new ResponseEntity<>(TransactionStatusEnum.OK, HttpStatus.OK);

	        } catch (NonexistentCardTransactionException e) {
	        	log.error("ERRO: Cartão inválido.");
	        	return new ResponseEntity<>(TransactionStatusEnum.CARTAO_INEXISTENTE, HttpStatus.UNPROCESSABLE_ENTITY);

	        } catch (InvalidPasswordException e) {
	        	log.error("ERRO: Senha inválida.");
	        	return new ResponseEntity<>(TransactionStatusEnum.SENHA_INVALIDA, HttpStatus.UNPROCESSABLE_ENTITY);

	        } catch (InsufficientFundsException e) {
	        	log.error("ERRO: Saldo insuficiente.");
	        	return new ResponseEntity<>(TransactionStatusEnum.SALDO_INSUFICIENTE, HttpStatus.UNPROCESSABLE_ENTITY);
	        }
	    }

}

