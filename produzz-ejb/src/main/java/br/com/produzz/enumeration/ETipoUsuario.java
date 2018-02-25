package br.com.produzz.enumeration;

public enum ETipoUsuario {
	ADMIN(1, "Administrador"),
	USUARIO(2, "Usuario");

	private Integer codigo;
	private String descricao;

	ETipoUsuario(final Integer codigo, final String descricao) {
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
