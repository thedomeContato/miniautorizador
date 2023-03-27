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

}
