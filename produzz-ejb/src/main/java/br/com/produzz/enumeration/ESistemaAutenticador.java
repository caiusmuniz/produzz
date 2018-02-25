package br.com.produzz.enumeration;

public enum ESistemaAutenticador {
	EMAIL(1, "Email/Senha"),
	FACEBOOK(2, "Facebook"),
	GOOGLE(3, "Google");

	private Integer codigo;
	private String descricao;

	ESistemaAutenticador(final Integer codigo, final String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}
