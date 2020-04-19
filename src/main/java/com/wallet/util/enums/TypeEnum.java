package com.wallet.util.enums;

public enum TypeEnum {

	// @formatter:off
	EN("ENTRADA"), 
	SD("SAÍDA");
    // @formatter:on

	private final String descricao;

	private TypeEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TypeEnum obterPorDescricao(String descricao) {

		for (TypeEnum tipo : TypeEnum.values()) {
			if (descricao.equals(tipo.getDescricao())) {
				return tipo;
			}
		}

		return null;
	}
}
