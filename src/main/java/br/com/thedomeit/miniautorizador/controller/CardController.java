package br.com.thedomeit.miniautorizador.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.thedomeit.miniautorizador.domain.dto.CardDto;
import br.com.thedomeit.miniautorizador.domain.dto.TransactionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cartoes")
@Api(tags = "VRCARD")
public class CardController {


	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Criar cartão")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Criação com sucesso!", response = CardDto.class),
			@ApiResponse(code = 400, message = "Bad request is received"),
			@ApiResponse(code = 500, message = "800000 - Server error")
	})
	public void insertCard(@Valid @RequestBody CardDto cardDto){
		
	}

	@GetMapping("/{numeroCartao}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Consultar saldo cartão")
	@ApiResponses(value = {
    		@ApiResponse(code = 200, message = "Saldo obtido"),
            @ApiResponse(code = 400, message = "Bad request is received"),
            @ApiResponse(code = 404, message = "Cartão não encontrado"),
            @ApiResponse(code = 500, message = "800000 - Server error")
      })
	public TransactionDto findByCard(@PathVariable("numeroCartao") String numeroCartao){
		return null;
	}
}

