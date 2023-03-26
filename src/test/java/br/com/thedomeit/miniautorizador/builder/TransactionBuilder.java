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
                             .numeroCartao("1234567890123456")
                             .senhaCartao("5555")
                             .valor(BigDecimal.valueOf(500))
                             .build();
    }

	public static TransactionDto newTransactionValid() {
	        return TransactionDto.builder()
	                             .numeroCartao("1234567890333444")
	                             .senhaCartao("1234")
	                             .valor(BigDecimal.valueOf(500))
	                             .build();
	    }
}