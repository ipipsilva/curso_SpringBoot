package com.wallet.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class WalletDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6923930465961404659L;

	private Long id;

	@Length(min = 3, message = "O nome deve conter no mínimo 3 caracteres.")
	@NotNull(message = "O nome não poder ser nulo.")
	private String nome;

	@NotNull(message = "O valor não pode ser nulo.")
	private BigDecimal value;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}