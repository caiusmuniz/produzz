package br.com.produzz.requisicao;

import java.io.Serializable;

public class Requisicao implements Serializable {
	private static final long serialVersionUID = 8603035402853943940L;

	private String token;	

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}
}
