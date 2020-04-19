package com.wallet.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class UserWalletDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2967734768175991190L;

	private Long id;

	@NotNull(message = "Informe o id do usuário")
	private Long users;

	@NotNull(message = "Informa o id da carteira")
	private Long wallet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsers() {
		return users;
	}

	public void setUsers(Long users) {
		this.users = users;
	}

	public Long getWallet() {
		return wallet;
	}

	public void setWallet(Long wallet) {
		this.wallet = wallet;
	}

}
