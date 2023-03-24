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

	@NotBlank(message = "cardNumber is necessary")
    @Size(min = 16, message = "The card must have 16 numbers")
    @Size(max = 16, message = "The card must have 16 numbers")
    @Schema(description = "Number of card", example = "1234567890123456", required = true)
	private String cardNumber;                         
	
	@NotBlank(message = "ClientName is necessary")
	@Schema(description = "Client name of card", example = "Client Name", required = true)
	private String clientName;

	@NotBlank(message = "Description Expense is necessary")
	@Schema(description = "Password of card", example = "1234", required = true)
	private String password;
	
}
