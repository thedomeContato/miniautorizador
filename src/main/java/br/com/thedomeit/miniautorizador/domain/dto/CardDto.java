package br.com.thedomeit.miniautorizador.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class CardDto {

	@NotBlank(message = "Numero do Cartão é necessário")
    @Size(min = 16, message = "Valor mínimo de 16 números")
    @Size(max = 16, message = "Valor mínimo de 16 números")
    @Schema(description = "Numero do Cartão", example = "1234567890123456", required = true)
	private String numeroCartao;             

	@NotBlank(message = "Senha é necessária")
	@Schema(description = "Senha do Cartão", example = "1234", required = true)
	private String senhaCartao;

	
}
