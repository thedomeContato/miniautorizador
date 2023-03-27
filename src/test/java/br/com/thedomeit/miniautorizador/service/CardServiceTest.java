package br.com.thedomeit.miniautorizador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.thedomeit.miniautorizador.builder.CardBuilder;
import br.com.thedomeit.miniautorizador.domain.dto.CardDto;
import br.com.thedomeit.miniautorizador.domain.entities.VrCard;
import br.com.thedomeit.miniautorizador.exception.DuplicateCardException;
import br.com.thedomeit.miniautorizador.exception.InvalidCardException;
import br.com.thedomeit.miniautorizador.repository.CardRepository;

@ExtendWith(SpringExtension.class)
public class CardServiceTest {
    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Captor
    ArgumentCaptor<VrCard> cardCap;

    private final VrCard validVrCard = CardBuilder.newVrCard();

    private final CardDto validCard = CardBuilder.newCardValid();
    
    @Test
    void whenReturnsOk() {
        cardService.createCard(validCard);
        verify(cardRepository).save(cardCap.capture());
    }

    @Test
    void whenGetBalanceReturnOk() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.of(validVrCard));
        assertEquals(BigDecimal.valueOf(500), cardService.getBalance(validVrCard.getCardNumber()).getBody());
    }
}

