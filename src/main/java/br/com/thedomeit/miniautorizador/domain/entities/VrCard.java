package br.com.thedomeit.miniautorizador.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@ToString
public class VrCard implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NR_CARD", nullable = false)
	private String cardNumber;
	
	@Column(name="DT_CREATION", nullable = false)
	private LocalDateTime creationDate;
	
	@Column(name="DS_PASSWORD", nullable = false)
	private String password;
	
	@Column(name="VL_BALANCE", nullable = false)
	private BigDecimal balance;
	
}
