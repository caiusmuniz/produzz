package br.com.produzz.retorno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.produzz.entity.Usuario;

public class UsuarioWs implements Serializable {
	private static final long serialVersionUID = 7734744683960013116L;

	private Long id;
	private String nome;
	private String sobrenome;
	private String login;
	private String indicador;
	private List<ContaWs> contas;

	public UsuarioWs() {
		this.contas = new ArrayList<ContaWs>();
	}

	public UsuarioWs(final Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		this.login = usuario.getEmail();
		this.indicador = usuario.getStatus();
		this.contas = new ArrayList<ContaWs>();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(final String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(final String indicador) {
		this.indicador = indicador;
	}

	public List<ContaWs> getContas() {
		return contas;
	}

	public void setContas(final List<ContaWs> contas) {
		this.contas = contas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioWs [id=")
				.append(id)
				.append(", nome=")
				.append(nome)
				.append(", sobrenome=")
				.append(sobrenome)
				.append(", login=")
				.append(login)
				.append(", indicador=")
				.append(indicador)
				.append(", contas=")
				.append(contas)
				.append("]");
		return builder.toString();
	}
}
