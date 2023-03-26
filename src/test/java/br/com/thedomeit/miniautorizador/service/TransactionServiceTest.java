package br.com.thedomeit.miniautorizador.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.thedomeit.miniautorizador.builder.CardBuilder;
import br.com.thedomeit.miniautorizador.builder.TransactionBuilder;
import br.com.thedomeit.miniautorizador.domain.dto.TransactionDto;
import br.com.thedomeit.miniautorizador.domain.entities.VrCard;
import br.com.thedomeit.miniautorizador.exception.InsufficientFundsException;
import br.com.thedomeit.miniautorizador.exception.InvalidCardException;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardTransactionException;
import br.com.thedomeit.miniautorizador.repository.CardRepository;
import br.com.thedomeit.miniautorizador.repository.TransactionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {
    @InjectMocks private TransactionService transacoesService;
    @Mock private CardRepository cardRepository;
    @Mock private TransactionRepository transactionRepository;

    private final VrCard cartaoPadraoEntidade = CardBuilder.newVrCard();

    private final VrCard cartaoPadraoSaldoInsuficienteEntidade = CardBuilder.newVrCardInsufficientFunds();

    private final TransactionDto transactionInvalidPassword = TransactionBuilder.newTransactionInvalidPassword();

    private final TransactionDto transactionValidPassword = TransactionBuilder.newTransactionValid();


    @Test
    void quandoSenhaIncorretaThrowsSenhaIncorretaException() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoEntidade));
        assertThrows(InvalidCardException.class,
                () -> transacoesService.startTransaction(transactionInvalidPassword));
    }

    @Test
    void quandoVrCardSemSaldoThrowsSaldoInsuficienteException() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoSaldoInsuficienteEntidade));
        assertThrows(InsufficientFundsException.class,
                () -> transacoesService.startTransaction(transactionValidPassword));
    }

    @Test
    void quandoVrCardNaoExisteThrowsVrCardInexistenteException() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.empty());
        assertThrows(NonexistentCardTransactionException.class,
                () -> transacoesService.startTransaction(transactionValidPassword));
    }

    @Test
    void quandoTransactionDtoValidaEfetuarTransactionDto() {
        when(cardRepository.findByCardNumber(any(String.class))).thenReturn(Optional.ofNullable(cartaoPadraoEntidade));

        assertNotNull(cartaoPadraoEntidade);
        transacoesService.startTransaction(transactionValidPassword);

        verify(cardRepository).save(cartaoPadraoEntidade);
    }
}
