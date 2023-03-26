package br.com.thedomeit.miniautorizador.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.thedomeit.miniautorizador.builder.CardBuilder;
import br.com.thedomeit.miniautorizador.builder.TransactionBuilder;
import br.com.thedomeit.miniautorizador.domain.dto.TransactionDto;
import br.com.thedomeit.miniautorizador.domain.entities.VrCard;
import br.com.thedomeit.miniautorizador.exception.InvalidCardException;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardTransactionException;
import br.com.thedomeit.miniautorizador.repository.CardRepository;
import br.com.thedomeit.miniautorizador.repository.TransactionRepository;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {
    @InjectMocks private TransactionService transacoesService;
    @Mock private CardRepository cardRepository;
    @Mock private TransactionRepository transactionRepository;

    private final VrCard validCard = CardBuilder.newVrCard();
    private final TransactionDto transactionInvalidPassword = TransactionBuilder.newTransactionInvalidPassword();
    private final TransactionDto transactionValidPassword = TransactionBuilder.newTransactionValid();


    @Test
    void whenInvalidPassword() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.ofNullable(validCard));
        assertThrows(InvalidCardException.class,
                () -> transacoesService.startTransaction(transactionInvalidPassword));
    }

    @Test
    void whenNonexistentCardTransaction() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.empty());
        assertThrows(NonexistentCardTransactionException.class,
                () -> transacoesService.startTransaction(transactionValidPassword));
    }
}
