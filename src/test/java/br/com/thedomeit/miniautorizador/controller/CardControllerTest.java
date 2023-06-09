package br.com.thedomeit.miniautorizador.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.thedomeit.miniautorizador.builder.CardBuilder;
import br.com.thedomeit.miniautorizador.domain.dto.CardDto;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardBalanceException;
import br.com.thedomeit.miniautorizador.service.CardService;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CardController.class)
@AutoConfigureMockMvc
public class CardControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardService cardService;

    private final CardDto validCard = CardBuilder.newCardValid();
    private final static String BASE_URL = "/cartoes";
    private final static String APPLICATION_JSON = "application/json";
    private final static String VALID_CARD = "1111222233334444";
    private final static String NONEXISTENT_CARD = "1213123112312312";

    @Test
    void whenCreateCardReturnOK() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCard)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenValidCardHaveFunds() throws Exception {
        when(cardService.getBalance(VALID_CARD))
                          .thenReturn(new ResponseEntity<>(BigDecimal.valueOf(500),HttpStatus.OK));

        mockMvc.perform(get(BASE_URL + "/{numeroCartao}", VALID_CARD)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenNonexistenteCardGetBalance() throws Exception {
        when(cardService.getBalance(NONEXISTENT_CARD))
                          .thenThrow(NonexistentCardBalanceException.class);

        mockMvc.perform(get(BASE_URL + "/{numeroCartao}", NONEXISTENT_CARD)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
