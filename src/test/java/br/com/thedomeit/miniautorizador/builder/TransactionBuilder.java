package br.com.thedomeit.miniautorizador.builder;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import br.com.thedomeit.miniautorizador.domain.dto.TransactionDto;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class TransactionBuilder {
    public static TransactionDto newTransactionInvalidPassword() {
        return TransactionDto.builder()
                             .numeroCartao("1149873445634233")
                             .senhaCartao("5555")
                             .valor(BigDecimal.valueOf(500))
                             .build();
    }

    public static TransactionDto newTransactionNonexistentCard() {
        return TransactionDto.builder()
                             .numeroCartao("4444444444444444")
                             .senhaCartao("4444")
                             .valor(BigDecimal.valueOf(54.33))
                             .build();
    }


	public static TransactionDto newTransactionValid() {
	        return TransactionDto.builder()
	                             .numeroCartao("1149873445634233")
	                             .senhaCartao("1234")
	                             .valor(BigDecimal.valueOf(500))
	                             .build();
	    }

    public static TransactionDto newTransactionInsufficientFunds() {
	        return TransactionDto.builder()
 	                             .numeroCartao("1149873445634233")
	                             .senhaCartao("1234")
	                             .valor(BigDecimal.valueOf(20.34))
	                             .build();
    }
}