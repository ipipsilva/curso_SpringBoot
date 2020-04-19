package com.wallet.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class WalletItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3502303281078668967L;

	private Long id;

	@NotNull(message = "Insira um id de carteira")
	private Long wallet;

	@NotNull(message = "Insira uma data")
	private Date date;

	@NotNull(message = "Insira um tipo")
	@Pattern(regexp = "^(ENTRADA|SAÍDA)$", message = "Para o tipo somente são aceitos os valores: ENTRADA ou SAÍDA.")
	private String type;

	@NotNull(message = "Insira uma descrição")
	@Length(min = 5, message = "A descrição deve ter tamnho mínimo de 5 caracteres.")
	private String description;

	@NotNull(message = "Insira um valor")
	private BigDecimal value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWallet() {
		return wallet;
	}

	public void setWallet(Long wallet) {
		this.wallet = wallet;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}