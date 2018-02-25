package br.com.produzz.enumeration;

public enum ECanal {
	FACEBOOK(1, "Facebook"),
	GOOGLE(2, "Google");

	private Integer codigo;
	private String descricao;

	ECanal(final Integer codigo, final String descricao) {
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
