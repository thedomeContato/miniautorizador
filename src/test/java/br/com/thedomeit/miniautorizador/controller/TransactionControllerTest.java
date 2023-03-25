package br.com.thedomeit.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.thedomeit.miniautorizador.builder.TransactionBuilder;
import br.com.thedomeit.miniautorizador.domain.dto.TransactionDto;
import br.com.thedomeit.miniautorizador.exception.InsufficientFundsException;
import br.com.thedomeit.miniautorizador.exception.InvalidPasswordException;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardTransactionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc

public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    private final TransactionDto novaTransactionDtoPadrao = TransactionBuilder.newTransactionValid();

    private final TransactionDto novaTransactionDtoCartaoInexistente = TransactionBuilder.newTransactionNonexistentCard();

    private final TransactionDto novaTransactionDtoSenhaIncorreta = TransactionBuilder.newTransactionInvalidPassword();

    private final TransactionDto novaTransactionDtoSaldoInsuficiente = TransactionBuilder.newTransactionInsufficientFunds();

    private final static String BASE_URL = "/transacoes";

    private final static String APPLICATION_JSON = "application/json";

    @Test
    void whenTransationReturnOK() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransactionDtoPadrao)))
                .andExpect(status().isOk());
    }

    @Test
    void whenTransactionNonexistentCard() throws Exception {
        doThrow(NonexistentCardTransactionException.class).when(transactionService).realizarTransactionDto(novaTransactionDtoCartaoInexistente);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransactionDtoCartaoInexistente)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void whenTransactionInvalidPassword() throws Exception {
        doThrow(InvalidPasswordException.class).when(transactionService).realizarTransactionDto(novaTransactionDtoSenhaIncorreta);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransactionDtoSenhaIncorreta)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void whenInsufficientFunds() throws Exception {
        doThrow(InsufficientFundsException.class).when(transactionService).realizarTransactionDto(novaTransactionDtoSaldoInsuficiente);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novaTransactionDtoSaldoInsuficiente)))
                .andExpect(status().isUnprocessableEntity());
    }

}
