package br.com.produzz.enumeration;

public enum EAtivo {
	ATIVO("S", "Ativo"),
	INATIVO("N", "Inativo");

	private String codigo;
	private String descricao;

	EAtivo(final String codigo, final String descricao) {
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
