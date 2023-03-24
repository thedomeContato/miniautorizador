package br.com.thedomeit.miniautorizador.domain.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
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
public class TransactionDto {

	@NotBlank(message = "cardNumber is necessary")
    @Size(min = 16, message = "The card must have 16 numbers")
    @Size(max = 16, message = "The card must have 16 numbers")
    @Schema(description = "Number of card", example = "1234567890123456", required = true)
	private String cardNumber;                         

	@NotBlank(message = "Description Expense is necessary")
	@Schema(description = "Password of card", example = "1234", required = true)
	private String password;

	@DecimalMin(value = "0.0", inclusive = false)
	@Digits(integer=5, fraction=2)
	@Schema(description = "Value of Transaction", example = "12.34", required = true)
	private BigDecimal valueTransaction;
}
