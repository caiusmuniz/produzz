package br.com.produzz.enumeration;

public enum EMovimentoSaldo {
	CREDITO("C", "Credito"),
	DEBITO("D", "Debito"),
	ESTORNO("E", "Estorno"),
	ZERAR("Z", "Zerar");

	private String codigo;
	private String descricao;

	EMovimentoSaldo(final String codigo, final String descricao) {
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
