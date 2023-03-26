package br.com.thedomeit.miniautorizador.builder;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.stereotype.Component;

import br.com.thedomeit.miniautorizador.domain.dto.CardDto;
import br.com.thedomeit.miniautorizador.domain.entities.VrCard;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class CardBuilder {

	public Random random = new Random();

    public static CardDto newCardWrongValue() {
        return CardDto.builder()
                .numeroCartao("AAAA123465667888")
                .senhaCartao("1234")
                .build();
    }

    public static CardDto newCardValid() {
        return CardDto.builder()
                .numeroCartao("1234567890123456")
                .senhaCartao("1234")
                .build();
    }
    
    public static VrCard newVrCard() {
        return VrCard.builder()
                .id(1L)
                .cardNumber("6543210987654321")
                .password("1234")
                .balance(BigDecimal.valueOf(500))
                .build();
    }

    public static CardDto duplicateCard() {
        return CardDto.builder()
                .numeroCartao("1234567890767676")
                .senhaCartao("1234")
                .build();
    }

    public static VrCard newVrCardInsufficientFunds() {
        return VrCard.builder()
                .id(2L)
                .cardNumber("1545454545456656")
                .password("1234")
                .balance(BigDecimal.valueOf(500))
                .build();
    }    
}
