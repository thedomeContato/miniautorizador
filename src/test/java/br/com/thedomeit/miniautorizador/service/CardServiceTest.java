package br.com.thedomeit.miniautorizador.service;

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
import br.com.thedomeit.miniautorizador.exception.NonexistentCardBalanceException;
import br.com.thedomeit.miniautorizador.repository.CardRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CardServiceTest {
    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Captor
    ArgumentCaptor<VrCard> cardCap;

    private final VrCard validVrCard = CardBuilder.newVrCard();

    private final CardDto cardWrongValue = CardBuilder.newCardWrongValue();

    private final CardDto validCard = CardBuilder.newCardValid();

    @Test
    void quandoAlfanumericoNoNumeroVrCardThrowsVrCardInvalidoException() {
        assertThrows(InvalidCardException.class,
                () -> cardService.createCard(cardWrongValue));
    }

    @Test
    void quandoVrCardExistirThrowsVrCardDuplicadoException() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.of(validVrCard));

        assertThrows(DuplicateCardException.class,
                () -> cardService.createCard(validCard));
    }

    @Test
    void quandoVrCardValidoSalvarVrCard() {
        cardService.createCard(validCard);
        verify(cardRepository).save(cardCap.capture());
    }

    @Test
    void quandoVrCardNaoExistisThrowVrCardInexistenteException() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.empty());

        assertThrows(NonexistentCardBalanceException.class,
                () -> cardService.getBalance("9999999999999993"));
    }

    @Test
    void quandoVrCardExistirRetornarSaldoDoVrCard() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.of(validVrCard));
        assertEquals(BigDecimal.valueOf(500), cardService.getBalance(validVrCard.getCardNumber()));
    }
}

