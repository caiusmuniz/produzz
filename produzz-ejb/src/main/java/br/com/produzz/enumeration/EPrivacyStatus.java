package br.com.produzz.enumeration;

public enum EPrivacyStatus {
	PUBLICO("public", "Public"),
	PRIVADO("private", "Private");

	private String codigo;
	private String descricao;

	EPrivacyStatus(final String codigo, final String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
}
