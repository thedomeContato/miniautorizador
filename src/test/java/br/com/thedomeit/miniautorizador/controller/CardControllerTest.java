package br.com.thedomeit.miniautorizador.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.thedomeit.miniautorizador.builder.CardBuilder;
import br.com.thedomeit.miniautorizador.domain.dto.CardDto;
import br.com.thedomeit.miniautorizador.exception.DuplicateCardException;
import br.com.thedomeit.miniautorizador.exception.InvalidCardException;
import br.com.thedomeit.miniautorizador.exception.NonexistentCardBalanceException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CardController.class)
@AutoConfigureMockMvc
public class CardControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CardService cartaoService;

    private final CardDto cartaoPadraoDuplicado = CardBuilder.duplicateCard();

    private final CardDto novoCartaoCorreto = CardBuilder.newCardValid();

    private final CardDto novoCartaoComAlfaNumerico = CardBuilder.newCardWrongValue();

    private final static String BASE_URL = "/cartoes";

    private final static String APPLICATION_JSON = "application/json";

    private final static String NUMERO_CARTAO_VALIDO = "1149873445634233";

    private final static String NUMERO_CARTAO_INEXISTENTE = "3333333333333333";

    @Test
    void quandoCartaoCriadoComSucessoRetornaHttpStatusCode201() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoCartaoCorreto)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenCreateCardAndExistYet() throws Exception {
        when(cartaoService.criarCartao(cartaoPadraoDuplicado))
                          .thenThrow(DuplicateCardException.class);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaoPadraoDuplicado)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void whenCreateCardWithWrongValuesOfCamp() throws Exception {
        when(cartaoService.criarCartao(novoCartaoComAlfaNumerico))
                          .thenThrow(InvalidCardException.class);

        mockMvc.perform(post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoCartaoComAlfaNumerico)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void whenValidCardHaveFunds() throws Exception {
        when(cartaoService.obterSaldoCartao(NUMERO_CARTAO_VALIDO))
                          .thenReturn(BigDecimal.valueOf(500));

        mockMvc.perform(get(BASE_URL + "/{numeroCartao}", NUMERO_CARTAO_VALIDO)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void whenNonexistenteCardGetBalance() throws Exception {
        when(cartaoService.obterSaldoCartao(NUMERO_CARTAO_INEXISTENTE))
                          .thenThrow(NonexistentCardBalanceException.class);

        mockMvc.perform(get(BASE_URL + "/{numeroCartao}", NUMERO_CARTAO_INEXISTENTE)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
