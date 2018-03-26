package br.com.produzz.enumeration;

public enum EPrivacyStatus {
	PUBLICO(1, "public", "Public"),
	PRIVADO(2, "private", "Private");

	private Integer id;
	private String codigo;
	private String descricao;

	EPrivacyStatus(final Integer id, final String codigo, final String descricao) {
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EPrivacyStatus get(final String codigo) {
		EPrivacyStatus ret = EPrivacyStatus.PRIVADO;

		for (EPrivacyStatus item : values()) {
			if (codigo.equalsIgnoreCase(item.getCodigo())) {
				ret = item;
			}
		}

		return ret;
	}
}
